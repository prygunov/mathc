package net.artux.mathc;

import java.util.Collection;
import java.util.HashMap;

public class Config {

    public static HashMap<String, Operation> supportedOperations = new HashMap<>();

    static {
        supportedOperations.put("+", new Operation("+", 1, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a+b;
        }));

        supportedOperations.put("-",new Operation("-", 1, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a-b;
        }));
        supportedOperations.put("*",new Operation("*", 2, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a*b;
        }));
        supportedOperations.put("/",new Operation("/", 2, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return b/a;
        }));
        supportedOperations.put("^",new Operation("^", 3, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return Math.pow(b, a);
        }));
        supportedOperations.put("sin",new Operation("sin",4, stack -> {
            double a = stack.pop();
            return Math.sin(a);
        }));
        supportedOperations.put("cos",new Operation("cos", 4, stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));

        supportedOperations.put("exp",new Operation("exp", 4, stack -> {
            double a = stack.pop();
            return Math.exp(a);
        }));

        supportedOperations.put("lg",new Operation("lg", 4, stack -> {
            double a = stack.pop();
            return Math.log10(a);
        }));
    }

    public static Collection<Operation> getSupportedOperations() {
        return supportedOperations.values();
    }
}
