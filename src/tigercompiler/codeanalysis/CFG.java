package tigercompiler.codeanalysis;

import tiger.irinstruction.*;

import java.util.*;

public class CFG {

    public static class Node {
        private final int number;
        private int loopDepth;
        private List<IRInstruction> instructionList;
        private Set<Node> successors;
        List<Set<String>> inSetList;
        List<Set<String>> outSetList;

        Node(int number) {
            this.number = number;
            this.loopDepth = 0;
            this.instructionList = new ArrayList<>();
            this.successors = new HashSet<>();
            this.inSetList = new ArrayList<>();
            this.outSetList = new ArrayList<>();
        }

        public int getNumber() {
            return number;
        }

        public List<IRInstruction> getInstructionList() {
            return instructionList;
        }

        public Set<Node> getSuccessors() {
            return successors;
        }

        public void addInstruction(IRInstruction instruction) {
            instructionList.add(instruction);
            inSetList.add(new HashSet<>());
            outSetList.add(new HashSet<>());
        }

        public void addSuccessor(Node successor) {
            successors.add(successor);
        }
    }

    //Structure
    private LinkedHashMap<Integer, Node> nodes;
    private final Map<String, Node> labelToNode;
    private int nodeCounter;

    //Function properties
    private final String name;
    private final Set<String> staticVariables;
    private final Set<String> localVariables;
    private final Set<String> functionSignature;

    //Analysis
    private Map<String, Set<String>> interferenceGraph;
    private final Map<Node, Set<Node>> dominators;


    public CFG(String name, Set<String> staticVariables, Set<String> localVariables, Set<String> functionSignature){
        this.nodes = new LinkedHashMap<>();
        this.labelToNode = new HashMap<>();
        this.nodeCounter = 0;
        this.name = name;
        this.staticVariables = staticVariables;
        this.localVariables = localVariables;
        this.dominators = new HashMap<>();
        this.functionSignature = functionSignature;
    }

    public String getName() {
        return name;
    }

    public Node getNewNode(){
        Node node = new Node(nodeCounter++);
        nodes.put(node.getNumber(), node);
        return node;
    }

    public Set<String> getLocalVariables() {
        return localVariables;
    }

    public Set<String> getStaticVariables() {
        return staticVariables;
    }

    public boolean containNode(String label){
        return labelToNode.containsKey(label);
    }

    public Node getNode(String label){
        return labelToNode.get(label);
    }

    public LinkedHashMap<Integer, Node> getNodes(){
        return nodes;
    }

    public void addLabel(String label, Node node){
        labelToNode.put(label, node);
    }

