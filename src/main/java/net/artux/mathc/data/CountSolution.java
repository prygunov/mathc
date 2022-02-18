package net.artux.mathc.data;

import net.artux.mathc.Config;
import net.artux.mathc.Operation;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CountSolution {

    private final Stack<Double> stack;
    private final Map<String, Double> values;
    private final Expression expression;
    private boolean done;
    private int i = 0;

    public CountSolution(Expression expression, Map<String, Double> values) {
        stack = new Stack<>();
        this.values = values;
        this.expression = expression;
        done = false;
    }

    public void tick() throws Exception {
        if (isDone())
            throw new SolutionException("Выражение уже преобразовано");

        if (i < expression.getContent().size()) {
            ExpressionPart expressionPart = expression.getContent().get(i++);
            if (expressionPart.isCommand()) {
                Operation operation = Config.supportedOperations.get(expressionPart.getValue());
                if (operation != null) {
                    stack.push(operation
                            .getComputer()
                            .compute(stack));
                } else throw new SolutionException("Операция " + expressionPart.getValue() + " не поддерживается");
            } else {
                Double d = values.get(expressionPart.getValue());
                if (d != null)
                    stack.push(d);
                else throw new SolutionException("Отсутствует значение для " + expressionPart.getValue());
            }
            if (i == expression.getContent().size())
                done = true;
        }
    }

    public Stack<Double> getStack() {
        return stack;
    }

    public boolean isDone() {
        return done;
    }
}
