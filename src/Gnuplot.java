

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Gnuplot {

    public static void stanKolejkiDoPliku(String nazwaPliku,LoggerZdarzen loggerZdarzen) {
        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#stan kolejki \t czas");
//
            for (int i = 0; i < loggerZdarzen.zdarzenia.size(); i++) {
                Zdarzenie z = loggerZdarzen.zdarzenia.get(i);
                if (i == 0) {
                    file.println(String.format("%f\t%d", 0.0, 0));
                    file.println(String.format("%f\t%d", z.czas, 0));
                    file.println("########");
                }

                if (i + 1 < loggerZdarzen.zdarzenia.size()) {

                    file.println(String.format("%f\t%d", z.czas, z.stanKolejki.wKolejce));
                    file.println(String.format("%f\t%d", loggerZdarzen.zdarzenia.get(i + 1).czas, z.stanKolejki.wKolejce));

                }

                if (i == loggerZdarzen.zdarzenia.size() - 1)
                    file.println(String.format("%f\t%d", z.czas, z.stanKolejki.wKolejce));

                file.println("########");


            }

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void odrzuconeDoPliku(String nazwaPliku, LoggerZdarzen loggerZdarzen) {


        int odrzuconeZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:odrzuconeZgloszenia)
            i=0;

        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#odrzucone zgloszenia \t czas");

            for (int i = 0; i < loggerZdarzen.listaOdrzuconychZgloszen.size(); i++) {
                Zgloszenie z = loggerZdarzen.listaOdrzuconychZgloszen.get(i);

                z.czasPrzybycia= z.czasPrzybycia +1 -1;
                odrzuconeZgloszenia[(int)z.czasPrzybycia]++;


            }
            for(int i=0;i<odrzuconeZgloszenia.length;i++){
                file.println(String.format("%d\t%d", i, odrzuconeZgloszenia[i]));
                file.println(String.format("%d\t%d", i+1, odrzuconeZgloszenia[i]));
                file.println("########");
            }


            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void zgloszeniaDoPliku(String nazwaPliku,LoggerZdarzen loggerZdarzen) {



        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#czas przyjscia zgloszenia");

           for(Zgloszenie z: loggerZdarzen.listaZgloszen){
               file.println(String.format("%f\t%d",z.czasPrzybycia,0));
           }



            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void obsluzoneDoPliku(String nazwaPliku,LoggerZdarzen loggerZdarzen) {



        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#czas obslugi");

            for(double d: loggerZdarzen.czasyObslugi){
                file.println(String.format("%f\t%d",d,0));

            }

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static void obsluzoneWJednostceCzasueDoPliku(String nazwaPliku,LoggerZdarzen loggerZdarzen) {


        int obsluzoneZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:obsluzoneZgloszenia)
            i=0;
        for(Double d: loggerZdarzen.czasyObslugi)
            obsluzoneZgloszenia[(int) d.doubleValue()]++;

        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#czas obslugi");
            int k=0;
            for(int d: obsluzoneZgloszenia){
                file.println(String.format("%d\t%d",d,k));
                file.println(String.format("%d\t%d",d,++k));
                file.println("########");
            }



            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void zgloszeniaWJednostceCzasueDoPliku(String nazwaPliku,LoggerZdarzen loggerZdarzen) {


        int przybycieZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:przybycieZgloszenia)
            i=0;
        for(Zgloszenie z: loggerZdarzen.listaZgloszen) {
            z.czasPrzybycia= z.czasPrzybycia +1 -1;
            przybycieZgloszenia[(int) z.czasPrzybycia]++;
        }
        try {
            PrintWriter file = new PrintWriter(nazwaPliku);
            file.println("#zgloszenia w jednostce czasu");
            int k=0;
            for(int d: przybycieZgloszenia){
                file.println(String.format("%d\t%d",d,k));
                file.println(String.format("%d\t%d",d,++k));
                file.println("########");
            }



            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }



    public static void stworzFolder(String sciezka,String nazwa){
        File folder = new File(sciezka+"\\"+nazwa);
        folder.mkdir();
    }
    public static void stworzFolder(String sciezka){
        File folder = new File(sciezka);
        boolean b =folder.mkdir();
    ;
    }

    public static void komorkaDoGnuplota(Komorka komorka){

       stworzFolder(new File("").getAbsolutePath()+"\\Wyniki\\",String.format("klaster %d,%d,%d", komorka.parametry.iloscKanalow, komorka.parametry.iloscKanalow, komorka.parametry.iloscKanalow));
       String sciezka = new File("").getAbsolutePath()+"\\Wyniki\\"+String.format("klaster lambda=%d kanaly:%d,%d,%d",1/ komorka.parametry.czasZgloszenia, komorka.parametry.iloscKanalow, komorka.parametry.iloscKanalow, komorka.parametry.iloscKanalow);
      stworzFolder(sciezka, komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia);
       Gnuplot.odrzuconeDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\odrzucone.txt", komorka.loggerZdarzen);
       Gnuplot.stanKolejkiDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\stanKolejki.txt", komorka.loggerZdarzen);
       Gnuplot.zgloszeniaDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\zgloszenia.txt", komorka.loggerZdarzen);
     Gnuplot.obsluzoneDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\obsluzone.txt", komorka.loggerZdarzen);
        Gnuplot.obsluzoneWJednostceCzasueDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\obsluzoneWczasie.txt", komorka.loggerZdarzen);
        Gnuplot.zgloszeniaWJednostceCzasueDoPliku(sciezka+ komorka.nazwaKomorki +" lambda="+1/ komorka.parametry.czasZgloszenia+"\\zgloszeniaWczasie.txt", komorka.loggerZdarzen);

    }

    public static void komorkaDoGnuplota(Komorka komorka,String sciezka){



        stworzFolder(sciezka, komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+ ", kanaly="+komorka.kanaly.size());
        Gnuplot.odrzuconeDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\odrzucone.txt", komorka.loggerZdarzen);
        Gnuplot.stanKolejkiDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\stanKolejki.txt", komorka.loggerZdarzen);
        Gnuplot.zgloszeniaDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\zgloszenia.txt", komorka.loggerZdarzen);
        Gnuplot.obsluzoneDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\obsluzone.txt", komorka.loggerZdarzen);
        Gnuplot.obsluzoneWJednostceCzasueDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\obsluzoneWczasie.txt", komorka.loggerZdarzen);
        Gnuplot.zgloszeniaWJednostceCzasueDoPliku(sciezka+ "\\"+komorka.nazwaKomorki +", lambda="+1/ komorka.parametry.czasZgloszenia+", kanaly="+komorka.kanaly.size()+"\\zgloszeniaWczasie.txt", komorka.loggerZdarzen);

    }

    public static int[] zgłoszeniaKlastra(Klaster klaster){
        int przybycieZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:przybycieZgloszenia)
            i=0;

        for(Komorka k:klaster.komorki)
            for(Zgloszenie z: k.loggerZdarzen.listaZgloszen) {
                z.czasPrzybycia= z.czasPrzybycia +1 -1;
                przybycieZgloszenia[(int) z.czasPrzybycia]++;
            }

        return przybycieZgloszenia;


    }

    public static int[] odrzuceniaKlastra(Klaster klaster) {
        int odrzuconeZgloszenia[] = new int[(int) Parametry.czas + 1];

        for (int i : odrzuconeZgloszenia)
            i = 0;

        for (Komorka k : klaster.komorki)
            for (int i = 0; i < k.loggerZdarzen.listaOdrzuconychZgloszen.size(); i++) {
                Zgloszenie z = k.loggerZdarzen.listaOdrzuconychZgloszen.get(i);

                z.czasPrzybycia = z.czasPrzybycia + 1 - 1;
                odrzuconeZgloszenia[(int) z.czasPrzybycia]++;
            }
        return odrzuconeZgloszenia;
    }

    public static int[] odrzuceniaKlastraDlaKomorki(Klaster klaster) {
        int odrzuconeZgloszenia[] = new int[klaster.komorki.size()];

        for (int i : odrzuconeZgloszenia)
            i = 0;
        int i=0;
        for (Komorka k : klaster.komorki) {
            odrzuconeZgloszenia[i++]=k.loggerZdarzen.odrzuconeZgloszenia;

        }
        return odrzuconeZgloszenia;
    }


    public static void sumujDaneKlastra(Klaster klaster){

        int przybycieZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:przybycieZgloszenia)
            i=0;

        for(Komorka k:klaster.komorki)
            for(Zgloszenie z: k.loggerZdarzen.listaZgloszen) {
            z.czasPrzybycia= z.czasPrzybycia +1 -1;
            przybycieZgloszenia[(int) z.czasPrzybycia]++;
        }

    }

    public static void generujWynikiKlastra(Klaster klaster){
        int[] przybyciaZgłoszen = zgłoszeniaKlastra(klaster);
        int[] obsluzoneWczasie = obslugaWczasieKlastra(klaster);
        int[] odrzuceniaKomorki=odrzuceniaKlastraDlaKomorki(klaster);
        obslugaKlastra(klaster);

        try {
            { PrintWriter file = new PrintWriter(sciezkaKlastra(klaster)+"\\obsluzoneWczasie.txt");
            file.println("#czas obslugi");
            int k=0;
            for(int d: obsluzoneWczasie){
                file.println(String.format("%d\t%d",d,k));
                file.println(String.format("%d\t%d",d,++k));
                file.println("########");
            }

            file.close();
            }
            {
                PrintWriter file = new PrintWriter(sciezkaKlastra(klaster)+"\\zgloszeniaWczasie.txt");
                file.println("#zgloszenia w jednostce czasu");
                int k=0;
                for(int d: przybyciaZgłoszen){
                    file.println(String.format("%d\t%d",d,k));
                    file.println(String.format("%d\t%d",d,++k));
                    file.println("########");
                }

                file.close();
            }
            {
                PrintWriter file = new PrintWriter(sciezkaKlastra(klaster)+"\\odrzuceniaDlaKomorki.txt");
                file.println("#odrzucenia komorki nr komorki");
                int k=0;
                for(int d: odrzuceniaKomorki){
                    file.println(String.format("%d\t%d",d,++k));
                    file.println("########");
                }

                file.close();
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }





    public static void obslugaKlastra(Klaster klaster){
        try {
            PrintWriter file = new PrintWriter(sciezkaKlastra(klaster)+"\\obsluga.txt");
            file.println("#czas obslugi");

            for(Komorka k:klaster.komorki)
                for(double d: k.loggerZdarzen.czasyObslugi){
                    file.println(String.format("%f\t%d",d,0));

            }

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static int[] obslugaWczasieKlastra(Klaster klaster){

        int obsluzoneZgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:obsluzoneZgloszenia)
            i=0;
        for(Komorka komorka: klaster.komorki)
            for(Double d: komorka.loggerZdarzen.czasyObslugi)
                obsluzoneZgloszenia[(int) d.doubleValue()]++;

       return obsluzoneZgloszenia;

    }

    public static int[] zgloszeniaWczasieKlastra(Klaster klaster){

        int zgloszenia[] = new int[(int)Parametry.czas+1];
        for(int i:zgloszenia)
            i=0;
        for(Komorka komorka: klaster.komorki)
            for(Double d: komorka.loggerZdarzen.czasyObslugi)
                zgloszenia[(int) d.doubleValue()]++;

        return zgloszenia;

    }



    public static String sciezkaKlastra(Klaster klaster){
        StringBuilder sciezka = new StringBuilder();
        sciezka.append(new File("").getAbsolutePath() + "\\Wyniki\\"+ String.format("klaster_kanaly"));
        for(Komorka komorka:klaster.komorki)
            sciezka.append("_"+komorka.kanaly.size());
        return sciezka.toString();

    }


public static void klasterDoGnuplota(Klaster klaster){

        String sciezka = sciezkaKlastra(klaster);
        stworzFolder(sciezkaKlastra(klaster));
    for(Komorka komorka:klaster.komorki) {
        komorkaDoGnuplota(komorka, sciezka);

    }
    generujWynikiKlastra(klaster);


}




    }


