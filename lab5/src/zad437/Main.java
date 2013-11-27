package zad437;


public class Main {

    public static void main(String[] args){
        Monitor monitor = new Monitor();
        for(int i=0;i< Properties.P;i++) {
            new Thread(new P(monitor)).start();
        }
    }
}
