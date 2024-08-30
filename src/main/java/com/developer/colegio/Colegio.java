package com.developer.colegio;

import javax.swing.JFrame;
import views.Inicio;

public class Colegio {
    public static void main(String[] args) {
        Inicio inicio = new Inicio();
        inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicio.setVisible(true);
    }
}