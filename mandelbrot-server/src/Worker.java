import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Worker implements Runnable {

	private volatile ConcurrentLinkedQueue<Task> taskList;
	private Socket socket;
	private volatile mandelbrotMatrix matrix;
	
	
	public Worker(Socket s, ConcurrentLinkedQueue<Task> t, mandelbrotMatrix m){
		this.taskList = t;
		this.socket = s;
		this.matrix = m;
	};
	
	public void run() {
		OutputStream out;
		ObjectOutputStream outObject;
		ObjectInputStream inObject;
		InputStream in;
		try {
			out = this.socket.getOutputStream();
			outObject = new ObjectOutputStream(out);
			in = this.socket.getInputStream();
			inObject= new ObjectInputStream(in);
			
			while(!taskList.isEmpty()){
					Task task = this.taskList.poll();
					outObject.writeObject(task);
					task = (Task) inObject.readObject(); //blocking
					
					//System.out.println("Coping chunk to main output");
					this.copyOnBig(task);
			}
			//close things
					outObject.close();
					out.close();
					inObject.close();
					in.close();
					this.socket.close();
		} catch(Exception e) {
			return;
		}
	}
	
	private void copyOnBig(Task task){
		matrix.setMatrix(task);
	}

}
