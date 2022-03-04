package net.artux.mathc.data;

import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.ui.DataChangeListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DataModelImpl implements DataModel{

    private Solution solution;
    private CountSolution countSolution;
    private boolean countResult;
    private final DataChangeListener dataChangeListener;
    private final Timer timer;
    private boolean isTimerRunning;
    private TimerTask lastTask;

    private Map<String, Double> values;

    public DataModelImpl(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
        timer = new Timer();
    }

    // задает новое выражение для преобразования
    public void setExpression(Expression expression) {
        solution = new Solution(expression);
        countSolution = null;
        values = new HashMap<>();
        for (ExpressionPart part :
                expression.getContent()) {
            if (!part.isCommand() && !part.isBracket())
                values.put(part.getValue(), 0d); // задаем переменные со значением 0
        }
        dataChangeListener.updateInputExpression(expression);
        updateSolution(solution);
    }

    public void setValue(String key, Double d) {
        values.put(key, d);
        try {
            if (isDone())
                clear();
        } catch (SolutionException ignored) {}
    }

    public Map<String, Double> getValues() {
        return values;
    }

    //обновляет отображение на экране стека и постфиксного выражения
    private void updateSolution(Solution solution) {
        StringBuilder builder = new StringBuilder();
        for (ExpressionPart part : solution.getResultParts()) {
            if (part.isFunction())
                builder.append(" ");
            builder.append(part);
        }
        dataChangeListener.updatePostfix(builder.toString());
        dataChangeListener.updateStack(solution.getStack());
    }

    // с решением или нет
    public void setCountResult(boolean countResult) {
        this.countResult = countResult;
    }

    public void tick() throws Exception {
        Solution solution = getSolution();
        if (!solution.isDone()) {
            solution.tick();
            updateSolution(solution);
        } else if (countSolution == null && countResult) {
            countSolution = new CountSolution(solution.getResultExpression(), values);
            dataChangeListener.updateStack(countSolution.getStack());
            dataChangeListener.updatePostfix(countSolution.getPostfixWithSelection());
        } else if (countResult && !countSolution.isDone()) {
            countSolution.tick();
            dataChangeListener.updateStack(countSolution.getStack());
            dataChangeListener.updatePostfix(countSolution.getPostfixWithSelection());
            if (countSolution.isDone())
                dataChangeListener.updateResult(countSolution.getStack().get(0));
        } else dataChangeListener.error(new SolutionException("Преобразование завершено"));
    }

    public void delayTicks(long tickTime) throws SolutionException {
        if (isTimerRunning)
            stopTicks();
        else if (!isDone()) {
            // запуск таймера
            isTimerRunning = true;
            dataChangeListener.timerStatusChanged(true);
            lastTask = new Repeater(dataChangeListener);
            timer.scheduleAtFixedRate(lastTask, tickTime, tickTime);
        } else throw new SolutionException("Выражение уже преобразовано");
    }

    // остановка таймера
    public void stopTicks() {
        isTimerRunning = false;
        dataChangeListener.timerStatusChanged(false);
        lastTask.cancel();
    }

    //прогоняем все такты пока не получим результат
    public void allTicks() throws Exception {
        while (!isDone())
            tick();
        if (countResult) {
            dataChangeListener.updateStack(countSolution.getStack());
            dataChangeListener.updatePostfix(countSolution.getPostfixWithSelection());
            if (countSolution.isDone())
                dataChangeListener.updateResult(countSolution.getStack().get(0));
        }else updateSolution(solution);

    }

    private Solution getSolution() throws SolutionException {
        if (solution != null)
            return solution;
        else throw new SolutionException("Не задано выражение");
    }

    public void clear() {
        solution = new Solution(solution.getExpression());
        countSolution = null;
        updateSolution(solution);
    }

    class Repeater extends TimerTask {

        private final DataChangeListener dataChangeListener;

        public Repeater(DataChangeListener dataChangeListener) {
            this.dataChangeListener = dataChangeListener;
        }

        @Override
        public void run() {
            try {
                if (!isDone()) {
                    tick();
                }else stopTicks();
            } catch (Exception e) {
                dataChangeListener.error(e);
                stopTicks();
            }
        }
    }

    private boolean isDone() throws SolutionException {
        if (!getSolution().isDone())
            return false;
        else {
            if (countResult) {
                if (countSolution == null)
                    return false;
                return countSolution.isDone();
            }else return true;
        }
    }

}
