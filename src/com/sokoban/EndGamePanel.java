package com.sokoban;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa opisujaca ekran wyswietlany po zakonczeniu gry
 */
public class EndGamePanel extends JPanel {

//    JButton nowaGra, wyniki;//dodawane w klasie GameWindow, bo tam jest ActionListener

    /** Konstruktor klasy, ustawia układ siatki o wymiarach 4x1, dodaje napis Koniec Gry na gorze okna  */
    public EndGamePanel() {

//        nowaGra=new JButton("Nowa gra");
//        wyniki=new JButton("Wyniki");
        setSize(300, 300);
        setLayout(new GridLayout(4,1));


        add(new JLabel("Koniec gry",SwingConstants.CENTER));
        //pozostały tekst i 2 przyciski są dodawane w klasie GameWindow
    }

}
