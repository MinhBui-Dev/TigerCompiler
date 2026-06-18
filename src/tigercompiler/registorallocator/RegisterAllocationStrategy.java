package tigercompiler.registorallocator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface RegisterAllocationStrategy {
    String getRegister(String variable);
    String getIntermediateRegister();
    void reset();
}
