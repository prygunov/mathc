package net.artux.mathc.data;

import net.artux.mathc.Config;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.util.Stack;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private final Stack<ExpressionPart> stack;
    private final Expression expression;
    private final List<ExpressionPart> resultExpression;
    private boolean done;
    private int i = 0;

    public Solution(Expression expression) {
        stack = new Stack<>();
        resultExpression = new ArrayList<>();
        this.expression = expression;
        done = false;
    }

    public void tick() throws SolutionException { // один такт для преобразования в постфиксную форму
        if (isDone())
            throw new SolutionException("Выражение уже преобразовано");

        if (i < expression.getContent().size()) {
            ExpressionPart expressionPart = expression.getContent().get(i++); // берем часть выражения
            if (expressionPart.isLeftBracket()) {
                push(expressionPart); // если левая скобка - в стек
            } else if (expressionPart.isRightBracket()) {
                if (pop())
                    i--;// если правая скобка - выгружаем стек в очередь до первой левой скобки, остаемся на текущей части с помощью i--
            } else if (expressionPart.isCommand()) {
                if (!stack.isEmpty() && !stack.peek().isLeftBracket()) { // сработает если стек не пуст и указатель стека не на (
                    int lastPriority = getPriority(stack.peek());
                    int thisPriority = getPriority(expressionPart);
                    if (lastPriority >= thisPriority) {// если приоритет последней операции в стеке больше или равен текущему
                        if (pop(getPriority(expressionPart))) {
                            i--; // выгружаем пока не встретим меньший приоритет
                        } else {
                            push(expressionPart);
                        }
                    } else push(expressionPart);
                } else push(expressionPart);
            } else {
                resultExpression.add(expressionPart); // если это переменная - с чистой совестью добавляем в очередь
            }
        } else if (!stack.isEmpty()) { // перебрали все части выражения, приступаем к выгрузке стека в очередь
            resultExpression.add(stack.pop());
            if (stack.isEmpty())
                done = true;
        } else {
            done = true;
        }
    }

    public Stack<ExpressionPart> getStack() {
        return stack;
    }

    public Expression getResultExpression() throws SolutionException {
        if (isDone())
            return new Expression(resultExpression);
        else throw new SolutionException("Выражение еще не преобразовано");
    }

    public List<ExpressionPart> getResultParts() {
        return resultExpression;
    }

    //выгрузка до первой скобки
    //false если выгрузка не произошла - встретили (
    private boolean pop() {
        ExpressionPart part = stack.pop();
        if (part.isLeftBracket()) {
            return false;
        }
        resultExpression.add(part);
        return true;
    }

    // false если выгрузка не произошла - встретили меньший приоритет
    // false если стек оказался пуст после выгрузки
    // true если выгрузка произошла + стек не пуст
    private boolean pop(int priority) {
        ExpressionPart part = stack.pop();
        if (getPriority(part) < priority) {
            return false;
        }
        resultExpression.add(part);
        return !stack.isEmpty();
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

    int getPriority(ExpressionPart part) {
        return Config.supportedOperations.get(part.getValue()).getPriority();
    }
}
