package zad431;

public class Main {

    public static void main(String[] args){
        Monitor m = new Monitor();
        for(int i=0;i<5;i++){
            new Thread(new RunnableA(m)).start();
            new Thread(new RunnableB(m)).start();
            new Thread(new RunnableAB(m)).start();
        }

    }
}
