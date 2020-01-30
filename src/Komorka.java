import java.util.*;

public class Komorka {
    String nazwaKomorki;
    List<Kanal> kanaly = new LinkedList<>();
    LinkedList<Zdarzenie> zdarzenia = new LinkedList<>();
    LinkedList<Zdarzenie> przyjsciaZgloszen  = new LinkedList<>();
    LinkedList<Zdarzenie> obslugaZgloszen = new LinkedList<>();
    LoggerZdarzen loggerZdarzen = new LoggerZdarzen();
    Parametry parametry;
    int rozmiarKolejki=4;
    

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

    public Komorka(){}
    public Komorka(int iloscKanalow){
        for(int i=0;i<iloscKanalow;i++)
            kanaly.add(new Kanal());
    }

    public Komorka(int iloscKanalow, String nazwaKomorki){
        for(int i=0;i<iloscKanalow;i++)
            kanaly.add(new Kanal());
    }

    public Komorka(String nazwaKomorki, Parametry parametry){
        this.nazwaKomorki = nazwaKomorki;
        for(int i=0;i<parametry.iloscKanalow;i++)
            kanaly.add(new Kanal());
        this.parametry=parametry;
    }

    public void obsluzZgloszenie(Zgloszenie zgloszenie){
        loggerZdarzen.czas=zgloszenie.czasPrzybycia;
        if(kolejka.size()<parametry.rozmiarKolejki) {
            kolejka.add(zgloszenie);
            System.out.println("Przyjeto zgloszenie do kolejki. Czas: "+loggerZdarzen.czas+" Zgloszenia w kolejce: "+kolejka.size());

        }
        else {
            System.out.println("!!!Odrzucono zgloszenie. Czas: "+zgloszenie.czasPrzybycia+" Zgloszenia w kolejce: "+ kolejka.size());
            zgloszenie.odrzucone=true;
            loggerZdarzen.odrzuconeZgloszenia++;
            loggerZdarzen.listaOdrzuconychZgloszen.add(zgloszenie);
        }
        loggerZdarzen.zdarzenia.add(new Zdarzenie(loggerZdarzen.czas,"zgloszenie",false,new StanKolejki(kolejka.size(),loggerZdarzen.czas)));
        loggerZdarzen.stanyKolejki.add(new StanKolejki(kolejka.size(),loggerZdarzen.czas));
        przyjsciaZgloszen.add(new Zdarzenie(zgloszenie));

    }

    public void zakonczenieObslugi(){
        Zdarzenie zdarzenie;

        zdarzenie = obslugaZgloszen.poll();
        loggerZdarzen.czas = zdarzenie.czas;
        zwolnijZajetyKanal();
        System.out.println(String.format("Obsłużono zgloszenie. Aktualny czas: %f. Zgloszenia w kolejce: %d",loggerZdarzen.czas,kolejka.size()));
        if(kolejka.size()>0)
        rozpocznijObsluge(wolnyKanal());
        //LoggerZdarzen.zdarzenia.add(new Zdarzenie(zdarzenie.czas,"zakonczenie",false,new StanKolejki(kolejka.size(),LoggerZdarzen.czas)));
        loggerZdarzen.stanyKolejki.add(new StanKolejki(kolejka.size(),zdarzenie.czas));
        loggerZdarzen.czasyObslugi.add(zdarzenie.czas);
    }

    public void rozpoczecieObslugi(){
        Zdarzenie zdarzenie;
        zdarzenie = przyjsciaZgloszen.peek();

        if (!zdarzenie.zgloszenie.odrzucone)

            if (czyJestWolnyKanal()) {
                rozpocznijObsluge(wolnyKanal());

            }
        przyjsciaZgloszen.poll();

    }



    public boolean rozpocznijObsluge(Kanal kanal){
        kanal.wolny=false;
        Zdarzenie zdarzenie = new Zdarzenie(loggerZdarzen.czas + kolejka.peek().czasObslugi,"obsluga");
        System.out.print(String.format("Rozpoczeto obsluge zdarzenia. Czas rozpoczecia: %f Czas zakonczenia obslugi: %f",loggerZdarzen.czas,loggerZdarzen.czas+kolejka.peek().czasObslugi));
        przyjsciaZgloszen.poll();
        obslugaZgloszen.add(zdarzenie);
        kolejka.poll();
        loggerZdarzen.stanyKolejki.add(new StanKolejki(kolejka.size(),loggerZdarzen.czas));
        loggerZdarzen.zdarzenia.add(new Zdarzenie(loggerZdarzen.czas+parametry.deltaT,"rozpoczecie",false,new StanKolejki(kolejka.size(),loggerZdarzen.czas)));
        System.out.println(" Zgloszenia w kolejce: "+ kolejka.size());

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

    public void zwolnijZajetyKanal(){
        for(Kanal kanal : kanaly)
            if(!kanal.wolny) {
                kanal.wolny = true;
                return;
            }
    }


}
