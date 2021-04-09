package com.sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Klasa definiujaca okno gry
 * zarzadzanie wejsciem z myszy i klawiatury, zmiana widokow w oknie
 */
public class GameWindow implements KeyListener, ActionListener {
    /** Aktualnie rozgrywany poziom */
    private int poziom;
    /** Liczba ruchow wykonanych przez gracza w tym poziomie */
    private int liczbaruchow;
    /** Parametry gry */
    private Config parametry;
    /** JFrame bedacy glownym oknem programu */
    private JFrame frame;
    /** Widok menu glownego */
    private MainMenuPanel panelMenu;
    /** Widok konca poziomu */
    private EndLevelPanel panelEndLevel;
    /** Widok konca gry */
    private EndGamePanel panelEndGame;
    /** Widok okna gry - informacje o grze i plansza */
    private GameplayPanel panelGameplay;
    /** Widok tabeli wynikow */
    private ScoreboardPanel panelScoreboard;
    /** Obiekt umozliwiajacy losowanie bonusu wg prawdopodobienstw opisanych w konfiguracji */
    private RandomBonus randombonus;
    /** Obiekt zawierajacy wyniki graczy w tabeli wynikow */
    private Scoreboard tabelaWynikow;
    /**
     * Licznik uzywany do pomiaru czasu rozgrywania poziomu
     * implementacja i sposob dzialania w konstruktorze
     * co sekunde licznik wywoluje wzrost zmiennej time o 1
     */
    Timer SimpleTimer;
    /** Układ glownego okna programu - układ kart, kazdy widok jest wyswietlany jako oddzielna karta */
    CardLayout card;
    /** Kontener zawierajacy widoki ktore moga byc wyswietlane w oknie glownym o ukladzie kart */
    Container c;
    /** Przycisk rozpoczynajacy nowa gre, wyswietlany w widoku menu glownego */
    JButton nowaGra;
    /** Przycisk wyswietlajacy widok tabeli wynikow, wyswietlany w menu glownym */
    JButton wyniki;
    /** Przycisk rozpoczynajacy kolejny poziom, wyswietlany w oknie konca poziomu */
    JButton nastepnyLevel;
    /** Przycisk powrotu do menu wyswietlany w ekranie konca gry */
    JButton koniecGry;
    /** Przycisk wyjscia z programu, wyswietlany w menu glownym */
    JButton wyjscie;
    /** Przycisk wyswietlajacy widok tabeli wynikow, wyswietlany w ekranie konca gry */
    JButton koniecWyniki;
    /** Przycisk powrotu do menu wyswietlany na ekranie tabeli wynikow */
    JButton wynikiPowrot;
    /** Informacje wyswietlane na dole menu glownego - tryb gry, nazwa uzytkownika */
    JTextArea infoMenu;
    /** Informacja o wylosowanym bonusie wyswietlana na ekranie konca poziomu */
    JTextArea bonusInfo;
    /** Informacje o czasie gry i liczbie ruchow, wyswietlane na ekranie konca poziomu */
    JTextArea endInfo1;
    /** Informacje o uzyskanych punktach i sumie punktow, wyswietlane na ekranie konca poziomu */
    JTextArea endInfo2;
    /** Informacja o wyniku koncowym, wyswietlana na ekranie konca gry */
    JTextArea sumaKoncowa;
    /** Informacje o zadaniu czasowym, wyswietlane na ekranie konca poziomu */
    JTextArea endInfoZadanieCzasowe;
    /** Nazwa uzytkownika, podana przez gracza na poczatku gry. Jesli gracz nic nie wpisal, domyslna nazwa to "Player" */
    String nickname;
    /** Czy program pracuje w trybie online */
    boolean online;
    /** Czy program jest w trakcie gry - wlacza lub wylacza obsluge klawiatury */
    boolean wTrakcieGry;
    //bonusy
    /** Czy gracz zdobyl w ostatnim poziomie bonus, na tej podstawie bonus jest losowany lub tez nie */
    boolean otrzymalBonus;
    /** Czy dany bonus zostal wylosowany */
    boolean mnozycPunkty, odjacPunkty, dacZadanie, zrobilZadanie;
    /** Czas rozgrywania poziomu w sekundach */
    int time=0;
    /** Suma punktow uzyskana we wszystkich poziomach */
    int sumaPunktow;
    /** Obiekt umozliwiajacy przeliczanie punktow */
    PointsCounter pointscounter;

