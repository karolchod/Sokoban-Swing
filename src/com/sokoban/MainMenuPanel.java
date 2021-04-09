package com.sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * Klasa opisujaca wyglad ekranu menu glownego
 */
public class MainMenuPanel extends JPanel {

//    JButton nowaGra, wyniki;

    /**
     * Konstruktor widoku menu glownego
     * Ustala układ siatki o wymiarach 5x1, dodaje tytul gry na gorze
     * Przyciski dodawane sa pozniej, w klasie GameWindow ktora deklaruje obiekt tej klasy
     */
    public MainMenuPanel() {

//        nowaGra=new JButton("Nowa gra");
//        wyniki=new JButton("Wyniki");
        setSize(300, 300);
        setLayout(new GridLayout(5,1));


        add(new JLabel("SOKOBAN",SwingConstants.CENTER));

    }




}
