public class Zdarzenie {
    double czas;
    String typ;



    public Zdarzenie(double czas, String typ) {
        this.czas = czas;
        this.typ = typ;
        LoggerZdarzen.dodajDoLoggera(this);
    }

    public Zdarzenie(Zgloszenie zgloszenie){
        this.czas = zgloszenie.czasPrzybycia;
        typ = "zgloszenie";
        LoggerZdarzen.dodajDoLoggera(this);

    }

    public Zdarzenie(Zgloszenie zgloszenie,boolean czyDodacDoLoggera){
        this.czas = zgloszenie.czasPrzybycia;
        typ = "zgloszenie";
       if(czyDodacDoLoggera)
        LoggerZdarzen.dodajDoLoggera(this);

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
}
