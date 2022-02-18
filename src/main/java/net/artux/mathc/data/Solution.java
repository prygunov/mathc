package net.artux.mathc.data;

import net.artux.mathc.Config;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

    private final Stack<ExpressionPart> stack;
    private final Expression expression;
    private final List<ExpressionPart> resultExpression;
    private boolean done;
    private int i = 0;

    public Solution(Expression expression){
        stack = new Stack<>();
        resultExpression = new ArrayList<>();
        this.expression = expression;
        done = false;
    }

    public void tick() throws SolutionException{
        if (isDone())
            throw new SolutionException("Выражение уже преобразовано");

        if (i<expression.getContent().size()){
            ExpressionPart expressionPart = expression.getContent().get(i++);
            if (expressionPart.getValue().equals("(")){
                push(expressionPart);
            }else if (expressionPart.getValue().equals(")"))
                pop();
            else if (expressionPart.isCommand()) {
                if (!stack.isEmpty() && !stack.peek().getValue().equals("(")) {
                    int lastPriority = getPriority(stack.peek());
                    int thisPriority = getPriority(expressionPart);
                    if (lastPriority >= thisPriority) {// если приоритет последней операции в стеке больше или равен текущему
                        pop(getPriority(expressionPart)); // (не смотри) // достаем в результат все пока не встретим
                    }
                }
                push(expressionPart);
            }else{
                resultExpression.add(expressionPart);
            }
        }else if (!stack.isEmpty()){
            resultExpression.add(stack.pop());
            if(stack.isEmpty())
                done = true;
        }else {
            done = true;
        }
    }

    public Stack<ExpressionPart> getStack(){
        return stack;
    }

    public List<ExpressionPart> getResultExpression() {
        return resultExpression;
    }

    private void pop() {
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            ExpressionPart part = stack.pop();
            if (part.getValue().equals("(")) {
                break;
            }
            resultExpression.add(part);
        }
    }

    private void pop(int priority) {
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            if (!stack.peek().getValue().equals("(")) {
                ExpressionPart part = stack.pop();
                if (getPriority(part) < priority) {
                    break;
                }
                resultExpression.add(part);
            }
        }
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean isDone() {
        return done;
    }

    private void push(ExpressionPart part) {
        stack.push(part);
    }

    int getPriority(ExpressionPart part){
        return Config.supportedOperations.get(part.getValue()).getPriority();
    }
}
