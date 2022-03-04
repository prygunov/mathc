package net.artux.mathc.data;

import net.artux.mathc.model.Expression;

import java.util.Map;

public interface DataModel {

    void setExpression(Expression expression);
    void setValue(String key, Double d);
    void setCountResult(boolean countResult);
    Map<String, Double> getValues();

    void tick() throws Exception;
    void delayTicks(long tickTime) throws SolutionException;
    void stopTicks();
    void allTicks() throws Exception;
    void clear();

}
