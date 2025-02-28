package it.phabdev.rubricaswing;

import it.phabdev.rubricaswing.swing.MainFrame;

import javax.swing.*;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame());
  }
}
