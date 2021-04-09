package com.sokoban;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa gornej czesci okna - informacje o czasie gry itp
 */
public class PanelInfo extends JPanel {

    /** Numer aktualnie rozgrywanego poziomu */
    private int poziom,liczbapoziomow;

    JTextArea liczbaRuchow,suma,czas,infoCofnij,numpoziomu,limitczasu, infoPauza,infoWyjscie;

    /**
     * Konstruktor klasy, ustala layout, wyswietlane teksty
     * @param poziom numer aktualnie rozgrywanego poziomu
     * @param liczbapoziomow liczba poziomow do rozegrania
     * @param nickname nazwa uzytkownika
     */
    public PanelInfo(int poziom, int liczbapoziomow, String nickname) {
        this.liczbapoziomow=liczbapoziomow;
        this.poziom=poziom;
        liczbaRuchow = new JTextArea("Liczba ruchow: 0");
        suma= new JTextArea("Suma punktow: 0");
        czas= new JTextArea("Czas 00:00");
        infoCofnij= new JTextArea("gracz: "+nickname);
        numpoziomu= new JTextArea("Poziom "+poziom+" z "+this.liczbapoziomow);
        limitczasu = new JTextArea("Brak limitu czasu");
        infoPauza = new JTextArea("Enter-pauza");
        infoWyjscie= new JTextArea("Esc-menu");

        liczbaRuchow.setBackground(UIManager.getColor ( "Panel.background" ));
        suma.setBackground(UIManager.getColor ( "Panel.background" ));
        czas.setBackground(UIManager.getColor ( "Panel.background" ));
        infoCofnij.setBackground(UIManager.getColor ( "Panel.background" ));
        numpoziomu.setBackground(UIManager.getColor ( "Panel.background" ));
        limitczasu.setBackground(UIManager.getColor ( "Panel.background" ));
        infoPauza.setBackground(UIManager.getColor ( "Panel.background" ));
        infoWyjscie.setBackground(UIManager.getColor ( "Panel.background" ));

        setLayout(new GridLayout(2,4));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
        setPreferredSize(new Dimension(250,50));
        add(suma);
        add(czas);
        add(liczbaRuchow);
        add(infoCofnij);

        add(numpoziomu);
        add(limitczasu);
        add(infoPauza);
        add(infoWyjscie);


    }

    /**
     * Resetuje informacje wyswietlane w panelu, wywolywana na poczatku poziomu
     * @param poziom poziom ktory teraz bedzie rozegrany
     * @param punkty liczba punktow uzyskanych w poprzednich poziomach
     * @param limitCzasu limit czasu zwiazany z zadaniem czasowym
     */
    public void resetInfo(int poziom, int punkty,int limitCzasu){
        liczbaRuchow.setText("Liczba ruchow 0");
        czas.setText("Czas 00:00");
        suma.setText("Suma punktow "+ punkty);
        if(limitCzasu>0){
            limitczasu.setText("Limit czasu: "+String.format("%02d:%02d",limitCzasu/60,limitCzasu%60));
        }else{
            limitczasu.setText("Brak limitu czasu");
        }

    }

    /**
     * Zmiana wyswietlanego czasu gry
     * @param i czas gry do wyswietlenia
     */
    public void setTime(String i){
        czas.setText("Czas " + i);
    }

    /**
     * Zmiana wyswietlanej liczby ruchow
     * @param num liczba ruchow do wyswietlenia
     */
    public void setLiczbaRuchow(int num){
        liczbaRuchow.setText("Liczba ruchow "+num);
//        repaint();
//        liczbaRuchow= new JLabel("Liczba ruchow "+num);

    }

}
