package runnables;

import buffers.Buffer;

public class S implements Runnable {
    private Buffer b1;
    private Buffer b2;

    public S(Buffer b1, Buffer b2) {
        this.b1 = b1;
        this.b2 = b2;
    }


    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while(true){
                //consume a
                Integer a = null;
                while (a==null)
                    a = b1.getValue();
                System.out.println(a + " consumed from b1");

                //consume b
                Integer b = null;
                while (b==null)
                    b = b1.getValue();
                System.out.println(b + " consumed from b1");

                //insert to b2
                boolean insertedSucessfully=false;
                while (!insertedSucessfully)
                    insertedSucessfully = b2.insertValue(a+b);
                System.out.println((a + b) + " inserted to b2");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
