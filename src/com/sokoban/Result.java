package com.sokoban;

import java.io.Serializable;

/**
 * Klasa opisujaca jeden wpis w tabeli wynikow
 */
public class Result implements Serializable {
    /** Zmienna zwiazana z serializacja */
    private static final long serialVersionUID = 1L;
    /** nazwa uzytkownika */
    String imie;
    /** wynik gracza */
    int wynik;
    /** liczba poziomow rozegrana przez gracza */
    int liczba_poziomow;
    /** data uzyskania wyniku */
    String data;

    /**
     * Konstruktor klasy
     * @param imie nazwa uzytkownika
     * @param wynik wynik gracza
     * @param liczba_poziomow liczba rozegranych poziomo
     */
    public Result(String imie, int wynik, int liczba_poziomow, String data) {
        this.imie = imie;
        this.wynik = wynik;
        this.liczba_poziomow = liczba_poziomow;
        this.data = data;
    }

    /**
     * Zwraca imie gracza
     * @return imie gracza
     */
    public String getImie() {
        return imie;
    }

    /**
     * Zwraca wynik gracza
     * @return wynik gracza
     */
    public int getWynik() {
        return wynik;
    }

    /**
     * Zwraca liczbe poziomow rozegrana przez gracza
     * @return liczba poziomow
     */
    public int getLiczba_poziomow() {
        return liczba_poziomow;
    }
    /**
     * Zwraca date gry
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Ustawia parametry opisujace gracza
     * @param imie nazwa uzytkownika
     * @param wynik wynik osiagniety przez gracza
     * @param liczba_poziomow liczba poziomow rozegranych przez gracza
     */
    public void setAll(String imie, int wynik, int liczba_poziomow, String data){
        this.imie=imie;
        this.wynik=wynik;
        this.liczba_poziomow=liczba_poziomow;
        this.data=data;
    }

}