    public void setNodes(LinkedHashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public void addEdge(int from, int to){
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        fromNode.addSuccessor(toNode);
    }

    public Map<String, Set<String>> getInterferenceGraph() {
        return interferenceGraph;
    }

    public void setInterferenceGraph(Map<String, Set<String>> interferenceGraph) {
        this.interferenceGraph = interferenceGraph;
    }

    private void computeDominators(){
        //System.out.println("Computing dominators list");
        Node startNode = nodes.get(0);

        for (Node node : nodes.values()) {
            dominators.put(node, new HashSet<>(nodes.values()));
        }

        dominators.get(startNode).clear();
        dominators.get(startNode).add(startNode);

        boolean changed;

        do {
            changed = false;
            for (Node node : nodes.values()){
                if (node == startNode) continue;

                Set<Node> newDominators = new HashSet<>(nodes.values());
                for (Node pred: getPredecessors(node)){
                    newDominators.retainAll(dominators.get(pred));
                }

                newDominators.add(node);
                if (!newDominators.equals(dominators.get(node))) {
                    dominators.put(node, newDominators);
                    changed = true;
                }
            }
        } while (changed);

    }

    private boolean isDominatedBy(Node node, Node dominator){
        return dominators.get(node).contains(dominator);
    }

    private Set<Node> getPredecessors(Node node){
        //System.out.println("Computing getPredecessors list");
        Set<Node> predecessors = new HashSet<>();
        for (Node potentialPredecessor : nodes.values()){
            if (potentialPredecessor.successors.contains(node)){
                predecessors.add(potentialPredecessor);
            }
        }
        return predecessors;
    }

    public void detectLoops(){
        computeDominators();
        Map<Node, Set<Node>> backEdges = new HashMap<>();

        //System.out.println("Starting Loop Detection");
        for (Node node : nodes.values()){
            for (Node successor : node.successors){
                if(isDominatedBy(node, successor)){
                    backEdges.computeIfAbsent(successor, k -> new HashSet<>()).add(node);
                    System.out.println(String.format("%s -> %s", successor.getNumber(), node.getNumber()));
                }
            }
        }

        //System.out.println("Finished Compute Back Edges");

        for (Node header : backEdges.keySet()){
            //System.out.println("Loop with header " + header.number);
            Set<Node> loopNodes = new HashSet<>();
            Deque<Node> stack = new ArrayDeque<>(backEdges.get(header));

            while (!stack.isEmpty()){
                Node n = stack.pop();
                if (loopNodes.add(n)){
                    stack.addAll(getPredecessors(n));
                    //System.out.println(String.format("Loop node: %s", n.getNumber()));
                }
            }
            loopNodes.add(header);
            //System.out.print("  Nodes: ");
            for (Node loopNode : loopNodes){

                loopNode.loopDepth++;
                //System.out.print(loopNode.number + " ");
            }
        }
    }

    public Map<String, Integer> calculateSpillCost(){
        Map<String, Integer> variableSpillCosts = new HashMap<>();
        int loadCostConstant = 1;
        int storeCostConstant = 2;
        for (Node node : nodes.values()){
            int loadCost = (int) (loadCostConstant * Math.pow(10, node.loopDepth));
            int storeCost = (int) (storeCostConstant * Math.pow(10, node.loopDepth));
            for (int i = 0; i < node.instructionList.size(); i++){
                Set<String> usedVariables = node.instructionList.get(i).getUse();
                Set<String> definedVariables = node.instructionList.get(i).getDef();

                for (String usedVariable : usedVariables){
                    variableSpillCosts.put(usedVariable, (int) (variableSpillCosts.getOrDefault(usedVariable, 0) + loadCost));
                }

                for (String definedVariable : definedVariables){
                    variableSpillCosts.put(definedVariable, (int) (variableSpillCosts.getOrDefault(definedVariable, 0) + storeCost));
                }
            }
        }
        return variableSpillCosts;
    }



    public void printCFG() {
        for (Node node : nodes.values()) {
            //System.out.println("Node: " + node.getNumber());
            for (IRInstruction instr : node.getInstructionList()) {
                //System.out.println("  " + instr);
            }
            for (Node successor : node.getSuccessors()) {
                //System.out.println("  -> " + successor.getNumber());
            }
        }
    }

    public String printCFGAsDOT() {
        StringBuilder dot = new StringBuilder();
        dot.append(String.format("subgraph cluster_%s {\n",name));

        for (Node node : nodes.values()) {
            dot.append("  ").append(name).append(node.getNumber()).append(" [label=\"").append("Block ").append(node.getNumber());

            dot.append("\n");
            for (IRInstruction instr : node.getInstructionList()) {
                dot.append(instr.toString());
            }
            dot.append("\"];\n");

            for (Node successor : node.getSuccessors()) {
                dot.append("  ").append(name).append(node.getNumber()).append(" -> ").append(name).append(successor.getNumber()).append(";\n");
            }
        }

        dot.append("}\n");
        //System.out.print(dot.toString());
        return dot.toString();
    }

    public String printLivenessAnalysis(){
        StringBuilder liveness = new StringBuilder();
        liveness.append(String.format("Liveness of function %s:", name)).append(System.lineSeparator());
        for (Node node : nodes.values()) {
            liveness.append(String.format("Block %s:", node.getNumber())).append(System.lineSeparator());
            List<IRInstruction> instructions = node.getInstructionList();
            for (int i = 0; i < instructions.size(); i++) {
                liveness.append("   IN: ").append(node.inSetList.get(i));
                liveness.append("   OUT: ").append(node.outSetList.get(i));
                liveness.append(instructions.get(i).toString());
            }
            liveness.append(System.lineSeparator());

        }

        return liveness.toString();
    }

    public String printInterferenceGraph(){
        StringBuilder dot = new StringBuilder();
        dot.append(String.format("subgraph cluster_%s {\n",name));

        for (String var : interferenceGraph.keySet()) {
            dot.append("  ").append(var).append(";\n");
            for (String neighbor : interferenceGraph.get(var)) {
                dot.append("  ").append(var).append(" -> ").append(neighbor).append(";\n");
            }
        }

        dot.append("}\n");
        return dot.toString();
    }

}
