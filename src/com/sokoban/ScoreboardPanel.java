package com.sokoban;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Klasa opisujaca wyglad panelu z tabela wynikow
 */
public class ScoreboardPanel extends JPanel {
    /** Tabela wynikow */
    JTable tabela;
    /** Dane wyswietlane w poszczegolnych komorkach tabeli */
    String[][] dane = {
            { "1.", " ", " ", " " , " " },
            { "2.", " ", " ", " " , " " },
            { "3.", " ", " ", " " , " " },
            { "4.", " ", " ", " " , " " },
            { "5.", " ", " ", " " , " " },
            { "6.", " ", " ", " " , " " },
            { "7.", " ", " ", " " , " " },
            { "8.", " ", " ", " " , " " },
            { "9.", " ", " ", " " , " " },
            { "10.", " ", " ", " " , " " },
    };
    /** Naglowki tabeli wynikow */
    String[] naglowki = { "Pozycja", "Imie", "Wynik", "Liczba poziomow", "Data" };
    /** Tabela wynikow */
    JScrollPane sp;
    /** Tabela wynikow */
    DefaultTableModel model;

    /** Konstruktor klasy */
    public ScoreboardPanel() {

        setSize(300, 300);
        setLayout(new GridLayout(3,1));

        model = new DefaultTableModel(dane, naglowki);
        tabela = new JTable(model);

        tabela.setFocusable(false);
        tabela.setRowSelectionAllowed(false);
        tabela.setBackground(UIManager.getColor ( "Panel.background" ));



        sp=new JScrollPane(tabela);

        add(new JLabel("TABELA WYNIKOW",SwingConstants.CENTER));
        add(sp);
        //przycisk powrotu jest dodawany w GameWindow, zeby tam obslugiwac klikniecie

    }

    /**
     * Wyswietla wynik gracza w odpowiednim wierszu tabeli
     * @param pozycja pozycja gracza
     * @param imie nazwa gracza
     * @param wynik wynik gracza
     * @param liczbaPoziomow liczba poziomow rozegranych przez gracza
     */
    public void setResultText(int pozycja, String imie, int wynik, int liczbaPoziomow, String data){

//        model.setValueAt(pozycja+". ", pozycja-1,0);
        model.setValueAt(imie, pozycja,1);
        model.setValueAt(String.valueOf(wynik), pozycja,2);
        model.setValueAt(String.valueOf(liczbaPoziomow), pozycja,3);
        model.setValueAt(data, pozycja,4);
    }

}
