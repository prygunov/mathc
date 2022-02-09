import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

public class DataModel {




    /*public String result(Expression reg){
        String result;
        while(){

        }
    }*/

   /* public boolean isReady(String reg){
        boolean ready = false;
        for (int i = 0; i < reg.length(); i++) {
            if (!ready)
                Arrays.stream(supportedOperations).anyMatch(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return String.valueOf(reg.charAt(i));
                    }
                });
        }
    }*/

    public Expression getPostFixEx(Expression expression) {
        // Перебираем expressionList
        Stack<ExpressionPart> stack = new Stack<>();
        List<ExpressionPart> expression1 = new ArrayList<>();

        expression.getContent().forEach(new Consumer<ExpressionPart>() {



            @Override
            public void accept(ExpressionPart expressionPart) {
                if (expressionPart.isCommand()) {
                    switch (expressionPart.getValue()) {
                        case "(":
                            push(expressionPart);
                            break;
                        case ")":
                            pop();
                            break;
                        default: {
                            if (!stack.isEmpty()) {
                                int lastPriority = getPriority(stack.peek());
                                int thisPriority = getPriority(expressionPart);
                                if (lastPriority>=thisPriority) {
                                    pop(getPriority(expressionPart));
                                    push(expressionPart);
                                }else push(expressionPart);
                            } else push(expressionPart);
                        }
                        break;

                    }
                }else{
                    expression1.add(expressionPart);
                }
            }

            private void pop() {
                for (int i = 0; i < stack.size(); i++) {
                    ExpressionPart part = stack.pop();
                    if (part.getValue().equals("(")) {
                        break;
                    }
                    expression1.add(part);
                }
            }

            private void pop(int priority) {
                for (int i = 0; i < stack.size(); i++) {
                    ExpressionPart part = stack.pop();
                    if (part.getValue().equals("(") || getPriority(part) < priority) {
                        break;
                    }
                    expression1.add(part);
                }
            }

            private void push(ExpressionPart part) {
                stack.push(part);
            }
        });

        while (!stack.isEmpty())
            expression1.add(stack.pop());

        return new Expression(expression1);
    }

    int getPriority(ExpressionPart part){
        switch (part.getValue()){
            case "(":
            case ")":
                return 4;
            case "*":
            case "/":
                return 2;
            case "sin":
            case "cos":
                return 2;
            case "^":
                return 3;
            default:
                return 1;
        }
    }

}
