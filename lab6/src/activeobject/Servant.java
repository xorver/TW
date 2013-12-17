package activeobject;

import properties.Properties;

import java.util.LinkedList;
import java.util.List;

public class Servant {
    private LinkedList<Integer> free=new LinkedList<>();
    private LinkedList<Integer> full=new LinkedList<>();

    public Servant() {
        for(int i=0;i<2* Properties.HALF_BUFFER_SIZE;i++)
            free.add(i);
    }

    public List<Integer> lockProduction(int n) {
        List<Integer> toReturn = new LinkedList<>(free.subList(0,n));
        free.subList(0,n).clear();
        return toReturn;
    }

    public void unlockProduction(List<Integer> indexes){
        full.addAll(indexes);
    }

    public List<Integer> lockConsumption(int n) {
        List<Integer> toReturn= new LinkedList<>(full.subList(0,n));
        full.subList(0,n).clear();
        return toReturn;
    }

    public void unlockConsumption(List<Integer> indexes){
        free.addAll(indexes);
    }

    public int getNumberOfFreeElements() {
        return free.size();
    }

    public int getNumberOfFullElements() {
        return full.size();
    }
}
