import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

    private Stack<ExpressionPart> stack;
    private Expression expression;
    List<ExpressionPart> resultExpression;
    boolean done;
    int i = 0;

    Solution(Expression expression){
        stack = new Stack<>();
        resultExpression = new ArrayList<>();
        this.expression = expression;
        done = false;

    }

    public void tick(){
        if (i<expression.getContent().size()){
            ExpressionPart expressionPart = expression.getContent().get(i++);
            if (expressionPart.isCommand()) {
                switch (expressionPart.getValue()) {
                    case "(":
                        push(expressionPart);
                        break;
                    case ")":
                        poop();
                        break;
                    default: {
                        if (!stack.isEmpty()) {
                            int lastPriority = getPriority(stack.peek());
                            int thisPriority = getPriority(expressionPart);
                            if (lastPriority>=thisPriority) {
                                poop(getPriority(expressionPart));
                                push(expressionPart);
                            }else push(expressionPart);
                        } else push(expressionPart);
                    }
                    break;
                }
            }else{
                resultExpression.add(expressionPart);
            }
        }else if (!stack.isEmpty())
            resultExpression.add(stack.pop());
        else done = true;
    }

    public Stack<ExpressionPart> getStack(){
        return stack;
    }

    public List<ExpressionPart> getResultExpression() {
        return resultExpression;
    }

    private void poop() {
        for (int i = 0; i < stack.size(); i++) {
            ExpressionPart part = stack.pop();
            if (part.getValue().equals("(")) {
                break;
            }
            resultExpression.add(part);
        }
    }

    private void poop(int priority) {
        for (int i = 0; i < stack.size(); i++) {
            ExpressionPart part = stack.pop();
            if (part.getValue().equals("(") || getPriority(part) < priority) {
                break;
            }
            resultExpression.add(part);
        }
    }

    private void push(ExpressionPart part) {
        stack.push(part);
    }

    int getPriority(ExpressionPart part){
        switch (part.getValue()){
            case "(":
            case ")":
                return 5;
            case "*":
            case "/":
                return 2;
            case "sin":
            case "cos":
            case "exp":
                return 3;
            case "^":
                return 4;
            default:
                return 1;
        }
    }
}
