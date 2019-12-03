import java.util.*;

public class LoggerZdarzen {
public static List<Zdarzenie> zgloszenia ;
public static List<Zdarzenie> zakonczeniaObslugi;
public static List<Zdarzenie> wszystkieZdarzenia;
public static Queue<Zdarzenie> doObslugi;
public static int odrzuconeZdarzenia=0;
public static  double  czas = 0;
public static int nrZdarzenia=0;

    public static void dodajDoLoggera(Zdarzenie zdarzenie){
        if(zdarzenie.typ.equals("zgloszenie"))
            LoggerZdarzen.zgloszenia.add(zdarzenie);
        else
            LoggerZdarzen.zakonczeniaObslugi.add(zdarzenie);

        LoggerZdarzen.wszystkieZdarzenia.add(zdarzenie);
        LoggerZdarzen.doObslugi.add(zdarzenie);
//        wszystkieZdarzenia.sort(new Comparator<Zdarzenie>() {
//            @Override
//            public int compare(Zdarzenie z1, Zdarzenie z2) {
//                if(z1.czas>z2.czas)
//                    return 1;
//                if(z1.czas == z2.czas)
//                    return 0;
//                return -1;
//            }
//
//        });
    }




    public LoggerZdarzen() {
        zgloszenia = new LinkedList<>();
        zakonczeniaObslugi = new LinkedList<>();
        wszystkieZdarzenia = new LinkedList<>();
        doObslugi = new PriorityQueue<>(new Comparator<Zdarzenie>() {
            @Override
            public int compare(Zdarzenie o1, Zdarzenie o2) {
                if(o1.czas>o2.czas)
                    return 1;
                if(o1.czas==o2.czas)
                    return 0;
                return -1;
            }
        });
    }
}
