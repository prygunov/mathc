package net.artux.mathc.data;

import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.ui.DataChangeListener;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DataModel {

    private Solution solution;
    private CountSolution countSolution;
    private boolean countResult;
    private final DataChangeListener dataChangeListener;
    private final Timer timer;
    private boolean isTimerRunning;
    private TimerTask lastTask;

    private Map<String, Double> values;

    public DataModel(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
        timer = new Timer();
    }

    public void setExpression(Expression expression) {
        solution = new Solution(expression);
        countSolution = null;
        dataChangeListener.updateInputExpression(expression);
        updateSolution(solution);
    }

    public Expression getExpression() throws SolutionException {
        return getSolution().getExpression();
    }

    public void setValues(Map<String, Double> values) {
        this.values = values;
    }

    private void updateSolution(Solution solution) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ExpressionPart part : solution.getResultParts()) {
            stringBuilder.append(part);
        }
        dataChangeListener.updatePostfix(stringBuilder.toString());
        dataChangeListener.updateStack(solution.getStack());
    }

    public void setCountResult(boolean countResult) {
        this.countResult = countResult;
    }

    public void tick() throws Exception {
        Solution solution = getSolution();
        if (!solution.isDone()) {
            solution.tick();
            updateSolution(solution);
        } else if (countSolution == null && countResult) {
            if (values == null)
                throw new SolutionException("Значения не заданы");
            countSolution = new CountSolution(solution.getResultExpression(), values);
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
            isTimerRunning = true;
            lastTask = new Repeater(dataChangeListener);
            timer.scheduleAtFixedRate(lastTask, tickTime, tickTime);
        }
        else throw new SolutionException("Выражение уже преобразовано");
    }

    public void stopTicks() {
        isTimerRunning = false;
        lastTask.cancel();
    }

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
        setExpression(solution.getExpression());
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
