package tiger.mips32;

public class SystemCall extends Instruction{
    public SystemCall(){
    }

    public String toString(){
        return "\tsyscall" + System.lineSeparator();
    }
}
