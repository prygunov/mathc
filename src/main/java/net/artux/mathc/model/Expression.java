package net.artux.mathc.model;

import java.util.List;

public class Expression {

    private final List<ExpressionPart> content;

    public Expression(List<ExpressionPart> content) {
        this.content = content;
    }

    public List<ExpressionPart> getContent() {
        return content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        getContent().forEach(expressionPart -> builder.append(expressionPart.getValue()));
        return builder.toString();
    }
}
