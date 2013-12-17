package activeobject;

import java.util.List;

public class Future {
    private List<Integer> result=null;
    private boolean done = false;

    public synchronized List<Integer> get() throws InterruptedException {
        while(!done)
            this.wait();
        return result;
    }

    public boolean isDone(){
        return done;
    }

    public synchronized void setResult(List<Integer> result){
        this.result=result;
        done=true;
        this.notifyAll();
    }
}
