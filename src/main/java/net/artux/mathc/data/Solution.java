package net.artux.mathc.data;

import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

    private Stack<ExpressionPart> stack;
    private Expression expression;
    List<ExpressionPart> resultExpression;
    boolean done;
    int i = 0;

    public Solution(Expression expression){
        stack = new Stack<>();
        resultExpression = new ArrayList<>();
        this.expression = expression;
        done = false;

    }

    public void tick() throws SolutionException{
        if (isDone())
            throw new SolutionException("Выражение преобразовано");

        if (i<expression.getContent().size()){
            ExpressionPart expressionPart = expression.getContent().get(i++);
            if (expressionPart.isCommand()) {
                switch (expressionPart.getValue()) {
                    case "(":
                        push(expressionPart); // запихуеваем в стек
                        break;
                    case ")":
                        poop(); // достать из стека все, пока не встретим левую скобку
                        break;
                    default: {
                        if (!stack.isEmpty()) {
                            int lastPriority = getPriority(stack.peek());
                            int thisPriority = getPriority(expressionPart);
                            if (lastPriority>=thisPriority) {// если приоритет последней операции в стеке больше или равен текущему
                                poop(getPriority(expressionPart)); // (не смотри) // достаем в результат все пока не встретим
                                push(expressionPart);
                            } else push(expressionPart); // иначе запихуеваем в стек
                        } else push(expressionPart); // запихуеваем в стек
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

    public boolean isDone() {
        return done;
    }

    private void push(ExpressionPart part) {
        stack.push(part);
    }

    int getPriority(ExpressionPart part){
        return switch (part.getValue()) {
            case "*", "/" -> 2;
            case "sin", "cos", "exp" -> 3;
            case "^" -> 4;
            default -> 1;
        };
    }
}
