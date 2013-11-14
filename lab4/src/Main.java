public class Main {

    public static void main(String[] args){
        Buffer b = new Buffer();
        Monitor m = new Monitor(b.SIZE*2);

        for(int i=0;i<10;i++){
            new Thread(new P(b,m)).start();
            new Thread(new K(b,m)).start();
        }
    }
}
