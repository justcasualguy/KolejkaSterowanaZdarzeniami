

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void wyslijZgloszenie(double czas, Komorka komorka) {
        komorka.obsluzZgloszenie(new Zgloszenie(czas, komorka.parametry.czasObslugi));
        komorka.loggerZdarzen.listaZgloszen.add(new Zgloszenie(czas, komorka.parametry.czasObslugi));
        komorka.loggerZdarzen.temp.add(new Zgloszenie(czas, komorka.parametry.czasObslugi));

    }


    public static void symulujKomorke(Komorka komorka){
        double czas = 0;
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println(String.format("Symulacja klastra : czas zgloszenia %f ilosc kanalow %d", komorka.parametry.czasZgloszenia, komorka.parametry.iloscKanalow));
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        while (komorka.loggerZdarzen.czas < Parametry.czas) {
            if (komorka.obslugaZgloszen.isEmpty()) {
                wyslijZgloszenie(czas += komorka.parametry.czasZgloszenia, komorka);
                komorka.rozpoczecieObslugi();
            } else {

                if (komorka.obslugaZgloszen.peek().czas <= czas + komorka.parametry.czasZgloszenia) {
                    komorka.zakonczenieObslugi();
                    wyslijZgloszenie(czas += komorka.parametry.czasZgloszenia, komorka);
                    if (komorka.kolejka.size() > 0)
                        komorka.rozpoczecieObslugi();

                } else {
                    wyslijZgloszenie(czas += komorka.parametry.czasZgloszenia, komorka);
                    komorka.rozpoczecieObslugi();
                }
            }

        }

        System.out.println("======================================================");

        System.out.println("Odrzucone zgloszenia: " + komorka.loggerZdarzen.odrzuconeZgloszenia);
        for (Zgloszenie z : komorka.loggerZdarzen.listaOdrzuconychZgloszen)
            System.out.println(String.format("Czas przybycia zgłoszenia: %f ", z.czasPrzybycia));

        System.out.println("======================================================");
        for (StanKolejki s : komorka.loggerZdarzen.stanyKolejki)
            System.out.println(String.format("Stan kolejki w %f : %d", s.czas, s.wKolejce));



        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println(String.format("Zakończnie symulacji  klastra : czas zgloszenia %f ilosc kanalow %d", komorka.parametry.czasZgloszenia, komorka.parametry.iloscKanalow));
        System.out.println("***************************************************");
        System.out.println("***************************************************");

    }




    public static void main(String[] args) {
        int sum=0;
        ArrayList<Komorka> komorki = new ArrayList<>();
        Klaster klaster = new Klaster(komorki);
        ArrayList<Integer[]> kombinacje = GFG.findCombinationsWithLength(9,3);
        int[] zestawKanalow = new int[3];
        int sumaOdrzuconychMin = 999999999;
//        komorki.add(new Klaster("klaster 1",new Parametry(1,0.1,3,4)));
//        komorki.add(new Klaster("klaster 2",new Parametry(1,0.2,3,4)));
//        komorki.add(new Klaster("klaster 3",new Parametry(1,0.25,3,4)));
        for(Integer[] i: kombinacje){

            sum=0;
            int k=0;
            komorki.removeAll(komorki);
            komorki.add(new Komorka("komorka 1",new Parametry(1,1,i[0],4)));
            komorki.add(new Komorka("komorka 2",new Parametry(1,0.2,i[1],4)));
            komorki.add(new Komorka("komorka 3",new Parametry(1,0.3,i[2],4)));



            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(String.format("Symulacja zestawu klastrów, ilosci kanałów: %d,%d,%d",komorki.get(0).parametry.iloscKanalow,komorki.get(1).parametry.iloscKanalow,komorki.get(2).parametry.iloscKanalow));
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            for(Komorka komorka :komorki) {

                symulujKomorke(komorka);
                sum += komorka.loggerZdarzen.odrzuconeZgloszenia;
            }

            if(sum<sumaOdrzuconychMin){
                sumaOdrzuconychMin=sum;
                zestawKanalow[0]=komorki.get(0).parametry.iloscKanalow;
                zestawKanalow[1]=komorki.get(1).parametry.iloscKanalow;
                zestawKanalow[2]=komorki.get(2).parametry.iloscKanalow;

            }
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(String.format("suma odrzuconych: %d zestaw kanalow: %d,%d,%d",sum,komorki.get(0).parametry.iloscKanalow,komorki.get(1).parametry.iloscKanalow,komorki.get(2).parametry.iloscKanalow));
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

            System.out.println("======================================================");
            System.out.println("======================================================");
            System.out.println(String.format("Zakończenie symulacji zestawu klastrów, ilosci kanałów: %d,%d,%d",komorki.get(0).parametry.iloscKanalow,komorki.get(1).parametry.iloscKanalow,komorki.get(2).parametry.iloscKanalow));
            System.out.println("======================================================");
            System.out.println("======================================================");
            Gnuplot.klasterDoGnuplota(klaster);

        }



    System.out.println(String.format("Min odrzuconych: %d zestaw kanalow: %d,%d,%d",sumaOdrzuconychMin,zestawKanalow[0],zestawKanalow[1],zestawKanalow[2]));
    }



}
