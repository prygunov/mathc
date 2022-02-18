package net.artux.mathc;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static List<Operation> supportedOperations = new ArrayList<>();

    static {
        supportedOperations.add(new Operation("+", stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a+b;
        }));

        supportedOperations.add(new Operation("-", stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a-b;
        }));
        supportedOperations.add(new Operation("*", stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a*b;
        }));
        supportedOperations.add(new Operation("/", stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a/b;
        }));
        supportedOperations.add(new Operation("^", stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return Math.pow(a, b);
        }));
        supportedOperations.add(new Operation("sin", stack -> {
            double a = stack.pop();
            return Math.sin(a);
        }));
        supportedOperations.add(new Operation("cos", stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));

        supportedOperations.add(new Operation("cos", stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));
    }

    public static Operation getOperation(String name) {
        for (Operation o :
                supportedOperations) {
            if (o.getName().equals(name))
                return o;
        }
        return null;
    }
}
