package tigercompiler.codeanalysis;

import tiger.irinstruction.*;

import java.util.*;

public class MyCodeAnalyzer {
    private final List<CFG> cfgList;
    private final IRProgram irProgram;

    public MyCodeAnalyzer(IRProgram irProgram) {
        cfgList = new ArrayList<>();
        this.irProgram = irProgram;
        buildCFG();
        livenessAnalysis();
        buildInterferenceGraph();
    }

    public List<CFG> getCfgList() {
        return cfgList;
    }

    private void buildCFG() {
        List<IRFunction> functionList = this.irProgram.getFunctions();
        IRStaticData staticData = this.irProgram.getData();

        Set<String> staticVars = staticData.getIntVars().keySet();

        for (IRFunction function : functionList) {
            //System.out.println(String.format("Start building CFG for: %s", function.getFunctionName()));
            String name = function.getFunctionName();
            Set<String> localVars = function.getData().getIntVars();
            Set<String> functionSignature = function.getFunctionArgs().keySet();
            CFG cfg = new CFG(name, staticVars, localVars, functionSignature);
            CFG.Node currentNode = null;
            CFG.Node previousNode = null;
            boolean newBLock = true;

            List<IRInstruction> instructions = function.getInstructions();
            for (IRInstruction instruction : instructions) {
                if (instruction instanceof IRLabel label) {
                    String labelName = label.getLabel();
                    if (cfg.containNode(labelName)) {
                        currentNode = cfg.getNode(labelName);
                    } else {
                        currentNode = cfg.getNewNode();
                        cfg.addLabel(labelName, currentNode);

                    }

                    if (previousNode != null) {
                        cfg.addEdge(previousNode.getNumber(), currentNode.getNumber());
                    }
                    currentNode.addInstruction(instruction);
                    previousNode = currentNode;
                    newBLock = false;
                } else {
                    if (newBLock) {
                        CFG.Node newNode = cfg.getNewNode();
                        if (previousNode != null) {
                            cfg.addEdge(previousNode.getNumber(), newNode.getNumber());
                        }
                        currentNode = newNode;
                        previousNode = currentNode;
                        newBLock = false;
                    }

                    currentNode.addInstruction(instruction);

                    if (instruction instanceof IRComparisonBranch || instruction instanceof IRBreak || instruction instanceof IRReturn) {
                        newBLock = true;
                        if (instruction instanceof IRComparisonBranch branch) {
                            String targetLabel = branch.getLabel();
                            CFG.Node targetNode;

                            if (cfg.containNode(targetLabel)) {
                                targetNode = cfg.getNode(targetLabel);
                            } else {
                                targetNode = cfg.getNewNode();
                                cfg.addLabel(targetLabel, targetNode);
                            }

                            cfg.addEdge(currentNode.getNumber(), targetNode.getNumber());

                        } else if (instruction instanceof IRBreak irBreak) {
                            String targetLabel = irBreak.getLabel();
                            CFG.Node targetNode;
                            if (cfg.containNode(targetLabel)) {
                                targetNode = cfg.getNode(targetLabel);
                            } else {
                                targetNode = cfg.getNewNode();
                                cfg.addLabel(targetLabel, targetNode);
                            }

                            cfg.addEdge(currentNode.getNumber(), targetNode.getNumber());
                        }

                        if (instruction instanceof IRBreak || instruction instanceof IRReturn) {
                            previousNode = null;
                        }
                    }
                }
            }
            cfg.detectLoops();
            this.cfgList.add(cfg);
        }
    }

    private void livenessAnalysis(){
        for (CFG cfg : cfgList) {
            //System.out.println(String.format("Start liveness Analysis: %s", cfg.getName()));
            boolean changed;
            LinkedHashMap<Integer, CFG.Node> nodes = cfg.getNodes();
            Set<String> staticData = cfg.getStaticVariables();
            do {
                changed = false;
                for (CFG.Node node : nodes.values()){
                    List<IRInstruction> instructionList = node.getInstructionList();
                    for (int i = instructionList.size()- 1; i >= 0; i--) {
                        Set<String> oldInSet = new HashSet<>(node.inSetList.get(i));
                        Set<String> oldOutSet = new HashSet<>(node.outSetList.get(i));

                        node.outSetList.get(i).clear();
                        if (i == node.getInstructionList().size()-1) {
                            for (CFG.Node successor : node.getSuccessors()) {
                                node.outSetList.get(i).addAll(successor.inSetList.get(0));
                            }
                        } else {
                            node.outSetList.get(i).addAll(node.inSetList.get(i+1));
                        }

                        node.inSetList.get(i).clear();
                        node.inSetList.get(i).addAll(instructionList.get(i).getUse());
                        node.inSetList.get(i).removeAll(staticData);

                        Set<String> outMinusDef = new HashSet<>(node.outSetList.get(i));
                        outMinusDef.removeAll(instructionList.get(i).getDef());
                        node.inSetList.get(i).addAll(outMinusDef);

                        if (!node.inSetList.get(i).equals(oldInSet) || !node.outSetList.get(i).equals(oldOutSet)) {
                            changed = true;
                        }
                    }
                }
            } while (changed);
            cfg.setNodes(nodes);
            //System.out.println(String.format("Finish liveness Analysis: %s", cfg.getName()));
        }
    }

    private void buildInterferenceGraph(){
        for (CFG cfg : cfgList) {
            //System.out.println(String.format("Start building interference graph: %s", cfg.getName()));
            Map<String, Set<String>> interferenceGraph = new HashMap<>();
            Map<Integer, CFG.Node> nodes = cfg.getNodes();
            for (CFG.Node node : nodes.values()) {
                for (int i = 0; i < node.getInstructionList().size(); i++) {
                    Set<String> liveVars = node.outSetList.get(i);

                    for (String liveVar : liveVars) {
                        interferenceGraph.computeIfAbsent(liveVar, k -> new HashSet<>());
                        for (String liveVar2 : liveVars) {
                            if (!liveVar.equals(liveVar2)){
                                interferenceGraph.get(liveVar).add(liveVar2);
                            }
                        }
                    }
                }
            }
            cfg.setInterferenceGraph(interferenceGraph);
            //System.out.println(String.format("Finish building interference graph: %s", cfg.getName()));
        }


    }



}