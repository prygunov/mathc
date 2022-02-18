package net.artux.mathc.ui;

import net.artux.mathc.data.Solution;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class InputForm extends JFrame implements DataChangeListener{

    private JPanel root;
    private JTextField inputField;
    private JPanel valuesPanel;
    private JPanel operationsPanel;
    private JButton btnAccept;
    private JButton btnClear;
    private JButton btnDeleteLast;

    private static final String[] values = {"a","b","c","d","e","f","g","h","i","j","(",")"};
    private static final String[] operations = {"+","-","*","/","sin","cos","^","exp"};

    private int leftists = 0;
    private int rightists = 0;
    private Expression exp;

    public InputForm (){
        setContentPane(root);
        setSize(700, 650);
        setLocationByPlatform(true);
        
        
        exp = new Expression(new ArrayList<>());
        
        GridLayout grid = new GridLayout(0,3);
        valuesPanel.setLayout(grid);
        GridLayout grid2 = new GridLayout(0,4);
        operationsPanel.setLayout(grid2);

        for(int i = 0;i < values.length;i++){
            JButton but = new JButton(values[i]);

            valuesPanel.add(but);
            but.addActionListener(e -> {
                try {
                    if(exp.getContent().size() ==0){ //Если элементов ещё нет, создаём первый
                        if(!but.getText().equals(")"))
                        exp.getContent().add(new ExpressionPart(but.getText(),false));
                    }
                    else {
                        var expr = exp.getContent().get(exp.getContent().size() - 1); //берём ссылку на последний ExpressionPart

                        if (expr.isCommand() || expr.getValue().equals("(")  || but.getText().equals(")")) { //Если левый элемент является символом, его необходимо заменить
                            exp.getContent().add(new ExpressionPart(but.getText(), false));
                        } else {
                                expr.Replace(but.getText(), false);
                                RemovingLeftersRighters(expr.getValue());//Счётчик левых и правых скоб
                        }
                    }
                        inputField.setText(exp.toString());
                        AddingLeftersRighters(but.getText());//Счётчик левых и правых скоб

                } catch (Exception ex) {
                    error(ex);
                }
            });
        }

        for(int i = 0; i < operations.length; i++){
            JButton but = new JButton(operations[i]);
            operationsPanel.add(but);
            but.addActionListener(e -> {
                try {
                    if(exp.getContent().size() ==0) return;
                    var expr = exp.getContent().get(exp.getContent().size()-1);
                    if(!expr.isCommand() && !expr.getValue().equals("(") ) {
                        exp.getContent().add(new ExpressionPart(but.getText(), true));
                    }
                    else{
                        RemovingLeftersRighters(expr.getValue());
                        expr.Replace(but.getText(),true);
                    }
                    inputField.setText(exp.toString());
                } catch (Exception ex) {
                    error(ex);
                }
            });
        }

        btnAccept.addActionListener(e -> {
            try{

            }
            catch (Exception ex) {
                error(ex);
            }
        });
        btnClear.addActionListener(e -> {
            try{

            }
            catch (Exception ex) {
                error(ex);
            }
        });
        btnDeleteLast.addActionListener(e -> {
            try{

            }
            catch (Exception ex) {
                error(ex);
            }
        });

        valuesPanel.revalidate();
        valuesPanel.repaint();
    }
    private void RemovingLeftersRighters(String text){
        if(text.equals("(")) leftists--;
        else if(text.equals(")")) rightists--;
    }
    private void AddingLeftersRighters(String text){
        if(text.equals("(")) leftists++;
        else if(text.equals(")")) rightists++;
    }
    @Override
    public void updateSolution(Solution solution) {

    }

    @Override
    public void error(Exception e) {

    }
}
