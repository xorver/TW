package activeobject;

import properties.Properties;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Servant {
    private Queue<Integer> free=new LinkedList<>();
    private Queue<Integer> full=new LinkedList<>();
    private int[] buffer = new int[Properties.HALF_BUFFER_SIZE*2];

    public Servant() {
        for(int i=0;i<2* Properties.HALF_BUFFER_SIZE;i++)
            free.add(i);
    }

    public void produce(List<Integer> argument) {
        for(Integer arg : argument ) {
            int index=free.poll();
            buffer[index]=arg;
            full.add(index);
        }
    }

    public List<Integer> consume(int n) {
        List<Integer> toReturn= new ArrayList<>();
        for(int i=0;i<n;i++) {
            int index =full.poll();
            toReturn.add(buffer[index]);
            free.add(index);
        }
        return toReturn;
    }

    public int getNumberOfFreeElements() {
        return free.size();
    }

    public int getNumberOfFullElements() {
        return full.size();
    }
}
