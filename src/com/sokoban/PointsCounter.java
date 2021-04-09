package com.sokoban;

/**
 * Klasa przeliczajaca liczbe punktow na koniec poziomu
 */
public class PointsCounter {
    /**
     * Parametry gry
     */
    Config parametry;

    /**
     * Konstruktor klasy przeliczajacej punkty
     * @param params parametry gry
     */
    public PointsCounter(Config params) {
        parametry=params;
    }

    /**
     * Metoda przeliczajaca punkty wedlug zasad okreslonych w parametrach gry
     * @param time czas w jakim gracz rozegral poziom
     * @param ruchy liczba ruchow wykonanych przez gracza
     * @param bonusMnoznik czy gracz otrzyml bonus "mnozenie punktow"
     * @param bonusStrata czy gracz otrzymal bonus "strata punktow"
     * @param zrobilZadanie czy gracz wykonal zadanie czasowe
     * @return
     */
    public int countPoints(int time,int ruchy, boolean bonusMnoznik, boolean bonusStrata, boolean zrobilZadanie){
        int points = parametry.get_points_initial();
        points-=time*parametry.get_points_decrease_one_second();
        points-=ruchy*parametry.points_decrease_one_move();
        if(bonusMnoznik)
            points=points* parametry.get_bonus_points_multiplier();
        if(bonusStrata)
            points-=parametry.get_penalty_lose_points();
        if(!zrobilZadanie)
            points = points / parametry.get_points_bonus_time_challenge();
        return points;
    }

}
