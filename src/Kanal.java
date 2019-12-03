public class Kanal {
    public boolean wolny;

    Kanal(){wolny = true;}

    public void zajmij(){
        wolny = false;
    }
    public void zwolnij(){
        wolny = true;
    }
}
