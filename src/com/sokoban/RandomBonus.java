package com.sokoban;

/**
 * Klasa losujaca bonus
 */
public class RandomBonus {
    /** Parametry gry */
    private Config parametry;

    /**
     * Konstruktor klasy losujacej bonus
     * @param params parametry gry
     */
    public RandomBonus(Config params) {
        parametry = params;
    }

    /**
     * Klasa losujaca bonus
     * @return wylosowany bonus: 1- cofnacCzas, 2-cofnacLicznikRuchow, 3-mnozycPunkty, 4-odjacPunkty, 5-dodacCzas
     */
    public int losuj(){
//        int bonus=1;
        //1- cofnacCzas, 2-cofnacLicznikRuchow, 3-mnozycPunkty, 4-odjacPunkty, 5-dodacCzas

        int[] tabela = new int[10];

        int j=0;

        for(int i=0;i<parametry.get_probability_bonus_time_rewind();i++){
            tabela[j]=1;
            j++;
        }
        for(int i=0;i<parametry.get_probability_bonus_move_counter_rewind();i++){
            tabela[j]=2;
            j++;
        }
        for(int i=0;i<parametry.get_probability_bonus_points_multiplier();i++){
            tabela[j]=3;
            j++;
        }
        for(int i=0;i<parametry.get_probability_penalty_lose_points();i++){
            tabela[j]=4;
            j++;
        }
        for(int i=0;i<parametry.get_probability_penalty_add_time();i++){
            tabela[j]=5;
            j++;
        }

//        ((Math.random() * (max - min)) + min);
//        int losowa = (int) ((Math.random() * (9 - 0)) + 0);

        int losowa = (int) ((Math.random() * (10 - 0)) + 0);
        if(losowa == 10) losowa = 9;
//        return bonus;
        return tabela[losowa];
    }

}
