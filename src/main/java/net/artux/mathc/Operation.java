package net.artux.mathc;

import net.artux.mathc.data.SolutionException;

import java.util.Stack;

public class Operation {

    private String name;
    private int priority;
    private Computer computer;

    public Operation(String name, int priority, Computer computer) {
        this.name = name;
        this.priority = priority;
        this.computer = computer;
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
