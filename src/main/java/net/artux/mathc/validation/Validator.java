package net.artux.mathc.validation;

import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.LinkedList;
import java.util.List;

public class Validator {

    static List<Catcher> errorCatchers = new LinkedList<>();
    static {
        errorCatchers.add(new Catcher("Пустое скобочное выражение", (part1, part2) -> part1.isLeftBracket() && part2.isRightBracket()));
        errorCatchers.add(new Catcher("Две операции подряд", (part1, part2) -> part1.isCommand() && part2.isCommand() && !part2.isFunction()));
        errorCatchers.add(new Catcher("После функции отсутствует скобка", (part1, part2) -> part1.isFunction() && !part2.isLeftBracket()));
        errorCatchers.add(new Catcher("После открывающей скобки операция", (part1, part2) -> part1.isLeftBracket() && part2.isCommand() && !part2.isFunction()));
        errorCatchers.add(new Catcher("Перед закрывающей скобкой операция или функция", (part1, part2) -> part2.isRightBracket() && (part1.isCommand() || part1.isFunction())));
        errorCatchers.add(new Catcher("Отсутствует операция", (part1, part2) -> {
            boolean isBrackets = part1.isLeftBracket() || part2.isRightBracket();
            return !part1.isCommand() && !part2.isCommand() && !isBrackets;
        }));
        errorCatchers.add(new Catcher("Отсутствует операция перед функцией", (part1, part2) -> {
            boolean isBracket = part1.getValue().equals("(");
            return !part1.isCommand() && !isBracket && part2.isCommand() && part2.isFunction();
        }));
    }

    // возвращает true, если выражение верное, иначе false или вызывает ошибку
    public static boolean isValid(Expression expression) throws ValidationException {
        if (expression.getContent().size()>2) { // проверка на размер
            int brackets = 0; // если счетчик для скобок меньше нуля, то нарушена скоб структура

            ExpressionPart previousPart = expression.getContent().get(0);
            if (previousPart.isLeftBracket())
                brackets++;
            else if (previousPart.isRightBracket())
                brackets--;

            if (previousPart.isCommand() && !previousPart.isFunction())
                throw new ValidationException("Выражение не может начинаться с операции");

            for (int i = 1; i < expression.getContent().size(); i++) {
                ExpressionPart current = expression.getContent().get(i);
                for (Catcher errorCatcher : errorCatchers){
                    // для проверки на большинство ошибок достаточно проверки двух след друг за другом частей
                    if (errorCatcher.isError(previousPart, current))
                        throw new ValidationException(errorCatcher.errorName);
                }

                if (current.isLeftBracket())
                    brackets++;
                else if (current.isRightBracket())
                    brackets--;

                if (brackets<0)
                    throw new ValidationException("Нарушена скобочная структура");

                previousPart = current;
            }

            if (previousPart.isCommand())
                throw new ValidationException("Выражение не может кончаться операцией или функцией");

            if (brackets!=0)
                throw new ValidationException("Количество левых скобок не соответствует правым");

            return true;
        }
        else throw new ValidationException("Выражение слишком короткое");
    }

    static class Catcher {
        String errorName;
        ErrorChecker errorChecker;

        public Catcher(String errorName, ErrorChecker errorChecker) {
            this.errorName = errorName;
            this.errorChecker = errorChecker;
        }

        public boolean isError(ExpressionPart part1, ExpressionPart part2){
            return errorChecker.isError(part1, part2);
        }

        interface ErrorChecker {
            boolean isError(ExpressionPart part1, ExpressionPart part2);
        }
    }

}
