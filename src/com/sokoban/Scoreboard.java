package com.sokoban;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


/**
 * Klasa opisujaca tabele wynikow
 */
public class Scoreboard {
    /** Tabela wynikow, tabela obiektow klasy Result */
    Result[] wyniki;
    /** Czy gra jest w trybie online */
    boolean online;

    /**
     * Konstruktor klasy tabeli wynikow
     * @param online czy gra jest w trybie online
     */
    public Scoreboard(boolean online) {

        this.online=online;
        wyniki = new Result[10];
        for(int i=0;i<wyniki.length;i++){
            wyniki[i] = new Result("brak", 0,0, "brak");
        }

        reload();
    }

    /** przeladowanie tabeli wynikow - odczyt z pliku lub serwera */
    public void reload() {

//        if(online){
//            //todo odczyt z serwera
//        }else {
//            //odczyt z pliku
//        }
        //nie ma serwera, wiec zawsze z pliku
        try{
            FileInputStream fis = new FileInputStream("resources/wyniki.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            wyniki = (Result[]) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            //blad odczytu, proba stworzenia pustego pliku z wynikami
            System.out.println("Brak pliku z wynikami, tworze pusty plik");
//            e.printStackTrace();
            try{
                FileOutputStream fos = new FileOutputStream("resources/wyniki.ser");

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(wyniki);
                oos.close();
            } catch (IOException err) {
                err.printStackTrace();
            }

        }


    }

    /**
     * Dodaje nowy wynik do tabeli, sortuje tabele i pozostawia 5 najlepszych wynikow - zapisuje je do pliku
     * @param imie nazwa gracza ktory ma byc dodany do tabeli wynikow
     * @param wynik wynik gracza ktory ma byc dodany
     * @param liczba_poziomow liczba poziomow rozegrana przez gracza
     */
    public void dodajWynik(String imie, int wynik, int liczba_poziomow){

        //dodajemy na pozycje 6, potem sortujemy i wywalamy pozycje 6
        // if(online) //jesli bedzie serwer
        //todo wyslanie do serwera
        //else
        //dodanie i zapis w pliku lokalnym

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(date);

        Result[] sortowaneWyniki = new Result[11];
        for(int i=0;i<wyniki.length;i++){
            sortowaneWyniki[i]=wyniki[i];
        }
        sortowaneWyniki[sortowaneWyniki.length-1] = new Result(imie, wynik, liczba_poziomow, strDate);
        //sortowanie wg wynikow, a dokladniej to umieszczanie na nowego na odpowiednia pozycje i przesuwanie starych w dol
        for (int i = sortowaneWyniki.length - 1 ; i > 0 ; i--) {
            Result p = sortowaneWyniki[i];
            Result next =  sortowaneWyniki[i-1];
            if(p.getWynik() > next.getWynik()) {
                // zamiana
                sortowaneWyniki[i] = next;
                sortowaneWyniki[i-1] = p;

            }
        }
        for(int i=0;i<wyniki.length;i++){
            wyniki[i] = sortowaneWyniki [i];
        }

        //zapis do pliku
        try{
            FileOutputStream fos = new FileOutputStream("resources/wyniki.ser");

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(wyniki);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Zwraca nazwe uzytkownika na danej pozycji w tabeli
     * @param pozycja pozycja gracza
     * @return nazwa uzytkownika
     */
    public String getImie(int pozycja){
        return wyniki[pozycja].getImie();
    }

    /**
     * Zwraca wynik gracza na danej pozycji
     * @param pozycja pozycja gracza
     * @return wynik gracza
     */
    public int getWynik(int pozycja){
        return wyniki[pozycja].getWynik();
    }

    /**
     * Zwraca liczbe poziomow gracza na danej pozycji
     * @param pozycja pozycja gracza w tabeli
     * @return liczba poziomow
     */
    public int getLiczbaPoziomow(int pozycja){
        return wyniki[pozycja].getLiczba_poziomow();
    }


    /**
     * Zwraca date gry
     * @param pozycja pozycja gracza w tabeli
     * @return data
     */
    public String getData(int pozycja){
        return wyniki[pozycja].getData();
    }
}
