package tigercompiler;

import tiger.irinstruction.*;
import tiger.mips32.*;
import tigercompiler.codeanalysis.CFG;
import tigercompiler.registorallocator.BriggsRegisterAllocationStrategy;
import tigercompiler.registorallocator.NaiveRegisterAllocationStrategy;
import tigercompiler.registorallocator.RegisterAllocationStrategy;

import java.util.*;

public class MyMIPSGenerator{

    private final String returnReg = "$ra";
    private final String framePointerReg = "$fp";
    private final String stackPointerReg = "$sp";
    private final String globalPointerReg = "$gp";
    private final List<String> savedRegisters = Arrays.asList("$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7");

    RegisterAllocationStrategy registerAllocationStrategy;

    private final List<Instruction> instructions;
    private HashMap<String, Integer> localVarAddress;

    private final IRProgram program;
    private HashSet<String> staticVars;
    private HashSet<String> localVars;
    private HashMap<String, String> staticArrays;

    private HashMap<String, Integer> localArrays;
    private Set<String> functionArgs;
    private int totalStackFrame;
    private int spillSize;
    private int maxArgs;
    private String strategy;

    private List<String> outSet;
    private List<String> inSet;


    public MyMIPSGenerator(IRProgram program, String strategyType) {
        this.instructions = new ArrayList<>();
        this.program = program;
        this.localVarAddress = new HashMap<>();
        this.localArrays = new HashMap<>();
        this.staticArrays = new HashMap<>();
        this.strategy = strategyType;

        //NEW CODE



        //init static
        staticVars = new HashSet<>(program.getData().getIntVars().keySet());
        Set<String> removed = new HashSet<>();
        for(String var : staticVars){
            if (var.contains("[")) {
                // Use regular expression to find the integer between "[" and "]"
                String[] details = extractArrayDetails(var);
                assert details != null;
                removed.add(var);
                staticArrays.put(details[0], details[1]);
            }
        }
        staticVars.removeAll(removed);
    }

