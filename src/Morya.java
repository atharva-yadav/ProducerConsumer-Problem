import java.util.LinkedList;

 class ProcucerConsumerApp {
	
	LinkedList<Integer> list = new LinkedList<>();
	int capacity = 4;
	int value = 0;
	
	public void produce() throws InterruptedException {
		
		while (true) {
			
			synchronized (this) {
				while (list.size() == capacity) {
					wait();
				}
				System.out.println("Produced - " + value);
				list.add(value++);
				System.out.println("Current list: "+list);
				
				//now after adding value, consumer can consume, so notify it
				notify();
				
				Thread.sleep(1000);
			}
		}
		
	}
	public void consume() throws InterruptedException {
		
		while (true) {
			
			synchronized (this) {
				while (list.size() == 0) {
					wait();
				}
				System.out.println("Consumed - " + list.removeFirst());
				
				//consumer consumed, hence producer can add new, so notify it
				notify();
				
				Thread.sleep(1000);
			}
		}
	}
}
public class Morya {

	public static void main(String[] args) {

//		System.out.println("GANPATI BAPPA MORYA");
		ProcucerConsumerApp obj = new ProcucerConsumerApp();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					obj.produce();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					obj.consume();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		t1.start();
		t2.start();
	}
}
