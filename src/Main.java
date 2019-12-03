public class Main {


    public static void main(String[] args) {

        double czas = 0;
        LoggerZdarzen logger = new LoggerZdarzen();

        Klaster klaster = new Klaster(2);

        while(LoggerZdarzen.czas<5)
        klaster.przyjmijZgłoszenie(new Zgloszenie(LoggerZdarzen.czas+Parametry.czasZgloszenia,Parametry.czasObslugi));
//        klaster.przyjmijZgłoszenie(new Zgloszenie(0.4,1));
//        klaster.przyjmijZgłoszenie(new Zgloszenie(0.6,1));
//        klaster.przyjmijZgłoszenie(new Zgloszenie(0.8,1));
int k;
    }


}