    /**
     * Konstruktor okna gry
     */
    public GameWindow() {

        liczbaruchow=0;
        poziom = 1;
        //okienko z pytaniem o tryb
        Object[] tryb = {"Online", "Offline"};
        int n = JOptionPane.showOptionDialog(null,
                "Prosze wybrac tryb gry",
                "Sokoban",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tryb,
                tryb[0]);
        System.out.println("Rozgrywka " + tryb[n]);

        infoMenu = new JTextArea();
        infoMenu.setBackground(UIManager.getColor ( "Panel.background" ));

        nickname = JOptionPane.showInputDialog(frame, "Nazwa uzytkownika:");//okienko z pytaniem o nick

        if(nickname==null||nickname.length()==0) //jesli nic nie wpisal lub wcisnal Cancel
            nickname = "Player";

        if(tryb[n]=="Online"){
            //todo Sprawdzenie dostepnosci serwera
//            online=true;
//            infoMenu.setText("Tryb online - "+nickname+" - polaczony");
            online = false;
            infoMenu.setText("Tryb online - "+nickname+" - brak polaczenia z serwerem, przelaczono na tryb offline");

        }
        else {
            online=false;
            infoMenu.setText("Tryb offline - "+nickname);
        }
        parametry = new Config(online);
        tabelaWynikow = new Scoreboard(online);
        pointscounter = new PointsCounter(parametry);
        randombonus = new RandomBonus(parametry);

        frame = new JFrame();
        panelMenu = new MainMenuPanel();
        panelEndLevel = new EndLevelPanel();
        panelEndGame = new EndGamePanel();
        panelScoreboard = new ScoreboardPanel();
        panelGameplay = new GameplayPanel(online,parametry.get_number_of_levels(), nickname);

        c = frame.getContentPane();
        card = new CardLayout(40, 30);

        c.setLayout(card);

        //konfiguracja przyciskow
        nowaGra = new JButton("Nowa gra");
        wyniki = new JButton("Wyniki");
        nastepnyLevel = new JButton("Dalej");
        wyjscie = new JButton("Wyjscie");
        koniecGry = new JButton("Powrot do menu");
        koniecWyniki = new JButton("Pokaz wyniki");
        wynikiPowrot = new JButton("Powrot do menu");

        nowaGra.addActionListener(this);
        wyniki.addActionListener(this);
        nastepnyLevel.addActionListener(this);
        koniecGry.addActionListener(this);
        wyjscie.addActionListener(this);
        koniecWyniki.addActionListener(this);
        wynikiPowrot.addActionListener(this);

        //dodawanie przyciskow
        panelScoreboard.add(wynikiPowrot);
        panelMenu.add(nowaGra);
        panelMenu.add(wyniki);
        panelMenu.add(wyjscie);
        panelMenu.add(infoMenu);
        panelMenu.setVisible(true);

        //konfiguracja wyswietlanych tekstow
        bonusInfo = new JTextArea();
        endInfoZadanieCzasowe=new JTextArea();
        endInfo1 = new JTextArea();
        endInfo2 = new JTextArea();
        bonusInfo.setBackground(UIManager.getColor ( "Panel.background" ));
        endInfoZadanieCzasowe.setBackground(UIManager.getColor ( "Panel.background" ));
        endInfo1.setBackground(UIManager.getColor ( "Panel.background" ));
        endInfo2.setBackground(UIManager.getColor ( "Panel.background" ));
        bonusInfo.setLineWrap(true);
        endInfoZadanieCzasowe.setLineWrap(true);
        endInfo1.setLineWrap(true);
        endInfo2.setLineWrap(true);
        //dodawanie napisow do ekranu konca poziomu
        panelEndLevel.add(bonusInfo);
        panelEndLevel.add(endInfoZadanieCzasowe);
        panelEndLevel.add(endInfo1);
        panelEndLevel.add(endInfo2);
        panelEndLevel.add(nastepnyLevel);

        //dodawanie tekstow i przyciskow do ekranu konca gry
        sumaKoncowa = new JTextArea();
        sumaKoncowa.setBackground(UIManager.getColor ( "Panel.background" ));
        panelEndGame.add(sumaKoncowa);
        panelEndGame.add(koniecGry);
        panelEndGame.add(koniecWyniki);

        //dodawanie widokow do okna programu
        c.add("menu", panelMenu);
        c.add("mapa", panelGameplay);
        c.add("endLevel", panelEndLevel);
        c.add("endGame",panelEndGame);
        c.add("scoreboard", panelScoreboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //dzialanie zamkniecia okna przyciskiem X w rogu
        frame.setPreferredSize(new Dimension(550, 500));//domyslny rozmiar okna
        frame.setTitle("Sokoban");      //tytul okna, wyswietlany na gorze okna

        frame.pack();
        frame.setVisible(true);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        card.show(c,"menu");//wyswietlamy widok menu glownego

        //licznik sekund, uruchamiany i zatrzymywany na poczatku i koncu rozgrywania poziomu
        //dodaje 1 do zmiennej co sekunde i aktualizuje czas wyswietlany w oknie gry
        SimpleTimer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                time+=1;
                panelGameplay.setTime(String.format("%02d:%02d",time/60,time%60));
//                panelGameplay.setTime((time / 60) + ":" + (time % 60));
            }
        });
