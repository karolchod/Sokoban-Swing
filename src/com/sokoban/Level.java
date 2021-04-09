package com.sokoban;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Klasa przechowujaca informacje o poziomie, odczytane z pliku
 */
public class Level {

    private int[][] polaPlanszy;
    /**
     * konstruktor klasy Level
     */
    public Level() {
        //konstruktor
        //System.out.println("level");

    }

    /**
     *  Metoda uzywana podczas odczytu poziomu z pliku
     * @param resourcePath sciezka pliku
     * @return obiekt typu File
     */
    private static File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Metoda odczytujaca poziom z pliku resources/poziom""numer_poziomu".txt
     * @param numer_poziomu numer poziomu ktory ma zostac odczytany z pliku
     * @param online czy gra jest w trybie online
     */
    public void readLevel(int numer_poziomu, boolean online) {
        if(online){
            System.out.println("pobieranie level z serwera");
            //todo TUTAJ ZROBIC ODCZYT POZIOMU Z SERWERA
        }else{
            System.out.println("odczyt z pliku");

            //TUTAJ ZROBIC ODCZYT Z PLIKU (PRZENIESC Z DOLU)
        }

        //NA RAZIE NIE MA ODCZYTU Z SERWERA, WIEC ZAWSZE ODCZYTUJE Z PLIKU
//        File file = new File("resources/poziom"+Integer.toString(numer_poziomu)+".txt");

//        File file = new File(getClass().getClassLoader().getResourceAsStream("poziom"+Integer.toString(numer_poziomu)+".txt").getFile());
        File file = getResourceAsFile("poziom"+Integer.toString(numer_poziomu)+".txt");
        try{
            Scanner scanner = new Scanner(file);
            ArrayList<String> wiersze = new ArrayList<String>();

            int wiersz=0;
            while (scanner.hasNext()) {
                wiersze.add(scanner.nextLine());
//                System.out.println(wiersze.get(wiersz));
                wiersz++;
            }
//            System.out.println("wiersze: "+wiersze.size());
//            System.out.println("kolumny: "+ wiersze.get(0).length());
            this.polaPlanszy = new int[wiersze.size()][wiersze.get(0).length()];
            for(int j=0;j<wiersze.size();j++){
                for(int i=0;i<wiersze.get(0).length();i++){
                    char c = wiersze.get(j).charAt(i);
                    this.polaPlanszy[j][i]=Character.getNumericValue(c);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // KONIEC ODCZYTU Z PLIKU
    }

    /***
     * Wypisuje na ekran informacje o punkcie (x,y) na mapie
     * @param x wspolrzedna x planszy (numerowane od 0 w gore)
     * @param y wspolrzedna y planszy (numerowane od 0 w gore)
     */
    public void printInfo(int x, int y) {
        System.out.println(polaPlanszy[x][y]);
    }

    /***
     * Zwraca informacje o punkcie (x,y) na mapie
     * @param x wspolrzedna x planszy (numerowane od 0 w gore)
     * @param y wspolrzedna y planszy (numerowane od 0 w gore)
     * @return informacja o punkcie na mapie
     */
    public int getInfo(int x, int y) {
        return polaPlanszy[x][y];
    }

    /***
     *  Zwraca wysokosc mapy (ilosc wierszy)
     * @return wysokosc mapy (ilosc wierszy)
     */
    public int getRows(){
        return this.polaPlanszy.length;
    }

    /***
     * Zwraca szerokosc mapy (ilosc kolumn)
     * @return szerokosc mapy (ilosc kolumn)
     */
    public int getColumns(){
        return this.polaPlanszy[0].length;
    }
}
