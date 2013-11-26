package zad432;

public class Main {


    public static void main(String[] args){
        Monitor monitor = new Monitor();
        for(int i=0;i<Properties.N;i++)
            new Thread(new P(i, monitor)).start();
    }
}
