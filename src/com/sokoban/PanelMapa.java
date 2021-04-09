package com.sokoban;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa dolnej czesci okna - pole gry, sterowanie postacia
 */
//public class PanelMapa extends JPanel implements KeyListener, ActionListener {
public class PanelMapa extends JPanel implements ActionListener {

    /** Numer poziomu */
    private int poziom;
    /** Parametry gry */
    private Config parametry;
    /** Mapa odczytana z pliku */
    private Level mapa;
    /** Tablica obrazow (tekstury gry) */
    private Image images[];
    /** Tablica przechowujaca aktualny status pol mapy */
    private int[][] currentStatus;
    /** Wspolrzedne gracza oraz wspolrzedne, na ktora gracz sie przemieszcza (wykorzystywane przy animacji) */
    int playerX, playerY,newPlayerX,newPlayerY;
    /** Wspolrzedne gracza oraz wspolrzedne, na ktore sie przemieszcza wyrazone w pikselach od krawedzi panelu */
    int playerXres, playerYres,newPlayerXres, newPlayerYres;
    /** Wspolrzedne przesuwanej skrzyni oraz wspolrzedne, na ktora skrzynia sie przemieszcza (wykorzystywane przy animacji) */
    int boxX, boxY,newBoxX,newBoxY;
    /** Wspolrzedne przesuwanej skrzyni oraz wspolrzedne, na ktore sie przemieszcza wyrazone w pikselach od krawedzi panelu */
    int boxXres, boxYres,newBoxXres, newBoxYres;
    /** Aktualny i poprzedni rozmiar okna, wykorzystywane do sprawdzenia czy rozmiar okna zmienił się*/
    int szerokoscres, wysokoscres,prevSzerokosc, prevWysokosc;
    /** Rozmiar jednego pola gry oraz liczba pikseli, o ktora przemieszcza sie obraz podczas animacji*/
    int bokKwadratu, przesuniecie;
    /** Zmienne informujace o stanie programu - czy metoda paintComponent wykonuje sie po raz pierwszy oraz czy trwa animacja */
    boolean firstTime,duringAnimation, duringBoxAnimation;
    /** Timer wykorzystywany do aktualizacji obrazu */
    javax.swing.Timer timer = new javax.swing.Timer(10,this);

    /** Zmienna przechowujaca ilosc pozostalych punktow docelowych, jesli 0 - plansza ukonczona */
    int liczbaCelow=0;

    boolean finished, przyznacBonus;

    /**
     * Konstruktor klasy PanelMapa, opisujacej widok planszy gry
     * @param online czy gra jet w trybie online

     */
    public PanelMapa(boolean online) throws IOException {
        finished = false;
//        this.poziom=poziom;
        poziom=1;
        parametry = new Config(online);
        mapa = new Level();

        mapa.readLevel(poziom,online);
//        setLayout(new GridLayout(1, 1));
//        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Mapa"));
        setBackground(UIManager.getColor ( "Panel.background" ));

        images = new Image[10];
        images[0] = ImageIO.read(getClass().getClassLoader().getResource("textures/podloga.png"));
        images[1] = ImageIO.read(getClass().getClassLoader().getResource("textures/sciana.png"));
//        images[2] = ImageIO.read(getClass().getClassLoader().getResource("textures/gracz.png"));
        images[2] = ImageIO.read(getClass().getClassLoader().getResource("textures/gracz_transparent.png"));
        images[3] = ImageIO.read(getClass().getClassLoader().getResource("textures/skrzynia.png"));
        images[4] = ImageIO.read(getClass().getClassLoader().getResource("textures/cel.png"));
        images[5] = ImageIO.read(getClass().getClassLoader().getResource("textures/aktywator.png"));
        images[6] = ImageIO.read(getClass().getClassLoader().getResource("textures/drzwi.png"));
        images[7] = ImageIO.read(getClass().getClassLoader().getResource("textures/bonus.png"));
        images[8] = ImageIO.read(getClass().getClassLoader().getResource("textures/aktywatorAktywowany.png"));
        images[9] = ImageIO.read(getClass().getClassLoader().getResource("textures/skrzyniaZlota.png"));

        this.currentStatus = new int[mapa.getRows()][mapa.getColumns()];
        for(int i=0;i<currentStatus.length;i++){
            for(int j=0;j<currentStatus[0].length;j++){
                if(mapa.getInfo(i,j)==2) {//jesli gracz to status podloga
                    currentStatus[i][j] = 0;
                    playerX=i;
                    playerY=j;
                    newPlayerX=playerX;
                    newPlayerY=playerY;
                }else if(mapa.getInfo(i,j)==4){//jesli pole to cel
                    currentStatus[i][j]=mapa.getInfo(i,j);
                    liczbaCelow+=1;
                }
                else
                    currentStatus[i][j]=mapa.getInfo(i,j);
            }
        }
//        addKeyListener(this);
        setFocusable(false);
        setFocusTraversalKeysEnabled(false);
//        requestFocus();

        timer.start();
        firstTime=true;
        duringAnimation=false;

        boxX=-1;
        boxY=-1;
        newBoxX=-1;
        newBoxY=-1;

        przyznacBonus=false;
    }

