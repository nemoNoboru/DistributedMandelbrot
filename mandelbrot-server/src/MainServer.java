import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainServer {

	private static ExecutorService workerpool;
	private volatile static LinkedList<Task> tasks;
	private volatile static mandelbrotMatrix mandeloutput;
	
	public static void setupServer(){
		System.out.println("Erecting server...");
		try {
			ServerSocket serv = new ServerSocket(1337);
			while(!tasks.isEmpty()){
				workerpool.execute(new Worker(serv.accept(), tasks.pop(), mandeloutput ));
				System.out.println("Accepted socket");
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
			if (!workerpool.awaitTermination(600, TimeUnit.SECONDS)) {
			       workerpool.shutdownNow(); // Cancel currently executing tasks
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Closed");
	}
	
	public static void main(String[] args) {
		int sizeX = 200;
		int sizeY = 200;
		
		if (args.length > 1){
			sizeX = Integer.parseInt(args[0]);
			sizeY = Integer.parseInt(args[1]);
		}
		
		P size = new P(sizeX,sizeY); // size of the image to generate
		tasks = new LinkedList<Task>();
		
		// divide the tasks
		Complex initz = new Complex(-2,1);
		Complex endz = new Complex(1,-1);
		
		for(int i = 0 ; i  < size.x(); i += 10){
			for(int j = 0; j  < size.y(); j += 10){
				P init = new P(i,j);
				P end = new P(i+10,j+10);
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
