package com.sokoban;

/***
 * Klasa zawiera testy zwiazane z etapem 2 projektu
 */
public class Etap2 {

    /***
     * ETAP 2 - test odczytu parametrow z pliku
     */
    public void testParametersFromFile() {
        Config parametry = new Config(false);
        //ODCZYT Z PLIKU, jesli z serwera to parametry.readServer()

        //TEST PARAMETROW
        System.out.println("Sokoban - program wyświetla parametry oraz przykładowy poziom");
        System.out.println("---Test odczytu parametrów z pliku tekstowego config.properties z katalogu resources---");
        System.out.println("Liczba poziomow: "+parametry.get_number_of_levels());
        System.out.println("Poczatkowa ilosc punktow po rozpoczeciu poziomu: "+parametry.get_points_initial());
        System.out.println("Strata punktowa po kazdej sekundzie gry: "+parametry.get_points_decrease_one_second());
        System.out.println("Strata punktowa po kazdym ruchu: "+parametry.points_decrease_one_move());
        System.out.println("Wartosc puntow po poziomie, powyzej ktorej otrzymujemy zadanie czasowe: "+parametry.get_points_bonus_time_level());
        System.out.println("    Zadanie czasowe polega na ukonczeniu poziomu w tyle sekund: "+parametry.get_points_bonus_time_challenge());
        System.out.println("    Kara za niewykonanie zadania jest podzielenie punktow tyle razy: "+parametry.get_points_bonus_time_challenge_points_division());
//        System.out.println("Wartosc punktow po poziomie, ponizej ktorej otrzymujemy mozliwosc cofania: "+parametry.get_points_bonus_undo_move());
        System.out.println("~~~~Wartosci bonusow otrzymywanych na polu bonusowym~~~~");
        System.out.println("    Cofniecie licznika czasu o tyle sekund: "+parametry.get_bonus_time_rewind_seconds());
        System.out.println("    Cofniecie licznika ruchow o tyle: "+parametry.get_bonus_move_counter_rewind());
        System.out.println("    Pomnozenie punktow za poziom tyle razy: "+parametry.get_bonus_points_multiplier());
        System.out.println("    Kara - strata tylu punktow: "+parametry.get_penalty_lose_points());
        System.out.println("    Kara - dodanie takiej liczby sekund do czasu: "+parametry.get_penatly_add_time());
        System.out.println("~~~~Prawdopodobienstwa otrzymania bonusow po wejsciu na pole bonusowe, sumuja sie do 10~~~~");
        System.out.println("    Cofniecie czasu: "+parametry.get_probability_bonus_time_rewind());
        System.out.println("    Cofniecie licznika ruchow: "+parametry.get_probability_bonus_move_counter_rewind());
        System.out.println("    Pomnozenie punktow: "+parametry.get_probability_bonus_points_multiplier());
//        System.out.println("    Mozliwosc cofania na kolejnym poziomie: "+parametry.get_probability_bonus_undo_move());
        System.out.println("    Strata punktow: "+parametry.get_probability_penalty_lose_points());
        System.out.println("    Strata czasu: "+parametry.get_probability_penalty_add_time());
        System.out.println("Informacje o serwerze:");
        System.out.println("IP: "+parametry.get_server_ip());
        System.out.println("port: "+parametry.get_server_port());
        System.out.println("Koniec testu odczytu parametrów");
        System.out.println(" ");
    }

    /***
     * ETAP 2 - test odczytu mapy z pliku
     */
    public void testLevelsFromFile() {
        System.out.println("poziom 1-mapa duza 10x10");
        Level mapaDuza = new Level();
        mapaDuza.readLevel(1,false);

        System.out.println("wierszy:"+ mapaDuza.getRows());
        System.out.println("kolumn:"+mapaDuza.getColumns());
        for(int wiersz=0;wiersz<mapaDuza.getRows();wiersz++){
            for(int kolumna=0;kolumna<mapaDuza.getColumns();kolumna++){
                System.out.printf("%d",mapaDuza.getInfo(wiersz,kolumna));
            }
            System.out.printf("%n");
        }

        System.out.println("poziom 2-mapa mala 5x7");
        Level mapaMala = new Level();
        mapaMala.readLevel(2,false);
        System.out.println("wierszy:"+ mapaMala.getRows());
        System.out.println("kolumn:"+mapaMala.getColumns());
        for(int wiersz=0;wiersz<mapaMala.getRows();wiersz++){
            for(int kolumna=0;kolumna<mapaMala.getColumns();kolumna++){
                System.out.printf("%d",mapaMala.getInfo(wiersz,kolumna));
            }
            System.out.printf("%n");
        }
    }
}
