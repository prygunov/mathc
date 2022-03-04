package net.artux.mathc;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Config {

    public static HashMap<String, Operation> supportedOperations = new LinkedHashMap<>();

    static {
        List<Operation> operations = new LinkedList<>();

        /*
        здесь задаются все поддерживаемые операции: устанавливаются приоритеты,
        тип - функция или обычная операция, и что эта операция производит при вызове

        важно заметить, что при подсчете, в метод compute передается стек, а сам метод уже берет
        нужное ему количество чисел из стека
        */

        operations.add(new Operation("+", 1, OperationType.SIGN, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a+b;
        }));
        operations.add(new Operation("-", 1, OperationType.SIGN, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a-b;
        }));
        operations.add(new Operation("*", 2, OperationType.SIGN, stack -> {
            double a = stack.pop();
            double b = stack.pop();
            return a*b;
        }));

        operations.add(new Operation("/", 2, OperationType.SIGN, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a/b;
        }));
        operations.add(new Operation("^", 3, OperationType.SIGN, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return Math.pow(a, b);
        }));
        operations.add(new Operation("%", 2, OperationType.SIGN, stack -> {
            double b = stack.pop();
            double a = stack.pop();
            return a % b;
        }));

        operations.add(new Operation("sin",4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.sin(a);
        }));
        operations.add(new Operation("cos", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.cos(a);
        }));
        operations.add(new Operation("tg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.tan(a);
        }));

        operations.add(new Operation("asin", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.asin(a);
        }));
        operations.add(new Operation("acos", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.acos(a);
        }));
        operations.add(new Operation("atg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.atan(a);
        }));

        operations.add(new Operation("exp", 4,OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.exp(a);
        }));
        operations.add(new Operation("lg", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.log10(a);
        }));
        operations.add(new Operation("sqrt", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.sqrt(a);
        }));

        operations.add(new Operation("cbrt", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.cbrt(a);
        }));
        operations.add(new Operation("abs", 4, OperationType.FUNCTION, stack -> {
            double a = stack.pop();
            return Math.abs(a);
        }));

        init(operations);
    }

    private static void init(List<Operation> operations) {
        for (Operation operation : operations) {
            supportedOperations.put(operation.getName(), operation);
        }
    }

    public static Collection<Operation> getSupportedOperations() {
        return supportedOperations.values();
    }
}
