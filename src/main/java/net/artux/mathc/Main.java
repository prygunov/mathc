package net.artux.mathc;

import javax.swing.*;

public class Main{

  public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName()); // получение и установка для программы стиля системы
    new Application(); // запуск приложения
  }

}
