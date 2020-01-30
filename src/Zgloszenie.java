import java.util.Iterator;
import java.util.List;

public class Zgloszenie {
    public double czasPrzybycia;
    public double czasObslugi;
    public boolean odrzucone;

    public Zgloszenie(double czasPrzybycia, double czasObslugi) {
        this.czasPrzybycia = czasPrzybycia;
        this.czasObslugi = czasObslugi;
        this.odrzucone=false;
    }


}
