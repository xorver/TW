import java.util.ArrayList;
import java.util.List;


public class MyInteger {

	public int n=0;
	
	public synchronized void inc(){
		n++;
	}
	
	public void startIncrementationThreads(int n) throws InterruptedException{
		List<Thread> list = new ArrayList<>();
		for(int i=0;i<n;i++)
			list.add(new IncThread(this));
		for(Thread t: list)
			t.start();
		for(Thread t: list)
			t.join();
	}
	
	private class IncThread extends Thread{
		
		MyInteger integer;
		
		public IncThread(MyInteger integer){
			this.integer=integer;
		}


		public void run(){
			integer.inc();
		}
	}

}
