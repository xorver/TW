package zad433;

public class Main {

    public static void main(String[] args){
        Monitor m = new Monitor();
        for (int i=0;i<Properties.P;i++)
            new Thread(new P(m)).start();
        for (int i=0;i<Properties.K;i++)
            new Thread(new K(m)).start();
    }
}
