import java.util.*;

public class Klaster {

    List<Kanal> kanaly = new LinkedList<>();
    List<Zdarzenie> zdarzenia = new LinkedList<>();
    int rozmiarKolejki=4;

   Queue<Zdarzenie> doObslugi = new PriorityQueue<>(new Comparator<Zdarzenie>() {
        @Override
        public int compare(Zdarzenie o1, Zdarzenie o2) {
            if(o1.czas>o2.czas)
                return 1;
            if(o1.czas==o2.czas)
                return 0;
            return -1;
        }
    });

    PriorityQueue<Zgloszenie> kolejka = new PriorityQueue<>(new Comparator<Zgloszenie>() {
        @Override
        public int compare(Zgloszenie o1, Zgloszenie o2) {
            if(o1.czasPrzybycia>o2.czasPrzybycia)
                return 1;
            if(o1.czasPrzybycia==o2.czasPrzybycia)
                return 0;
            return -1;
        }
    });

    public  Klaster(){}
    public  Klaster(int iloscKanalow){
        for(int i=0;i<iloscKanalow;i++)
            kanaly.add(new Kanal());
    }
    public boolean przyjmijZgłoszenie(Zgloszenie zgloszenie){
        if(kolejka.size()<=rozmiarKolejki) {
            kolejka.add(zgloszenie);
            doObslugi.add(new Zdarzenie(zgloszenie,false));
        }
        else {
            zgloszenie.odrzucone=true;
            LoggerZdarzen.odrzuconeZdarzenia++;
        }
        zdarzenia.add(new Zdarzenie(zgloszenie));
        obsluzZdarzenie(zgloszenie.czasPrzybycia);
        return !zgloszenie.odrzucone;
    };

    public void obsluzZdarzenie(double czas){

        Zdarzenie zdarzenie = (Zdarzenie)doObslugi.peek();
        LoggerZdarzen.czas = czas;
        if(zdarzenie.typ.equals("zgloszenie")) {
            if(czyJestWolnyKanal()) {
                rozpocznijObsluge(new Zgloszenie(LoggerZdarzen.czas, Parametry.czasObslugi), wolnyKanal());
                doObslugi.poll();

            }
        }
        if(zdarzenie.typ.equals("obsluga")){
            kanaly.get(0).wolny=true;
            doObslugi.poll();
            obsluzZdarzenie(czas);
        }
        System.out.println(String.format("Obsłużono zdarzenie. Typ: %s  Czas: %f",zdarzenie.typ,czas));

    }


    public boolean rozpocznijObsluge(Zgloszenie zgloszenie,Kanal kanal){


        kanal.wolny=false;
        Zdarzenie zdarzenie = new Zdarzenie(LoggerZdarzen.czas + kolejka.peek().czasObslugi,"obsluga");
        zdarzenia.add(zdarzenie);
        doObslugi.add(zdarzenie);
        kolejka.poll();
        return true;
        }

    public boolean czyJestWolnyKanal(){
        for(Kanal kanal : kanaly)
            if(kanal.wolny)
                return true;
            return false;
    }

    public Kanal wolnyKanal(){
        for(Kanal kanal : kanaly)
            if(kanal.wolny)
                return kanal;

            return null;
    }



}