    /**
     * Przeladowanie planszy na poczatku kolejnego poziomu
     * @param poziom poziom do rozegrania
     * @param online czy gra jest w trybie online
     */
    public void reload(int poziom,boolean online){
        liczbaCelow=0;
        finished = false;
        this.poziom=poziom;
//        parametry = new Config();
//        mapa = new Level();
        mapa.readLevel(poziom,online);
        setBackground(UIManager.getColor ( "Panel.background" ));
        this.currentStatus = new int[mapa.getRows()][mapa.getColumns()];
        for(int i=0;i<currentStatus.length;i++){
            for(int j=0;j<currentStatus[0].length;j++){
                if(mapa.getInfo(i,j)==2) {//jesli gracz to status podloga
                    currentStatus[i][j] = 0;
                    playerX=i;
                    playerY=j;
                    newPlayerX=playerX;
                    newPlayerY=playerY;
                }else if(mapa.getInfo(i,j)==4){//jesli pole to cel
                    currentStatus[i][j]=mapa.getInfo(i,j);
                    liczbaCelow+=1;
                }
                else
                    currentStatus[i][j]=mapa.getInfo(i,j);
            }
        }
//        addKeyListener(this);
        setFocusable(false);
        setFocusTraversalKeysEnabled(false);
//        requestFocus();

        timer.start();
        firstTime=true;
        duringAnimation=false;

        boxX=-1;
        boxY=-1;
        newBoxX=-1;
        newBoxY=-1;

        przyznacBonus=false;
    }

    /**
     * Metoda otwierajaca drzwi na mapie, wywolywana po wejsciu na pole Aktywatora (dzwigni)
     */
    private void openDoors(){
        for(int i=0;i<currentStatus.length;i++){
            for(int j=0;j<currentStatus[0].length;j++){
                if(currentStatus[i][j]==6) {//jesli drzwi to zamiana na podloge
                    this.currentStatus[i][j]=0;
                }
            }
        }
    }

