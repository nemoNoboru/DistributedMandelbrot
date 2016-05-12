import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainServer {

	private static ExecutorService workerpool;
	private static volatile ConcurrentLinkedQueue<Task> tasks;
	private volatile static mandelbrotMatrix mandeloutput;
	
	public static void setupServer(){
		System.out.println("Erecting server...");
		try {
			ServerSocket serv = new ServerSocket(1338);
			serv.setSoTimeout(6000);
			System.out.println("Erected server");
			while(!tasks.isEmpty()){ // is the job done?
				try {
					workerpool.execute(new Worker(serv.accept(), tasks, mandeloutput ));
					System.out.println("Accepted socket");
				} catch (SocketTimeoutException e) {
					// if timeout, check if the job already has been done and exit.
					continue;
				}
			}
			System.out.println("Job done");
			serv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutdownServer(){
		System.out.println("closing server...");
		workerpool.shutdown();
		try {
			if (!workerpool.awaitTermination(6, TimeUnit.SECONDS)) {
			       workerpool.shutdownNow(); // Cancel currently executing tasks
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Closed");
	}
	
	public static void main(String[] args) {
		int sizeX = 200;
		int sizeY = 200;
		double initZX = -2;
		double initZY = 1;
		double endZX = 1;
		double endZY = -1;
		int chunkSize = 100;
		
		if (args.length > 1){
			sizeX = Integer.parseInt(args[0]);
			sizeY = Integer.parseInt(args[1]);
		}
		if (args.length > 5){
			initZX = Double.parseDouble(args[2]);
			initZY = Double.parseDouble(args[3]);
			endZX = Double.parseDouble(args[4]);
			endZY = Double.parseDouble(args[5]);
		}
		
		P size = new P(sizeX,sizeY); // size of the image to generate
		tasks = new ConcurrentLinkedQueue<Task>();
		
		// divide the tasks
		Complex initz = new Complex(initZX,initZY);
		Complex endz = new Complex( endZX,endZY);
		
		for(int i = 0 ; i  < size.x(); i += chunkSize){
			for(int j = 0; j  < size.y(); j += chunkSize){
				P init = new P(i,j);
				P end = new P(i+chunkSize,j+chunkSize);
				tasks.add(new Task(init,end,initz,endz,size));
				//System.out.println("created " + init.x() + " " + init.y() + " TO " + end.x + " " + end.y);
			}
		}
		System.out.println("created "+ tasks.size() +" tasks");
		//end
		
		System.out.println("Building worker pool...");
		workerpool = Executors.newCachedThreadPool();

		
		mandeloutput = new mandelbrotMatrix(size.x(),size.y());
		
		setupServer();
		shutdownServer();
		mandeloutput.printToFile();
		

	}

}
