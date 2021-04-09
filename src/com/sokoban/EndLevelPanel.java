package com.sokoban;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa opisujaca ekran wyswietlany po zakonczeniu poziomu
 */
public class EndLevelPanel extends JPanel {

    /** Konstruktor klasy, ustawia układ siatki o wymiarach 6x1, dodaje napis "Ukonczono poziom" na gorze okna  */
    public EndLevelPanel() {
        setSize(300, 300);
        setLayout(new GridLayout(6,1));
        add(new JLabel("Ukonczono poziom",SwingConstants.CENTER));
        //przyciski sa dodawane w GameWindow, bo tam jest listener od przyciskow
        //tresc kolejnych wierszy tez jest zmieniana w GameWindow, bez uzycia metod tylko bezposrednio
    }




}