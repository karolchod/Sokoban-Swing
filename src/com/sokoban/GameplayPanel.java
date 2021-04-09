package com.sokoban;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Klasa opisujaca ekran gry, zawierajacy gorny panel z informacjami oraz dolny panel wyswietlajacy plansze gry
 */
public class GameplayPanel extends JPanel {
    /** gorny panel okna, informacje dla gracza np czas, liczba ruchow, sterowanie  */
    private PanelInfo panelInfo;
    /** dolny panel okna, widok planszy gry  */
    private PanelMapa panelMapa;
    /** dwa parametry gry, ustawiane jako parametry konstruktora  */
    private int poziom,liczbapoziomow;

    /**
     * Konstruktor klasy GameplayPanel (widok gry)
     * @param online czy gra jest w trybie online
     * @param liczbapoziomow liczba poziomow, ktora ma byc rozegrana
     * @param nickname nazwa uzytkownika podana na poczatku dzialania programu
     */
    public GameplayPanel(boolean online,int liczbapoziomow, String nickname) {

        poziom = 1;
        this.liczbapoziomow = liczbapoziomow;
        panelInfo = new PanelInfo(this.poziom,this.liczbapoziomow, nickname);

        try {
            panelMapa = new PanelMapa(online);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        panelInfo.repaint();
        panelMapa.repaint();
        add(panelInfo, BorderLayout.NORTH);
        add(panelMapa, BorderLayout.CENTER);
        setVisible(true);


    }

    /**
     * Przekazuje informacje o wcisnietym przycisku do dolnego panelu odpowiadajacego za widok gry
     * @param code kod wcisnietego przycisku
     */
    public void keyboard(int code) {
        panelMapa.keyboard(code);
    }

    /**
     * Informuje, czy cel gry jest osiagniety
     * @return boolean, czy cel gry jest osiagniety
     */
    public boolean isFinished() {
        return panelMapa.isFinished();
    }

    /**
     * Informuje, czy gracz otrzymal bonus (podniosl gwiazdke, czyli wszedl na pole bonusowe)
     * @return czy otrzymal bonus
     */
    public boolean otrzymalBonus(){
        return panelMapa.otrzymalBonus();
    }

    /**
     * Metoda sluzaca do aktualizacji calego widoku GameplayPanel, uzywana na poczatku poziomu
     * @param poziom poziom ktory bedzie teraz rozegrany
     * @param online czy gra jest w trybie online
     * @param sumaPunktow suma punktow zdobytych w poprzednich poziomach
     * @param limitCzasu limit czasu zwiazany z przydzieleniem graczowi "zadania punktowego"
     */
    public void reload(int poziom, boolean online, int sumaPunktow, int limitCzasu) {
        panelMapa.reload(poziom,online);
        panelInfo.resetInfo(poziom,sumaPunktow, limitCzasu);
    }

    /**
     * Metoda aktualizujaca liczbe ruchow wyswietlana w panelu gornym (informacyjnym)
     * @param num liczba ruchow do wyswietlenia
     */
    public void setLiczbaRuchow(int num){
        panelInfo.setLiczbaRuchow(num);
    }

    /**
     * Metoda aktualizujaca czas gry wyswietlany w panelu gornym (informacyjnym)
     * @param i czas do wyswietlenia
     */
    public void setTime(String i){
        panelInfo.setTime(i);
    }

}
