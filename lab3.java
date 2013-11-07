
public class Buffer {
	final int N=1;
	int[] buffer;
	public Buffer() {
		buffer = new int[N];
	}
	
	public synchronized void produce(int val) throws InterruptedException{
		while(buffer[0]!=0)
			this.wait();
		buffer[0]=val;
		this.notifyAll();
		System.out.println(Thread.currentThread().getName() +" Produced "+val);
	}
	
	public synchronized int consume() throws InterruptedException{
		while(buffer[0]==0)
			this.wait();
		int toReturn = buffer[0];
		buffer[0]=0;
		this.notifyAll();
		System.out.println(Thread.currentThread().getName() +" Consumed: "+toReturn);
		return toReturn;
	}
}


public class K implements Runnable {
	
	private Buffer b;
	private int id;
	
	public K(Buffer b,int id) {
		this.b = b;
		this.id=id;
	}

	@Override
	public void run() {
		try {
			while (true){
				b.consume();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}


public class P implements Runnable {
	
	private Buffer b;
	private int id;
	
	public P(Buffer b,int id) {
		this.b = b;
		this.id = id;
	}

	@Override
	public void run() {
		try {
			while (true){
				b.produce(id);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}


public class Main {

	public static void main(String[] args) {
		Buffer b = new Buffer();
		for(int i=1;i<10;i++){
			new Thread(new P(b,i)).start();
			new Thread(new K(b,i)).start();
		}

	}

}

