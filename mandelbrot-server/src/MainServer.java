import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainServer {

	private static ExecutorService workerpool;
	private static LinkedList<Task> tasks;
	private static mandelbrotMatrix mandeloutput;
	
	public static void setupServer(){
		System.out.println("Erecting server...");
		try {
			ServerSocket serv = new ServerSocket(1337);
			while(!tasks.isEmpty()){
				workerpool.execute(new Worker(serv.accept(), tasks.pop(), mandeloutput ));
			}
			serv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void shutdownServer(){
		System.out.println("closing server...");
		workerpool.shutdown();
		try {
			if (!workerpool.awaitTermination(60, TimeUnit.SECONDS)) {
			       workerpool.shutdownNow(); // Cancel currently executing tasks
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Closed");
	}
	
	public static void main(String[] args) {
		int resX = 300;
		int resY = 300;
		tasks = new LinkedList<Task>();
		// work in progress
		P init = new P(0,0);
		P end = new P(resX,resY);
		Complex c = new Complex(-1,1);
		Complex cend = new Complex(0.4,0.4);
		
		tasks.push(new Task(init, end, c, cend));
		System.out.println("Building worker pool...");
		workerpool = Executors.newFixedThreadPool(8);

		
		mandeloutput = new mandelbrotMatrix(resX,resY);
		
		setupServer();
		shutdownServer();
		mandeloutput.printSTDOUT();

	}

}
