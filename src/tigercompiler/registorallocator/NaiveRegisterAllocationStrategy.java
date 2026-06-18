package tigercompiler.registorallocator;

import java.util.HashMap;
import java.util.Map;

public class NaiveRegisterAllocationStrategy implements RegisterAllocationStrategy {
    private final Map<String, String> registerMap = new HashMap<>();
    private int registerCounter = 0;

    @Override
    public String getRegister(String variable) {
        return allocateNewRegister();
    }

    @Override
    public String getIntermediateRegister(){
        return allocateNewRegister();
    }

    private String allocateNewRegister() {
        if (registerCounter >= 10) {
            registerCounter = 0;
        }
        String register = "$t" + registerCounter;
        registerCounter++;
        return register;
    }

    @Override
    public void reset(){
        registerMap.clear();
        registerCounter = 0;
    }

}
