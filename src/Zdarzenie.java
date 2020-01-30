public class Zdarzenie implements Comparable<Zdarzenie> {
    double czas;
    String typ;
    Zgloszenie zgloszenie=null;
    StanKolejki stanKolejki;

    public Zdarzenie(double czas, String typ) {
        this.czas = czas;
        this.typ = typ;
      //  LoggerZdarzen.dodajDoLoggera(this);
    }
    public Zdarzenie(double czas, String typ,boolean czyDodacDoLoggera) {
        this.czas = czas;
        this.typ = typ;
        //if(czyDodacDoLoggera)
       // LoggerZdarzen.dodajDoLoggera(this);
    }
    public Zdarzenie(double czas, String typ,boolean czyDodacDoLoggera,StanKolejki stanKolejki) {
        this.czas = czas;
        this.typ = typ;
        //if(czyDodacDoLoggera)
          //  LoggerZdarzen.dodajDoLoggera(this);
        this.stanKolejki=stanKolejki;
    }
    public Zdarzenie(Zgloszenie zgloszenie){
        this.czas = zgloszenie.czasPrzybycia;
        typ = "zgloszenie";
        this.zgloszenie=zgloszenie;
       // LoggerZdarzen.dodajDoLoggera(this);

    }

    public Zdarzenie(Zgloszenie zgloszenie,boolean czyDodacDoLoggera){
        this.czas = zgloszenie.czasPrzybycia;
        typ = "zgloszenie";
       //if(czyDodacDoLoggera)
       // LoggerZdarzen.dodajDoLoggera(this);

    }

    public double getCzas() {
        return czas;
    }

    public void setCzas(double czas) {
        this.czas = czas;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Override
    public int compareTo(Zdarzenie z) {
        if(this.czas>z.czas)
            return 1;
        if(this.czas==z.czas) {

                if (z.typ.equals("obsluga"))
                    return 1;
                if(z.typ.equals("zgloszenie"))
                    return -1;
                return 0;
            }
            return -1;


    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Zdarzenie))
            return false;
        Zdarzenie z = (Zdarzenie)obj;

        return z.czas==this.czas&&this.typ.equals(z.typ);
    }

    @Override
    public String toString() {
        return String.format("Typ:%s , czas: %f",typ,czas);
    }
}
