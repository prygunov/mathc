package net.artux.mathc.data;

import net.artux.mathc.model.Expression;
import net.artux.mathc.ui.DataChangeListener;

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
        Solution solution = getSolution();
        if (!solution.isDone())
            timer.scheduleAtFixedRate(new Repeater(getSolution(), dataChangeListener), tickTime, tickTime);
        else throw new SolutionException("Выражение уже преобразовано");
    }

    public void stopTicks(){
        timer.cancel();
        timer.purge();
    }

    public void allTicks() throws SolutionException {
        while (!solution.isDone())
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

    static class Repeater extends TimerTask {

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
