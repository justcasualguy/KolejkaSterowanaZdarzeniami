import java.util.*;

public class LoggerZdarzen {
public  List<Zgloszenie> listaZgloszen ;
public  List<Zdarzenie> zakonczeniaObslugi;
public  List<Zdarzenie> wszystkieZdarzenia;
public  List<Zdarzenie> zdarzenia=  new LinkedList<>();
public  List<Zgloszenie> listaOdrzuconychZgloszen = new LinkedList<>();
public  List<StanKolejki> stanyKolejki = new LinkedList<>();
public  List<Zgloszenie> temp = new LinkedList<>();
public  List<Double> czasyObslugi = new LinkedList<>();


public  Queue<Zdarzenie> doObslugi;
public  int odrzuconeZgloszenia =0;
public   double  czas = 0;
public  int nrZdarzenia=0;






    public LoggerZdarzen() {
        listaZgloszen = new LinkedList<>();
        zakonczeniaObslugi = new LinkedList<>();
        wszystkieZdarzenia = new LinkedList<>();
        doObslugi = new PriorityQueue<>(new Comparator<Zdarzenie>() {
            @Override
            public int compare(Zdarzenie o1, Zdarzenie o2) {

                if(o1.czas>o2.czas)
                    return 1;
                if(o1.czas==o2.czas) {
                    if (o1.typ.equals("obsluga"))
                        return 1;
                    if(o2.typ.equals("obsluga"))
                        return -1;
                    return 0;
                }
                return -1;
            }
        });
    }
}
