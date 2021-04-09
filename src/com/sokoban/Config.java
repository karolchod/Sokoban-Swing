package com.sokoban;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



/**
 * Klasa odczytujaca parametry z pliku
 */
public class Config {

    /** Obiekt przechowujacy wszystkie parametry programu */
    private Properties prop;

    /** Konstruktor klasy Config, odczyt parametrow z pliku */
    public Config(boolean online) {

        //System.out.println("config");
        this.prop = new Properties();
        //TODO odczyt z serwera
        //najpierw odczyt z pliku, stamtad wziac ip i port, nastepnie zmiana po kolei wartosci kazdego parametru w obiekcie Properties prop
//        if(online){
//
//        }else{
//
//        }
        System.out.println("Odczyt parametrów z serwera nie jest teraz możliwy");

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            this.prop.load(input);
            System.out.println("Odczytano parametry z pliku");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    /** Zwraca liczbe poziomow */
    public int get_number_of_levels() {
        return Integer.parseInt(this.prop.getProperty("number_of_levels"));
    }

    /** Zwraca poczatkowa liczbe punktow na kazdym z poziomow */
    public int get_points_initial() {
        return Integer.parseInt(this.prop.getProperty("points_initial"));
    }

    /** Zwraca spadek puntowy po kazdej sekundzie gry */
    public int get_points_decrease_one_second() {
        return Integer.parseInt(this.prop.getProperty("points_decrease_one_second"));
    }

    /** Zwraca spadek punktowy po kazdym ruchu */
    public int points_decrease_one_move() {
        return Integer.parseInt(this.prop.getProperty("points_decrease_one_move"));
    }

    /** Zwraca wartosc puntow po poziomie, powyzej ktorej otrzymujemy zadanie czasowe */
    public int get_points_bonus_time_level() {
        return Integer.parseInt(this.prop.getProperty("points_bonus_time_level"));
    }

    /** Zwraca czas w sekundach, w ktorym nalezy ukonczyc poziom podczas zadania czasowego */
    public int get_points_bonus_time_challenge() {
        return Integer.parseInt(this.prop.getProperty("points_bonus_time_challenge"));
    }

    /** Zwraca liczbe, przez jaka dzieli sie liczbe punktow po nie wykonaniu zadania czasowego */
    public int get_points_bonus_time_challenge_points_division() {
        return Integer.parseInt(this.prop.getProperty("points_bonus_time_challenge_points_division"));
    }

    /** Zwraca czas w sekundach, o ktory cofany jest licznik czasu po otrzymaniu bonusu */
    public int get_bonus_time_rewind_seconds() {
        return Integer.parseInt(this.prop.getProperty("bonus_time_rewind_seconds"));
    }

    /** Zwraca liczbe ruchow, ktora jest odejmowana po otrzymaniu bonusu */
    public int get_bonus_move_counter_rewind() {
        return Integer.parseInt(this.prop.getProperty("bonus_move_counter_rewind"));
    }

    /** Zwraca liczbe, przez ktora jest mnozona liczba punktow po otrzymaniu bonusu */
    public int get_bonus_points_multiplier() {
        return Integer.parseInt(this.prop.getProperty("bonus_points_multiplier"));
    }

    /** Zwraca liczbe punktow, ktora jest odejmowana po otrzymaniu kary */
    public int get_penalty_lose_points() {
        return Integer.parseInt(this.prop.getProperty("penalty_lose_points"));
    }

    /** Zwraca czas w sekundach, ktory jest dodawany po otrzymaniu kary */
    public int get_penatly_add_time() {
        return Integer.parseInt(this.prop.getProperty("penatly_add_time"));
    }

    /** Zwraca prawdopodobienstwo otrzymania bonusu cofanie czasu */
    public int get_probability_bonus_time_rewind() {
        return Integer.parseInt(this.prop.getProperty("probability_bonus_time_rewind"));
    }

    /** Zwraca prawdopodobienstwo otrzymania bonusu cofniecie licznika punktow */
    public int get_probability_bonus_move_counter_rewind() {
        return Integer.parseInt(this.prop.getProperty("probability_bonus_move_counter_rewind"));
    }

    /** Zwraca prawdopodobienstwo otrzymania bonusu mnozenie punktow */
    public int get_probability_bonus_points_multiplier() {
        return Integer.parseInt(this.prop.getProperty("probability_bonus_points_multiplier"));
    }

    /** Zwraca prawdopodobienstwo otrzymania kary strata punktow */
    public int get_probability_penalty_lose_points() {
        return Integer.parseInt(this.prop.getProperty("probability_penalty_lose_points"));
    }

    /** Zwraca prawdopodobienstwo otrzymania kary strata czasu */
    public int get_probability_penalty_add_time() {
        return Integer.parseInt(this.prop.getProperty("probability_penalty_add_time"));
    }

    /** Zwraca ip serwera */
    public String get_server_ip() {
        return this.prop.getProperty("server_ip");
    }

    /** Zwraca port serwera */
    public String get_server_port() {
        return this.prop.getProperty("server_port");
    }
}
