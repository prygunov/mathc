package net.artux.mathc;

import java.util.Collection;
import java.util.HashMap;

public class Config {

    public static HashMap<String, Operation> supportedOperations = new HashMap<>();

    static {
        supportedOperations.put("+", new Operation("+", 1, OperationType.OPERATOR, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a+b;
        }));

        supportedOperations.put("-",new Operation("-", 1,OperationType.OPERATOR, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a-b;
        }));
        supportedOperations.put("*",new Operation("*", 2, OperationType.OPERATOR,stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a*b;
        }));
        supportedOperations.put("/",new Operation("/", 2,OperationType.OPERATOR, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a/b;
        }));
        supportedOperations.put("^",new Operation("^", 3,OperationType.OPERATOR, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return Math.pow(a, b);
        }));
        supportedOperations.put("sin",new Operation("sin",4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.sin(a);
        }));
        supportedOperations.put("cos",new Operation("cos", 4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));

        supportedOperations.put("exp",new Operation("exp", 4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.exp(a);
        }));

        supportedOperations.put("lg",new Operation("lg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.log10(a);
        }));
    }

    public static Collection<Operation> getSupportedOperations() {
        return supportedOperations.values();
    }
}
