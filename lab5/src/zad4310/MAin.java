package zad4310;

public class Main {

    public static void main(String[] args){
        Monitor monitor = new Monitor();
        for(int i=0;i<Properties.A;i++)
            new Thread(new A(monitor)).start();
        for(int i=0;i<Properties.AB;i++)
            new Thread(new AB(monitor)).start();
        for(int i=0;i<Properties.AB_NOWAIT;i++)
            new Thread(new ABNowait(monitor)).start();
    }
}
