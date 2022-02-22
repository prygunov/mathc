package net.artux.mathc;

import net.artux.mathc.util.Stack;

public class Operation {

    private final String name;
    private final int priority;
    private final OperationType type;
    private final Computer computer;

    public Operation(String name, int priority, OperationType type, Computer computer) {
        this.name = name;
        this.priority = priority;
        this.type = type;
        this.computer = computer;
    }

    public OperationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Computer getComputer() {
        return computer;
    }

    public int getPriority() {
        return priority;
    }

    public interface Computer{

        double compute(Stack<Double> stack) throws Exception;

    }

}
