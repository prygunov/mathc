package net.artux.mathc.data;

import net.artux.mathc.model.Expression;
import net.artux.mathc.ui.DataChangeListener;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class DataModel {

    private Solution solution;
    private final DataChangeListener dataChangeListener;
    private final Timer timer;

    public DataModel(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
        timer = new Timer();
    }

    public Expression getPostFixResult(Expression expression) {
        solution = new Solution(expression);
        try {
            while (!solution.done)
                solution.tick();
            return new Expression(solution.getResultExpression());
        } catch (Exception ignored) {
        }
        return null;
    }

    public void setExpression(Expression expression) {
        solution = new Solution(expression);
        dataChangeListener.updateSolution(solution);
    }

    public void tick() throws SolutionException {
        Solution solution = getSolution();
        solution.tick();
        dataChangeListener.updateSolution(solution);
    }

    public void delayTicks(long tickTime) throws SolutionException {
        timer.scheduleAtFixedRate(new Repeater(getSolution(), dataChangeListener), tickTime, tickTime);
    }

    public void stopTicks(){
        timer.cancel();
        timer.purge();
    }

    public void allTicks() throws SolutionException {
        while (!solution.done)
            solution.tick();
        dataChangeListener.updateSolution(solution);
    }

    private Solution getSolution() throws SolutionException {
        if (solution != null)
            return solution;
        else throw new SolutionException("Не задано выражение");
    }

    public void clear() {
        solution = null;
        dataChangeListener.updateSolution(null);
    }

    class Repeater extends TimerTask {

        private final Solution solution;
        private final DataChangeListener dataChangeListener;

        public Repeater(Solution solution, DataChangeListener dataChangeListener) {
            this.solution = solution;
            this.dataChangeListener = dataChangeListener;
        }

        @Override
        public void run() {
            if (!solution.isDone()) {
                try {
                    solution.tick();
                    dataChangeListener.updateSolution(solution);
                } catch (SolutionException e) {
                    dataChangeListener.error(e);
                }
            }
        }
    }

}