//        SimpleTimer.start();
        sumaPunktow=0;
        wTrakcieGry=false;
    }


    /**
     * Metoda obslugujaca zdarzenia z klawiatury po wcisnieciu przycisku znakowego
     * Nie jest wykorzystywana
     */
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("keyTyped: "+e);
    }
    /**
     * Metoda obslugujaca zdarzenia z klawiatury po wcisnieciu przycisku
     * Powoduje przesuniecie postaci, skrzyni, otwieranie drzwi, zaliczanie celu, cofanie ruchu, powrot do menu, okno pauzy
     * jesli po wykonanym ruchu gra jest zakonczona, losuje bonus i przelicza punkty, po czym ustala zadanie czasowe i aktualizuje liczbe punktow, wyswietla panel konca gry
     *
     */
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("keyPressed: "+e);
        int code = e.getKeyCode();
        if(wTrakcieGry){ //zmienna ma wartosc true tylko kiedy gracz widzi ekran gry, na pozostalych ekranach wcisniecia klawiatury sa pomijane
            if(code==KeyEvent.VK_UP||code==KeyEvent.VK_DOWN||code==KeyEvent.VK_RIGHT||code==KeyEvent.VK_LEFT){//wcisniete strzalki, ruch postaci
                liczbaruchow+=1;
                panelGameplay.keyboard(code);
                panelGameplay.setLiczbaRuchow(liczbaruchow);
                if (panelGameplay.isFinished()){ //jesli po tym ruchu jest koniec poziomu (wszystkie skrzynie sa na swoich miejscach)
                    SimpleTimer.stop();//TIMER STOP
                    System.out.println("CZAS KONCOWY: "+time);
                    System.out.println("koniec poziomu");
                    wTrakcieGry=false;
                    mnozycPunkty=false;
                    odjacPunkty=false;
                    //zrobilZadanie=false;
                    otrzymalBonus = panelGameplay.otrzymalBonus();
                    if(otrzymalBonus){//jesli podniosl gwiazdke - wszedl na pole bonusowe
                        int przyznanyBonus=randombonus.losuj();//losujemy bonus
                        if(przyznanyBonus==1){
                            time-=parametry.get_bonus_time_rewind_seconds();
                            bonusInfo.setText("Wylosowano bonus: Cofniecie licznika czasu o "+parametry.get_bonus_time_rewind_seconds()+" sekund ");
                            if(time<0)
                                time = 0;
                        }
                        else if(przyznanyBonus==2){
                            liczbaruchow-=parametry.get_bonus_move_counter_rewind();
                            bonusInfo.setText("Wylosowano bonus: Cofniecie licznika ruchow o "+ parametry.get_bonus_move_counter_rewind());
                        }
                        else if(przyznanyBonus==3){
                            mnozycPunkty=true;
                            bonusInfo.setText("Wylosowano bonus: Pomnozenie punktow "+parametry.get_bonus_points_multiplier()+" razy");
                        }
                        else if(przyznanyBonus==4){
                            odjacPunkty=true;
                            bonusInfo.setText("Wylosowano kare: odjecie "+parametry.get_penalty_lose_points()+" punktow");
                        }
                        else if(przyznanyBonus==5){
                            time+=parametry.get_penatly_add_time();
                            bonusInfo.setText("Wylosowano kare: dodanie "+parametry.get_penatly_add_time()+" sekund");
                        }

                    }else{
                        bonusInfo.setText("Nie przyznano bonusu");
                    }

                    if(dacZadanie){//jesli mial w poprzedniej rundzie zrobic zadanie
                        if(time>parametry.get_points_bonus_time_challenge()){//jesli nie zdazyl w czasie
                            zrobilZadanie=false;
                            endInfoZadanieCzasowe.setText("Nie wykonales zadania czasowego. ");
                        }else{
                            zrobilZadanie=true;
                            endInfoZadanieCzasowe.setText("Wykonales zadanie czasowe. ");
                        }
                    }else{
                        endInfoZadanieCzasowe.setText("");
                    }

                    int nowePunkty = pointscounter.countPoints(time,liczbaruchow, mnozycPunkty,odjacPunkty,zrobilZadanie);//obliczanie punktow

                    if(nowePunkty>parametry.get_points_bonus_time_level()){//ma za duzo punktow, dostaje zadanie
                        dacZadanie=true;
                        endInfoZadanieCzasowe.append(" Nowe zadanie: Musisz wykonac kolejny poziom w "+String.format("%02d:%02d",
                                parametry.get_points_bonus_time_challenge()/60,
                                parametry.get_points_bonus_time_challenge()%60)+
                                ", kara za niewykonanie zadania to podzielenie punktow przez "+
                                parametry.get_points_bonus_time_challenge_points_division());
                    }else{
                        endInfoZadanieCzasowe.append("Nie otrzymano zadania czasowego");
                        dacZadanie=false;
                    }

                    sumaPunktow+=nowePunkty;//dodawanie punktow do sumy

                    //aktualizacja tresci na ekranie
                    endInfo1.setText("czas: "+String.format("%02d:%02d",time/60,time%60)+", "+liczbaruchow+" ruchow" );
                    endInfo2.setText("uzyskano "+nowePunkty+" punktow, w sumie "+sumaPunktow+" punktow");

                    card.show(c,"endLevel");
                    zrobilZadanie=true;
                    otrzymalBonus=false;
                }
            }
            else if (code == KeyEvent.VK_SPACE) { //wcisnieta spacja, cofanie ruchu jesli mozliwe
                System.out.println("cofanie ruchu - nie jest zaimplementowane");
                //CZY JEST PRZYZNANY BONUS COFANIE RUCHU?
                //rezygnujemy z funkci cofania
            }
            else if (code == KeyEvent.VK_ESCAPE) { //wcisnieto Esc, powrot do menu, ROZGRYWKA NIE ZAPISUJE SIE, BEDZIE TRZEBA OD NOWA GRAC
                SimpleTimer.stop();
                time = 0;
                sumaPunktow=0;
                System.out.println("Powrot do menu");
                card.show(c,"menu");
            }
            else if (code == KeyEvent.VK_ENTER){ //wcisnieto ENTER, pauza
                SimpleTimer.stop();

                //okienko z pytaniem o tryb
                Object[] pauza = {"Powrot"};

                int n = JOptionPane.showOptionDialog(null,
                        "Pauza",
                        "Sokoban",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        pauza,
                        pauza[0]);

                System.out.println("Koniec pauzy, wcisnieto: " + pauza[n]);
                SimpleTimer.start();
            }
        }
    }

    /**
     * Metoda obslugujaca zdarzenia z klawiatury po puszczeniu przycisku
     * Nie jest wykorzystywana
     */
    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("keyReleased: "+e);
    }

    /**
     *Obsluga zdazen wywolanych wcisnieciem przycisku na ekranie
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command == "Nowa gra") { //przycisk nowej gry w menu glownym
            System.out.println("klik nowaGra");
//            card.last(c);
            poziom=1;
            liczbaruchow=0;
            sumaPunktow=0;
            panelGameplay.reload(poziom,online,sumaPunktow,0);
            dacZadanie=false;
            zrobilZadanie=true;
            card.show(c,"mapa");
            time=0;
            SimpleTimer.start();
            wTrakcieGry=true;
        }

        if (command == "Wyniki"){   //przycisk pokazania tabeli wynikow w menu glownym
            System.out.println("klik Wyniki");

            tabelaWynikow.reload();
            for(int i=0;i<10;i++){
                panelScoreboard.setResultText(i,tabelaWynikow.getImie(i),tabelaWynikow.getWynik(i) , tabelaWynikow.getLiczbaPoziomow(i), tabelaWynikow.getData(i));
            }
            card.show(c,"scoreboard");
        }

        if(command=="Dalej"){ //przycisk przejscia do kolejnego poziomu na ekranie konca poziomu
            if(poziom == parametry.get_number_of_levels()){ //KONIEC GRY, zamiast kolejnego poziomu przycisk pokazuje ekran konca gry
                System.out.println("koniec gry");
                tabelaWynikow.dodajWynik(nickname, sumaPunktow, parametry.get_number_of_levels());
                sumaKoncowa.setText("WYNIK KONCOWY: "+ sumaPunktow);
                card.show(c,"endGame");
                wTrakcieGry=false;
            }else{// to jeszcze nie koniec gry, pokaz kolejny poziom
                poziom+=1;
                liczbaruchow=0;
                System.out.println("Rozpoczynam kolejny poziom");
                if(dacZadanie){
                    panelGameplay.reload(poziom,online,sumaPunktow,parametry.get_points_bonus_time_challenge());
                }else{
                    panelGameplay.reload(poziom,online,sumaPunktow,0);
                }
                card.show(c,"mapa");
                time=0;
                SimpleTimer.start();
                zrobilZadanie=true;
                wTrakcieGry=true;
            }

        }
        if(command=="Wyjscie"){ //wyjscie z gry, przycisk na ekranie menu glownego

            System.out.println("KONIEC PROGRAMU");

            System.exit(0);
        }
        if(command=="Powrot do menu"){//wyswietla menu glowne, przycisk w oknie po konca gry
            card.show(c,"menu");

        }
        if(command=="Pokaz wyniki"){//wyswietla tabele wynikow, przycisk w oknie po konca gry

            tabelaWynikow.reload(); //odczyt wynikow z pliku lub serwera
            for(int i=0;i<10;i++){   //aktualizacja danych w wyswietlanej tabeli
                panelScoreboard.setResultText(i,tabelaWynikow.getImie(i),tabelaWynikow.getWynik(i) , tabelaWynikow.getLiczbaPoziomow(i), tabelaWynikow.getData(i));
            }
            card.show(c,"scoreboard");//wyswietlenie karty z wynikami

        }
    }
}
