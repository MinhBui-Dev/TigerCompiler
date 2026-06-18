package tigercompiler.registorallocator;

import java.util.*;

public class BriggsRegisterAllocationStrategy implements RegisterAllocationStrategy {
    private Map<String, Set<String>> interferenceGraph;
    private Map<String, Set<String>> oldInterferenceGraph;
    private Map<String, Integer> spillCosts;
    private HashMap<String, String> registerAssignment;
    private Stack<String> nodeStack;
    private int numberTempReg;
    private int numberSavedReg;
    private int temp;

    public BriggsRegisterAllocationStrategy(Map<String, Set<String>> interferenceGraph, Map<String, Integer> spillCosts) {
        this.registerAssignment = new HashMap<>();
        this.nodeStack = new Stack<>();
        this.numberSavedReg = 8;
        this.temp = 0;


        this.interferenceGraph = interferenceGraph;
        this.spillCosts = spillCosts;
        //Deep copy interference graph
        oldInterferenceGraph = new HashMap<>();
        for (String node : interferenceGraph.keySet()){
            oldInterferenceGraph.put(node, new HashSet<>(interferenceGraph.get(node)));
        }

        simplify();
        select();

        System.out.println("Register Allocation:");
        for (String var : registerAssignment.keySet()) {
            System.out.println("Variable: " + var + " -> Register: " + registerAssignment.get(var));
        }

    }


    @Override
    public String getRegister(String variable) {
        if (registerAssignment.containsKey(variable)) {
            System.out.println("Variable: " + variable + " -> Register: " + registerAssignment.get(variable));
        }
        return registerAssignment.get(variable);
    }

    @Override
    public String getIntermediateRegister(){
        return allocateNewRegister();
    }

    private String allocateNewRegister() {
        if (temp >= 10) {
            temp = 0;
        }
        String register = "$t" + temp;
        temp++;
        return register;
    }

    @Override
    public void reset(){
        registerAssignment.clear();
    }

    private void simplify(){
        Set<String> nodes = new HashSet<>(interferenceGraph.keySet());
        while(!nodes.isEmpty()){
            boolean removed = false;

            for(String node : nodes){
                if(interferenceGraph.get(node).size() < (numberTempReg + numberSavedReg)){
                    nodes.remove(node);
                    nodeStack.push(node);
                    for (String neighbor : interferenceGraph.get(node)){
                        interferenceGraph.get(neighbor).remove(node);
                    }
                    removed = true;
                    break;
                }
            }

            if(!removed){
                // All nodes have >= K neighbors
                String spillNode = selectSpillNode(nodes);
                nodes.remove(spillNode);
                nodeStack.push(spillNode);
                for (String neighbor : interferenceGraph.get(spillNode)){
                    interferenceGraph.get(neighbor).remove(spillNode);
                }
            }
        }
    }


    private String selectSpillNode(Set<String> nodes){
        String spillNode = null;
        int minCost = Integer.MAX_VALUE;
        for(String node : nodes){
            int cost = spillCosts.getOrDefault(node, Integer.MAX_VALUE);
            if(cost < minCost){
                minCost = cost;
                spillNode = node;
            }
        }
        return spillNode;
    }

    private void select(){
        interferenceGraph = oldInterferenceGraph;
        Queue<String> availableRegisters = new LinkedList<>();
        for (int i = 0; i < numberSavedReg; i++){
            availableRegisters.add("$s"+i);
        }

        while (!nodeStack.isEmpty()){
            String node = nodeStack.pop();


            for (String neighbor : interferenceGraph.get(node)){
                System.out.println(String.format("Checking neighbor: %s of %s", neighbor, node));
                if(registerAssignment.containsKey(neighbor)){
                    availableRegisters.remove(registerAssignment.get(neighbor));
                    System.out.println(String.format("Removing: %s of %s", registerAssignment.get(neighbor), neighbor));
                }
            }

            if(!availableRegisters.isEmpty()){
                String register = availableRegisters.iterator().next();
                System.out.println(String.format("Assigning Registers: %s", register));
                registerAssignment.put(node, register);
            } else {
                registerAssignment.put(node, String.format("Spill_%s", node));
            }
        }
    }
}
