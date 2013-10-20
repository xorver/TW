
public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		MyInteger integer = new MyInteger();
		integer.startIncrementationThreads(1500);
		System.out.println(integer.n);
	}
}
