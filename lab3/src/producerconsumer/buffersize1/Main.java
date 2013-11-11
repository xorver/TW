package producerconsumer.buffersize1;

public class Main {

    public static void main(String[] args) {
        Buffer b = new Buffer();
        for(int i=1;i<10;i++){
            new Thread(new P(b,i)).start();
            new Thread(new K(b)).start();
        }

    }

}
