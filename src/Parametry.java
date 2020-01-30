

public class Parametry {
    public  double czasObslugi=0.33;
    public  double czasZgloszenia=0.2;
    public  int iloscKanalow = 1;
    public  int rozmiarKolejki=4;
    public  double deltaT=0.00;
public static double czas=5;

    public Parametry(double czasObslugi, double czasZgloszenia, int iloscKanalow, int rozmiarKolejki) {
        this.czasObslugi = czasObslugi;
        this.czasZgloszenia = czasZgloszenia;
        this.iloscKanalow = iloscKanalow;
        this.rozmiarKolejki = rozmiarKolejki;
    }
}