    public List<Instruction> generateMIPS(List<CFG> cfgs) {
        //Initialized Static Data
        this.instructions.add(new Data(program.getData().getIntVars(), program.getData().getFloatVars()));



        //find main function
        int index = 0;
        List<IRFunction> functions = program.getFunctions();
        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getFunctionName().equals("main")){
                index = i;
                break;
            }
        }

        if (strategy.equals("briggs")){
            this.registerAllocationStrategy = new BriggsRegisterAllocationStrategy(cfgs.get(index).getInterferenceGraph(),cfgs.get(index).calculateSpillCost());
        } else {
            this.registerAllocationStrategy = new NaiveRegisterAllocationStrategy();
        }

        irToMIPS(functions.get(index), cfgs.get(index));


        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getFunctionName().equals("main")){
                continue;
            }
            if (registerAllocationStrategy instanceof BriggsRegisterAllocationStrategy){
                registerAllocationStrategy = new BriggsRegisterAllocationStrategy(cfgs.get(i).getInterferenceGraph(), cfgs.get(i).calculateSpillCost());
            }
            irToMIPS(functions.get(i), cfgs.get(i));
        }
        return this.instructions;
    }

    private void initFunction(){
        this.localVarAddress.clear();
        this.localArrays.clear();
        this.totalStackFrame = 0;
        this.maxArgs = 4;
    }

    private void irToMIPS(IRFunction function, CFG cfg) {

        initFunction();

        this.functionArgs = new HashSet<>(function.getFunctionArgs().keySet());


        for (IRInstruction instruction : function.getInstructions()) {
            if (instruction instanceof IRFunctionCall IR){
                if (IR.getArgs().size() > maxArgs){
                    maxArgs = IR.getArgs().size();
                }
            }

            if (instruction instanceof IRProcedureCall IR){
                if (IR.getArgs().size() > maxArgs){
                    maxArgs = IR.getArgs().size();
                }
            }
        }
        this.instructions.add(new Label(function.getFunctionName()));

        mapVarToMemory(function);
        setupStackFrame(function);
        for (IRInstruction instruction : function.getInstructions()){


            translateInstruction(instruction);
        }
    }

    private void mapVarToMemory(IRFunction function) {

        LinkedHashMap<String, String> args = function.getFunctionArgs();

        this.localVars = new HashSet<>(function.getData().getIntVars());

        Set<String> removed = new HashSet<>();
        for (String var : this.localVars){
            if (var.contains("[")){
                String[] details = extractArrayDetails(var);
                assert details != null;
                int size = Integer.parseInt(details[1]);
                this.localArrays.put(details[0], size);
                removed.add(var);
            }
        }

        this.localVars.removeAll(removed);

        int total_array_size = 0;
        for (int array_size: localArrays.values()){
            total_array_size += array_size;
        }


        int variableOffset = 4 * (savedRegisters.size() + total_array_size + this.localVars.size() + 2 + maxArgs);
        if (variableOffset % 8 != 0){
            variableOffset += 4;
        }

        this.totalStackFrame += variableOffset;

        variableOffset -= 4;
        for (Map.Entry<String, String> entry : args.entrySet()){
            String var = entry.getKey();
            localVarAddress.put(var, variableOffset);
            variableOffset -= 4;

        }
        System.out.println(variableOffset);
        variableOffset -= 4;
        System.out.println(variableOffset);
        variableOffset -= 4;
        variableOffset -= 4 * savedRegisters.size();
        System.out.println(variableOffset);
        for (String var : localVars){
            if (args.containsKey(var)){
                continue;
            }
            System.out.println(variableOffset);
            localVarAddress.put(var, variableOffset);
            variableOffset -= 4;
        }

        variableOffset += 4;
        for (String array : localArrays.keySet()){
            localVarAddress.put(array, variableOffset - (localArrays.get(array) * 4));
            variableOffset -= localArrays.get(array) * 4;
            System.out.println(variableOffset);
        }
    }



    private void setupStackFrame(IRFunction function) {

        //Add code to get argument pass through the stack

        //Set frame Pointer to Current Stack Pointer
        //allocate stack space for current frame pointer and return address
        this.instructions.add(new AddIntermediate(stackPointerReg, stackPointerReg, String.format("%d", -(totalStackFrame - (function.getFunctionArgs().size() * 4 )))));

        int offset = this.totalStackFrame - 4;
        offset -= (function.getFunctionArgs().size()*4);

        this.instructions.add(new StoreWordOffset(framePointerReg, stackPointerReg, String.format("%d", offset)));
        offset -= 4;
        this.instructions.add(new StoreWordOffset(returnReg, stackPointerReg, String.format("%d", offset)));
        offset -= 4;
        //Save calling function saved register

        for (String reg : savedRegisters) {
            this.instructions.add(new StoreWordOffset(reg, stackPointerReg, String.format("%d", offset)));
            offset -= 4;
        }

        //Set frame pointer to current stack pointer
        this.instructions.add(new Move(framePointerReg, stackPointerReg));
        this.spillSize = offset + 4;

    }

    private void tearDownStackFrame(){


        //restore save register
        int offset = spillSize;
        for (String reg : savedRegisters) {
            this.instructions.add(new LoadWordOffset(reg, stackPointerReg, String.format("%d", offset)));
            offset += 4;
        }
        //reload saved register

        this.instructions.add(new LoadWordOffset(returnReg,stackPointerReg, String.format("%d", offset)));
        offset += 4;
        this.instructions.add(new LoadWordOffset(framePointerReg,stackPointerReg, String.format("%d", offset)));
        offset += 4;
        this.instructions.add(new AddIntermediate(stackPointerReg,stackPointerReg,String.format("%d",offset)));
        this.instructions.add(new Return(returnReg));
    }

    private void translateInstruction(IRInstruction instruction) {
        if (instruction instanceof IRArrayLoad IR) {
            generateArrayLoadCode(IR);

        } else if (instruction instanceof IRArrayStore IR) {
            generateArrayStoreCode(IR);


        } else if (instruction instanceof IRAssign IR) {
            generateAssignCode(IR);
        } else if (instruction instanceof IRAssignConstant IR) {
            generateAssignConstantCode(IR);


        }else if (instruction instanceof IRAssignArray IR) {
           generateAssignArrayCode(IR);

        }  else if (instruction instanceof IRBinaryOp IR) {

            generateBinaryOpCode(IR);


        } else if (instruction instanceof IRBreak IR) {

            generateLabelCode(IR);

        } else if (instruction instanceof IRComparisonBranch IR) {
           generateComparisonBranchCode(IR);
        } else if (instruction instanceof IRFunctionCall IR) {
          generateFunctionCallCode(IR);

        } else if (instruction instanceof IRLabel IR) {

            this.instructions.add(new Label(IR.getLabel()));

        } else if (instruction instanceof IRProcedureCall IR) {

            generateProcedureCallCode(IR);


        } else if (instruction instanceof IRReturn IR) {

            generateReturnCode(IR);

        }
    }

    private void generateBinaryOpCode(IRBinaryOp irBinaryOp) {
        String left = irBinaryOp.getLeft();
        String right = irBinaryOp.getRight();
        String destination = getRegister(irBinaryOp.getResult());
        String op = irBinaryOp.getOp();

        if (isLiteral(left)){
            left = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(left,irBinaryOp.getLeft()));
        } else {
            left = getRegister(left);
        }

        if (isLiteral(right)){
            right = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(right,irBinaryOp.getRight()));
        } else {
            right = getRegister(right);
        }


        switch (op) {
            case "+":
                this.instructions.add(new Add(destination,left,right));
                break;
            case "-":
                this.instructions.add(new Sub(destination,left,right));
                break;
            case "*":
                this.instructions.add(new Mul(destination,left,right));
                break;
            case "/":
                this.instructions.add(new Div(destination,left,right));
                break;
                case "&":
                    //add instruction for AND
                    this.instructions.add(new Mul(left,left,right));
                    this.instructions.add(new Comparison(destination,"$zero",left));
                    break;
                case "|":
                    //add instruction for or
                    this.instructions.add(new Add(left,left,right));
                    this.instructions.add(new Comparison(destination,"$zero",left));
                    break;
        }

        storeRegisterToVariable(destination, irBinaryOp.getResult());
    }

    private void generateAssignCode(IRAssign irAssign) {
        String target = irAssign.getTarget();
        String source = irAssign.getSource();

        if (isLiteral(source)) {
            target = getRegister(target);
            this.instructions.add(new LoadIntermediate(target, source));
        } else if (localArrays.containsKey(target) && localArrays.containsKey(source)) {
            int size = localArrays.get(target);
            String index = registerAllocationStrategy.getIntermediateRegister();
            target = returnArrayAddressToRegister(target);
            source = returnArrayAddressToRegister(source);
            this.instructions.add(new LoadIntermediate(index, "0"));
            this.instructions.add(new Label(String.format("CopyLoop")));
            String temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadWordOffset(temp, source, "0"));
            this.instructions.add(new StoreWordOffset(temp, target, "0"));
            this.instructions.add(new AddIntermediate(target, target, "4"));
            this.instructions.add(new AddIntermediate(source, source, "4"));
            this.instructions.add(new AddIntermediate(index, index, "1"));
            temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(temp, String.format("%d", size)));
            this.instructions.add(new Branch("!=", index, temp, String.format("CopyLoop")));
            return;
        } else if (staticArrays.containsKey(target) && staticArrays.containsKey(source)) {
            String size = staticArrays.get(target);
            String index = registerAllocationStrategy.getIntermediateRegister();
            target = registerAllocationStrategy.getIntermediateRegister();
            source = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadAddress(target, irAssign.getTarget()));
            this.instructions.add(new LoadAddress(source, irAssign.getSource()));
            this.instructions.add(new LoadIntermediate(index, "0"));
            this.instructions.add(new Label(String.format("CopyLoop")));
            String temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadWordOffset(temp, source, "0"));
            this.instructions.add(new StoreWordOffset(temp, target, "0"));
            this.instructions.add(new AddIntermediate(target, target, "4"));
            this.instructions.add(new AddIntermediate(source, source, "4"));
            this.instructions.add(new AddIntermediate(index, index, "1"));
            temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(temp, String.format("%s", size)));
            this.instructions.add(new Branch("!=", index, temp, String.format("CopyLoop")));
            return;
        } else if (staticArrays.containsKey(source) || staticArrays.containsKey(target)) {
            String size;
            if (staticArrays.get(target) != null) {
                //target is static
                size = staticArrays.get(target);
                target = registerAllocationStrategy.getIntermediateRegister();
                this.instructions.add(new LoadAddress(target, irAssign.getTarget()));
                source = returnArrayAddressToRegister(source);
            } else {
                //source is static
                size = staticArrays.get(source);
                source = registerAllocationStrategy.getIntermediateRegister();
                this.instructions.add(new LoadAddress(source, irAssign.getSource()));
                target = returnArrayAddressToRegister(target);
            }
            String index = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(index, "0"));
            this.instructions.add(new Label(String.format("CopyLoop")));
            String temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadWordOffset(temp, source, "0"));
            this.instructions.add(new StoreWordOffset(temp, target, "0"));
            this.instructions.add(new AddIntermediate(target, target, "4"));
            this.instructions.add(new AddIntermediate(source, source, "4"));
            this.instructions.add(new AddIntermediate(index, index, "1"));
            temp = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(temp, String.format("%s", size)));
            this.instructions.add(new Branch("!=", index, temp, String.format("CopyLoop")));
            return;

        } else {
            target = getRegister(target);
            source = loadVariableIntoRegister(source);
            this.instructions.add(new Add(target, source, "$zero"));
        }



        storeRegisterToVariable(target, irAssign.getTarget());
    }

    private void generateArrayLoadCode(IRArrayLoad irArrayLoad) {
        String result = irArrayLoad.getResult();
        String index = irArrayLoad.getIndex();
        String array = irArrayLoad.getArray();

        if (isLiteral(index)){
            if (this.localVarAddress.containsKey(array)){
                int offset = this.localVarAddress.get(array);
                offset += (Integer.parseInt(index)*4);
                result = getRegister(result);
                this.instructions.add(new LoadWordOffset(result,stackPointerReg,String.format("%d", offset)));
            } else {
                String temp = registerAllocationStrategy.getIntermediateRegister();
                int offset = (Integer.parseInt(index)*4);
                this.instructions.add(new LoadAddress(temp,array));
                result = getRegister(result);
                this.instructions.add(new LoadWordOffset(result,temp,String.format("%d", offset)));
            }
        } else {
            index = getRegister(index);
            this.instructions.add(new PowerTwo(index, index, "2"));
//            this.instructions.add(new Add(index,index,index)); // i x 2
//            this.instructions.add(new Add(index,index,index)); // i x 2
            //add offset from stack
            this.instructions.add(new Add(index,index,returnArrayAddressToRegister(array)));
            result = getRegister(result);
            this.instructions.add(new LoadWordOffset(result,index,"0"));
        }
        storeRegisterToVariable(result, irArrayLoad.getResult());
    }

    private void generateArrayStoreCode(IRArrayStore irArrayStore) {
        String array = irArrayStore.getArray();
        String index = irArrayStore.getIndex();
        String source = irArrayStore.getSource();

        if (isLiteral(index)){
            if (this.localVarAddress.containsKey(array)){
                int offset = this.localVarAddress.get(array);
                offset += (Integer.parseInt(index)*4);
                if (isLiteral(source)){
                    source = registerAllocationStrategy.getIntermediateRegister();
                    this.instructions.add(new LoadIntermediate(source, irArrayStore.getSource()));
                } else {
                    source = getRegister(source);
                }
                this.instructions.add(new StoreWordOffset(source,stackPointerReg,String.format("%d", offset)));
            } else {
                String temp = registerAllocationStrategy.getIntermediateRegister();
                int offset = (Integer.parseInt(index)*4);
                this.instructions.add(new LoadAddress(temp,array));
                if (isLiteral(source)){
                    source = registerAllocationStrategy.getIntermediateRegister();
                    this.instructions.add(new LoadIntermediate(source, irArrayStore.getSource()));
                } else {
                    source = getRegister(source);
                }
                this.instructions.add(new StoreWordOffset(source,temp,String.format("%d", offset)));
            }

        } else {
            index = getRegister(index);
            this.instructions.add(new PowerTwo(index,index,"2"));
            this.instructions.add(new Add(index,index,returnArrayAddressToRegister(array)));
            if (isLiteral(source)){
                source = registerAllocationStrategy.getIntermediateRegister();
                this.instructions.add(new LoadIntermediate(source, irArrayStore.getSource()));
            } else {
                source = getRegister(source);
            }
            this.instructions.add(new StoreWordOffset(source,index,"0"));
        }

    }

    private void generateAssignArrayCode(IRAssignArray irAssignArray) {
        //DONE
        String array = irAssignArray.getTarget();
        int size = Integer.parseInt(irAssignArray.getSize());
        String value = irAssignArray.getValue();

        //Get array base address
        int baseOffset;

        String comment = String.format("#Loading value to store for assign array %s: %s", array, value);
        String temp = registerAllocationStrategy.getIntermediateRegister();
        this.instructions.add(new LoadIntermediate(temp, value, comment));

        if (localVarAddress.containsKey(array)){
            baseOffset = this.localVarAddress.get(array);
            for (int i = 0; i < size; i++){
                this.instructions.add(new StoreWordOffset(temp,stackPointerReg,String.format("%d", baseOffset + i * 4)));
            }
        } else {
            baseOffset = this.localVarAddress.get(array);
            String temp2 = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadAddress(temp2,array));
            for (int i = 0; i < size; i++){
                this.instructions.add(new StoreWordOffset(temp,temp2,String.format("%d", baseOffset + i * 4)));
            }
        }
    }

    private void generateAssignConstantCode(IRAssignConstant irAssignConstant) {
        String value = irAssignConstant.getConstant();
        String target = getRegister(irAssignConstant.getTarget());
        this.instructions.add(new LoadIntermediate(target,value));
        storeRegisterToVariable(target, irAssignConstant.getTarget());
    }

    private void generateComparisonBranchCode(IRComparisonBranch irComparisonBranch) {

        String src = irComparisonBranch.getLeftOperand();
        String target = irComparisonBranch.getRightOperand();
        String op = irComparisonBranch.getOp();
        String label = irComparisonBranch.getLabel();

        if (isLiteral(src)){
            src = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(src,irComparisonBranch.getLeftOperand()));
        } else {
            src = getRegister(src);
        }

        if (isLiteral(target)){
            target = registerAllocationStrategy.getIntermediateRegister();
            this.instructions.add(new LoadIntermediate(target,irComparisonBranch.getRightOperand()));
        } else {
            target = getRegister(target);
        }
        this.instructions.add(new Branch(op, src, target, label));
    }

    private void generateFunctionCallCode(IRFunctionCall irFunctionCall) {
        String functionName = irFunctionCall.getFunctionName();
        String returnArg = getRegister(irFunctionCall.getReturnVar());


        if (functionName.equals("not")) {
            List<String> args = irFunctionCall.getArgs();
            assert args.size() == 1;
            if (isLiteral(args.get(0))) {
                returnArg = registerAllocationStrategy.getIntermediateRegister();
                this.instructions.add(new LoadIntermediate(returnArg,args.get(0)));
            }
            String arg = getRegister(args.get(0));
            this.instructions.add(new Comparison(returnArg, arg, "1"));
            storeRegisterToVariable(returnArg, irFunctionCall.getReturnVar());

        } else {
            ArrayList<String> regs = new ArrayList<>();
            for (String arg : irFunctionCall.getArgs()) {
                if (isLiteral(arg)) {
                    String reg = registerAllocationStrategy.getIntermediateRegister();
                    this.instructions.add(new LoadIntermediate(reg,arg));
                    regs.add(reg);
                } else {
                    regs.add(getRegister(arg));
                }
            }
            this.instructions.add(new FunctionReturn(returnArg, functionName, regs));
            storeRegisterToVariable(returnArg, irFunctionCall.getReturnVar());
        }
    }

    private void generateLabelCode(IRBreak irBreak) {
        this.instructions.add(new Jump(irBreak.getLabel()));
    }

    private void generateProcedureCallCode(IRProcedureCall irProcedureCall) {
        String functionName = irProcedureCall.getFunctionName();
        List<String> args = irProcedureCall.getArgs();
        if (functionName.equals("printi") || functionName.equals("exit")) {
            assert args.size() == 1;
            String arg = args.get(0);

            if (isLiteral(arg)) {
                arg = registerAllocationStrategy.getIntermediateRegister();
                this.instructions.add(new LoadIntermediate(arg, args.get(0)));
            } else {
                arg = getRegister(arg);
            }
            this.instructions.add(new LibCall(arg, functionName));
        } else {
            ArrayList<String> passThrough = new ArrayList<>();
            for (String arg : args) {
                if (isLiteral(arg)) {
                    String reg = registerAllocationStrategy.getIntermediateRegister();
                    this.instructions.add(new LoadIntermediate(reg, arg));
                    passThrough.add(reg);
                } else {
                    passThrough.add(getRegister(arg));
                }

            }
            this.instructions.add(new FunctionCall(irProcedureCall.getFunctionName(), passThrough));
        }

    }

    private void generateReturnCode(IRReturn irReturn) {
        if (!irReturn.getValue().isEmpty()) {
            if (isLiteral(irReturn.getValue())) {
                this.instructions.add(new LoadIntermediate("$v0", irReturn.getValue()));
            } else {
                String value = getRegister(irReturn.getValue());
                this.instructions.add(new Move("$v0", value));
            }
        }
        tearDownStackFrame();
    }

    private String getRegister(String variable){
        String reg;
        if (registerAllocationStrategy instanceof NaiveRegisterAllocationStrategy){
            reg = loadVariableIntoRegister(variable);
        } else {
            reg = loadVariableIntoRegister(variable);
        }
        return reg;
    }


    private String loadVariableIntoRegister(String var){
        String reg;
        if (registerAllocationStrategy instanceof NaiveRegisterAllocationStrategy){
            reg = registerAllocationStrategy.getRegister(var);
            String memoryLocation = getVariableMemoryLocation(var);
            this.instructions.add(new LoadWord(reg,memoryLocation));
        } else {
            reg = registerAllocationStrategy.getRegister(var);
            if (reg == null ){
                reg = registerAllocationStrategy.getIntermediateRegister();
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new LoadWord(reg,memoryLocation));
            } else if (reg.contains("Spill")){
                reg = registerAllocationStrategy.getIntermediateRegister();
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new LoadWord(reg,memoryLocation));
            } else if (this.functionArgs.contains(var)){
                this.functionArgs.remove(var);
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new LoadWord(reg,memoryLocation));
            }

        }
        return reg;
    }

    private void storeRegisterToVariable(String reg, String var){
        //System.out.println(String.format("Storing register %s to location for %s", reg, var));
        if (registerAllocationStrategy instanceof NaiveRegisterAllocationStrategy){
            String memoryLocation = getVariableMemoryLocation(var);
            this.instructions.add(new StoreWord(reg,memoryLocation));
        } else {
            System.out.println(String.format("Trying to save %s from %s", var, reg));
            String register = registerAllocationStrategy.getRegister(var);
            if (register == null ){
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new StoreWord(reg,memoryLocation));
            } else if (register.contains("Spill")){
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new StoreWord(reg,memoryLocation));
            } else if (this.functionArgs.contains(var)){
                String memoryLocation = getVariableMemoryLocation(var);
                this.instructions.add(new StoreWord(reg,memoryLocation));
                this.functionArgs.remove(var);
            }
        }
    }


    //======================================================================================================================
    //HELPER FUNCTION
    //======================================================================================================================
    private String returnArrayAddressToRegister(String array){
        String reg = getRegister(array);
        if (localVarAddress.containsKey(array)){
            int offset = localVarAddress.get(array);
            this.instructions.add(new LoadIntermediate(reg,"0"));
            this.instructions.add(new AddIntermediate(reg,stackPointerReg,"0"));
            this.instructions.add(new AddIntermediate(reg, reg,String.format("%d",offset)));
        } else {
            this.instructions.add(new LoadAddress(reg,array));
        }
        return reg;
    }

    private String getVariableMemoryLocation(String variable){

        if (localVarAddress.containsKey(variable)){
            return String.format("%d($sp)", localVarAddress.get(variable));
        } else {
            return String.format("%s",variable);
        }
    }


    private static String[] extractArrayDetails(String s) {
        int openBracketIndex = s.indexOf('[');
        int closeBracketIndex = s.indexOf(']');

        if (openBracketIndex != -1 && closeBracketIndex != -1 && openBracketIndex < closeBracketIndex) {
            String arrayName = s.substring(0, openBracketIndex);
            String arraySize = s.substring(openBracketIndex + 1, closeBracketIndex);
            return new String[]{arrayName, arraySize};
        }
        return null;
    }

    private boolean isLiteral(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
