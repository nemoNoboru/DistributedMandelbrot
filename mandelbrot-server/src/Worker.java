import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Worker implements Runnable {

	private Task task;
	private Socket socket;
	private mandelbrotMatrix matrix;
	
	
	public Worker(Socket s, Task t, mandelbrotMatrix m){
		this.task = t;
		this.socket = s;
		this.matrix = m;
	};
	
	public void run() {
		OutputStream out;
		try {
			out = this.socket.getOutputStream();
		
		ObjectOutputStream outObject = new ObjectOutputStream(out);
		InputStream in = this.socket.getInputStream();
		ObjectInputStream inObject = new ObjectInputStream(in);
		
		outObject.writeObject(this.task);
		this.task = (Task) inObject.readObject(); //blocking
		
		this.copyOnBig();
		
		//close things
		outObject.close();
		out.close();
		inObject.close();
		in.close();
		this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void copyOnBig(){
		matrix.setMatrix(task.getInit().x(), task.getInit().y(), task.getMatrix());
	}

}
