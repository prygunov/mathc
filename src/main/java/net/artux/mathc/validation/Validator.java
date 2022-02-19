package net.artux.mathc.validation;

import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    static List<Catcher> errorCatchers = new ArrayList<>();
    static {
        errorCatchers.add(new Catcher("Две скобки подряд", (part1, part2) -> part1.getValue().equals("(") && part2.getValue().equals(")")));
        errorCatchers.add(new Catcher("Две операции подряд", (part1, part2) -> part1.isCommand() && part2.isCommand() && !part2.isFunction()));
        errorCatchers.add(new Catcher("После функции отсутствует скобка", (part1, part2) -> part1.isFunction() && !part2.getValue().equals("(")));
        errorCatchers.add(new Catcher("После открывающей скобки операция", (part1, part2) -> part1.getValue().equals("(") && part2.isCommand() && !part2.isFunction()));
        errorCatchers.add(new Catcher("Отсутствует операция", (part1, part2) -> {
            boolean isBrackets = part1.getValue().equals("(") || part2.getValue().equals(")");
            return !part1.isCommand() && !part2.isCommand() && !isBrackets;
        }));
        errorCatchers.add(new Catcher("Отсутствует операция перед функцией", (part1, part2) -> {
            boolean isBracket = part1.getValue().equals("(");
            return !part1.isCommand() && !isBracket && part2.isCommand() && part2.isFunction();
        }));
    }

    public static boolean isValid(Expression expression) throws ValidationException {
        if (expression.getContent().size()>0) {
            int leftBrackets = 0;
            int rightBrackets = 0;

            ExpressionPart previousPart = expression.getContent().get(0);
            if (previousPart.getValue().equals("("))
                leftBrackets++;
            else if (previousPart.getValue().equals(")"))
                rightBrackets++;

            for (int i = 1; i < expression.getContent().size(); i++) {
                ExpressionPart current = expression.getContent().get(i);
                for (Catcher errorCatcher : errorCatchers){
                    if (errorCatcher.errorChecker.error(previousPart, current))
                        throw new ValidationException(errorCatcher.error);
                }

                if (current.getValue().equals("("))
                    leftBrackets++;
                else if (current.getValue().equals(")"))
                    rightBrackets++;

                previousPart = current;
            }

            if (rightBrackets != leftBrackets)
                throw new ValidationException("Количество левых скобок не соответствует правым");

            return true;
        }
        return false;
    }

    static class Catcher {
        String error;
        ErrorChecker errorChecker;

        public Catcher(String error, ErrorChecker errorChecker) {
            this.error = error;
            this.errorChecker = errorChecker;
        }
    }

    interface ErrorChecker {
        boolean error(ExpressionPart part1, ExpressionPart part2);
    }

}
