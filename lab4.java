import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {

	private int bufferSize;
	private List<Integer> free=new ArrayList<>();
	private List<Integer> full=new ArrayList<>();
	private Lock lock = new ReentrantLock();
	private Condition produceCond = lock.newCondition();
	
	
	public Monitor(int bufferSize){
		this.bufferSize=bufferSize;
		for(int i=0;i<bufferSize;i++)
			free.add(i);
	}
	
	public int[] produce(int n){
		int[] toReturn= new int[n];
		try {
			lock.lock();
			while(free.size()<n)
				produceCond.await();
			for(int i=0;i<n;i++)
				toReturn[i]=free.remove(free.size()-1);
		} catch (Exception e) {
			lock.unlock();
			e.printStackTrace();
		}
		return toReturn;
		
	}
	
	
	
}


public class Buffer {
	public final int SIZE=10;
	public int[] resources;
	
	public Buffer(){
		resources = new int[SIZE];
	}
}
