package com.developer.colegio;

import javax.swing.JFrame;
import views.ListaProfesores;
import views.CrearProfesores;

public class Colegio {
    public static void main(String[] args) {
        ListaProfesores listaProfesores = new ListaProfesores();
        listaProfesores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaProfesores.setVisible(true);
    }
}