    /**
     * Metoda rysujaca obiekty gry na ekranie
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        szerokoscres = getWidth();
        wysokoscres = getHeight();

        if (getWidth() < getHeight())
            bokKwadratu = szerokoscres / mapa.getRows();
        else
            bokKwadratu = wysokoscres / mapa.getColumns();
        if(firstTime){
            newPlayerX=playerX;
            newPlayerY=playerY;
            newPlayerXres=newPlayerX*bokKwadratu;
            newPlayerYres=newPlayerY*bokKwadratu;
            playerXres=playerX*bokKwadratu;
            playerYres=playerY*bokKwadratu;
            firstTime=false;
            prevSzerokosc=getWidth();
            prevWysokosc=getHeight();
            przesuniecie=bokKwadratu/10;
        }

        if(getHeight()!=prevWysokosc||getWidth()!=prevSzerokosc){//okno zmienilo rozmiar, trzeba dostosowac wielkosc postaci
            prevWysokosc=getHeight();
            prevSzerokosc=getWidth();
            playerXres=playerX*bokKwadratu;
            playerYres=playerY*bokKwadratu;
            newPlayerXres=newPlayerX*bokKwadratu;
            newPlayerYres=newPlayerY*bokKwadratu;
        }

        //rysowanie mapy
        for (int i = 0; i < mapa.getRows(); i++) {
            for (int j = 0; j < mapa.getColumns(); j++) {
                int x = i * bokKwadratu, y = j * bokKwadratu;

                if(!((i==boxX&&j==boxY)||(i==newBoxX&&j==newBoxY))){
                    g.drawImage(images[currentStatus[i][j]], x, y, bokKwadratu, bokKwadratu, this);
                }else{
                    g.drawImage(images[0], boxX*bokKwadratu, boxY*bokKwadratu, bokKwadratu, bokKwadratu, this);
                    g.drawImage(images[0], newBoxX*bokKwadratu, newBoxY*bokKwadratu, bokKwadratu, bokKwadratu, this);
                }


            }
        }

        g.drawImage(images[2], playerXres, playerYres, bokKwadratu, bokKwadratu, this);
        if(duringBoxAnimation)
            g.drawImage(images[3], boxXres, boxYres,bokKwadratu,bokKwadratu,this);
    }

    /**
     * Metoda obslugujaca zdarzenia z klawiatury po wcisnieciu przycisku
     * Powoduje przesuniecie postaci, skrzyni, otwieranie drzwi, zaliczanie celu, cofanie ruchu
     * powrot do menu,
     * w przypadku kiedy kolejny cel jest zaliczony sprawdza, czy gra nie powinna byc zakonczona
     */
    public void keyboard(int code) {
//        System.out.println("keyPressed: "+e);
//        int code = e.getKeyCode();
        int delX=0, delY=0;



        if(!duringAnimation){
            if(code==KeyEvent.VK_UP){
                delX=0;
                delY=-1;
                System.out.println("MOVE: up");
            }
            else if(code==KeyEvent.VK_DOWN){
                delX=0;
                delY=1;
                System.out.println("MOVE: down");
            }
            else if(code==KeyEvent.VK_RIGHT){
                delX=1;
                delY=0;
                System.out.println("MOVE: right");
            }
            else if(code==KeyEvent.VK_LEFT){
                delX=-1;
                delY=0;
                System.out.println("MOVE: left");

            }
            else if(code==KeyEvent.VK_SPACE){
                System.out.println("MOVE: space");
            }
            else if(code==KeyEvent.VK_ESCAPE){
                System.out.println("MOVE: esc");
            }


            if(code==KeyEvent.VK_SPACE){
                System.out.println("MOVE: Cofanie ruchu");
            }
            else if (code==KeyEvent.VK_ESCAPE){
                System.out.println("MOVE: Powrot do menu");
            }else{
                switch (currentStatus[playerX+delX][playerY+delY]){
                    case 0:
                        newPlayerX=playerX+delX;
                        newPlayerY=playerY+delY;
                        break;
                    case 3:
                        System.out.println("Skrzynia");

                        if(currentStatus[playerX+delX*2][playerY+delY*2]==0){//jesli za skrzynia jest podloga
                            System.out.println("!!Podloga za skrzynia!!");
                            newPlayerX=playerX+delX;
                            newPlayerY=playerY+delY;


                            boxX=playerX+delX;
                            boxY=playerY+delY;
                            newBoxX=boxX+delX;
                            newBoxY=boxY+delY;

                            boxXres=boxX*bokKwadratu;
                            boxYres=boxY*bokKwadratu;
                            newBoxXres=newBoxX*bokKwadratu;
                            newBoxYres=newBoxY*bokKwadratu;
                            currentStatus[boxX][boxY]=0;
                            currentStatus[newBoxX][newBoxY]=3;

                        }
                        if(currentStatus[playerX+delX*2][playerY+delY*2]==5||currentStatus[playerX+delX*2][playerY+delY*2]==6||currentStatus[playerX+delX*2][playerY+delY*2]==7||currentStatus[playerX+delX*2][playerY+delY*2]==1){
                            //jesli za skrzynia jest podloga, drzwi lub aktywator
                            System.out.println("!!Nie mozna przesunac tej skrzyni!!");

                        }
                        if(currentStatus[playerX+delX*2][playerY+delY*2]==4){//jesli za skrzynia jest cel
                            System.out.println("Osiagnieto cel");
//                            currentStatus[newPlayerX+delX][newPlayerY+delY]=9;
                            newPlayerX=playerX+delX;
                            newPlayerY=playerY+delY;


                            boxX=playerX+delX;
                            boxY=playerY+delY;
                            newBoxX=boxX+delX;
                            newBoxY=boxY+delY;

                            boxXres=boxX*bokKwadratu;
                            boxYres=boxY*bokKwadratu;
                            newBoxXres=newBoxX*bokKwadratu;
                            newBoxYres=newBoxY*bokKwadratu;
                            currentStatus[boxX][boxY]=0;
                            currentStatus[newBoxX][newBoxY]=9;

                            liczbaCelow-=1;
                            if(liczbaCelow==0){
                                System.out.println("!!!!!!!!Wygrana!!!!!!!!!");
                                setBackground(Color.GREEN);
                                finished = true;
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Cel");
                        newPlayerX=playerX+delX;
                        newPlayerY=playerY+delY;
                        break;
                    case 5:
                        System.out.println("Aktywator");
                        newPlayerX=playerX+delX;
                        newPlayerY=playerY+delY;
                        openDoors();
                        currentStatus[newPlayerX][newPlayerY]=8;
                        break;
                    case 6:
                        System.out.println("Drzwi");
                        break;
                    case 7:
                        System.out.println("Zdobyto bonus");
                        newPlayerX=playerX+delX;
                        newPlayerY=playerY+delY;
                        currentStatus[newPlayerX][newPlayerY]=0;
                        przyznacBonus=true;
                        break;
                    case 8:
                        System.out.println("Aktywator po aktywacji");
                        newPlayerX=playerX+delX;
                        newPlayerY=playerY+delY;
                        break;
                    case 9:
                        System.out.println("Skrzynia na celu");
                        break;
                    default:
                        System.out.println("Sciana");

                }
            }

        }

        newPlayerXres=newPlayerX*bokKwadratu;
        newPlayerYres=newPlayerY*bokKwadratu;
    }


    /**
     * Odswiezanie ekranu wywolywane timerem co 10 ms
     * Sprawdza, czy postac powinna byc przemieszczona
     * Jesli tak, przesuwa postac lub skrzynie o 1/10 wielkosci kwadratu dopoki obiekt nie bedzie wyswietlany w odpowiednim miejscu
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        szerokoscres = getWidth();
        wysokoscres = getHeight();

        if (getWidth() < getHeight())
            bokKwadratu = szerokoscres / mapa.getRows();
        else
            bokKwadratu = wysokoscres / mapa.getColumns();
        przesuniecie=bokKwadratu/10;
        duringAnimation=false;
        duringBoxAnimation=false;
//        przesuniecie = 1;
        if(newPlayerXres<playerXres){//gracz za daleko, przemieszczamy w lewo
            duringAnimation=true;
            this.playerXres-=przesuniecie;
            if(newPlayerXres>playerXres)
                this.playerXres=newPlayerXres;
            repaint();
        }
        if(newPlayerXres>playerXres){//gracz za blisko, przemieszzcamy w prawo
            duringAnimation=true;
            this.playerXres+=przesuniecie;
            if(newPlayerXres<playerXres)
                this.playerXres=newPlayerXres;
            repaint();
        }
        if(newPlayerYres<playerYres){//gracz za nisko, przemieszczamy w gore
            this.playerYres-=przesuniecie;
            if(newPlayerYres>playerYres)
                this.playerYres=newPlayerYres;

            duringAnimation=true;
            repaint();
        }
        if(newPlayerYres>playerYres){//gracz za wysoko, przemieszczamy w dol
            this.playerYres+=przesuniecie;
            if(newPlayerYres<playerYres)
                this.playerYres=newPlayerYres;

            duringAnimation=true;
            repaint();
        }
        ///////////
        if(newBoxXres<boxXres){//box za daleko, przemieszczamy w lewo
            duringBoxAnimation=true;
            this.boxXres-=przesuniecie;
            if(newBoxXres>boxXres)
                this.boxXres=newBoxXres;
            repaint();
        }
        if(newBoxXres>boxXres){//box za blisko, przemieszzcamy w prawo
            this.boxXres+=przesuniecie;
            if(newBoxXres<boxXres)
                this.boxXres=newBoxXres;
            repaint();
            duringBoxAnimation=true;
        }
        if(newBoxYres<boxYres){//box za nisko, przemieszczamy w gore
            this.boxYres-=przesuniecie;
            if(newBoxYres>boxYres)
                this.boxYres=newBoxYres;
            duringBoxAnimation=true;
            repaint();
        }
        if(newBoxYres>boxYres){//box za wysoko, przemieszczamy w dol
            this.boxYres+=przesuniecie;
            if(newBoxYres<boxYres)
                this.boxYres=newBoxYres;
            duringBoxAnimation=true;
            repaint();
        }
        if(newPlayerXres==playerXres&&newPlayerYres==playerYres){
            this.playerX=newPlayerX;
            this.playerY=newPlayerY;

            duringAnimation=false;
        }
        if(boxX!=-1&&boxY!=-1&&newBoxX!=-1&&newBoxY!=-1){
            if(newBoxYres==boxYres&&newBoxXres==boxXres){
                boxX=-1;
                boxY=-1;
                newBoxX=-1;
                newBoxY=-1;
                duringBoxAnimation=false;
            }
        }


//        System.out.println("duringanimation "+duringAnimation);
    }

    /**
     * Informacja czy cel gry jest osiagniety (skrzynie sa na swoich miejscach)
     * @return true, jesli osiagnieto cel gry
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Informacja czy gracz otrzymuje bonus (podniosl gwiazdke, czyli wszedl na pole bonusowe)
     * @return true, jesli otrzymal bonus
     */
    public boolean otrzymalBonus() {
        return przyznacBonus;
    }
}
