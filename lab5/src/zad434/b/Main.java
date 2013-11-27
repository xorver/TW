package zad434.b;


public class Main {

    public static void main(String[] args){
        Monitor monitor = new Monitor();
        for(int i=0;i< Properties.K;i++) {
            new Thread(new P(monitor)).start();
            new Thread(new K(monitor)).start();
        }
    }
}
