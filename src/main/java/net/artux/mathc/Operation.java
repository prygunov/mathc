package net.artux.mathc;

import net.artux.mathc.data.SolutionException;

import java.util.Stack;

public class Operation {

    private String name;
    private Computer computer;

    public Operation(String name, Computer computer) {
        this.name = name;
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public Computer getComputer() {
        return computer;
    }

    public interface Computer{

        double compute(Stack<Double> stack) throws Exception;

    }

}
