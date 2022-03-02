package net.artux.mathc;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Config {

    public static HashMap<String, Operation> supportedOperations = new LinkedHashMap<>();

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
        supportedOperations.put("%",new Operation("%", 2,OperationType.OPERATOR, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a % b;
        }));

        supportedOperations.put("sin",new Operation("sin",4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.sin(a);
        }));
        supportedOperations.put("cos",new Operation("cos", 4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));
        supportedOperations.put("tg",new Operation("tg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.tan(a);
        }));

        supportedOperations.put("asin",new Operation("asin", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.asin(a);
        }));
        supportedOperations.put("acos",new Operation("acos", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.acos(a);
        }));
        supportedOperations.put("atg",new Operation("atg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.atan(a);
        }));

        supportedOperations.put("exp",new Operation("exp", 4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.exp(a);
        }));

        supportedOperations.put("lg",new Operation("lg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.log10(a);
        }));

        supportedOperations.put("sqrt",new Operation("sqrt", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.sqrt(a);
        }));

        supportedOperations.put("cbrt",new Operation("cbrt", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.cbrt(a);
        }));

        supportedOperations.put("abs",new Operation("abs", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.abs(a);
        }));
    }

    public static Collection<Operation> getSupportedOperations() {
        return supportedOperations.values();
    }
}
