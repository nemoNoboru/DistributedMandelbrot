import java.net.*;
import java.io.*;

public class MainClient {

	/* 1) Crear un socket conectandose al server
	 * aceptar un paquete task del socket
	 * calcularlo usando la clase Client
	 * devolver un paquete usando un socket.
	 */
	
	private static Task Process(Task t){
		P result = t.getInit().minus(t.getEnd());
		// work in progress
		double sizeX = t.getEndZ().re() - t.getInitZ().re();
		double sizeY = t.getEndZ().im() - t.getInitZ().im();
		double stepX = sizeX / result.x;
		double stepY = sizeY / result.y;
		
		for (int i = 0 ; i < result.x() ; i++ ){
			for (int j = 0 ; j < result.y() ; j++ ){
				double X = i * sizeX / result.x() + t.getInitZ().re();
				double Y = j * sizeY / result.y() + t.getInitZ().im();
				Complex C = new Complex(X,Y);
				Complex Z = new Complex(0,0);
				for (int iter = 0; iter < 299; iter++ ){
					Z = Z.times(Z).plus(C);
					if(2<Z.abs()){
						t.getMatrix()[i][j] = 0;
						break;
					} else {
						t.getMatrix()[i][j] = 1;
					}
				}
			}
		}
		return t;
	}
	
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",1337);
			OutputStream out = s.getOutputStream();
			ObjectOutputStream outObject = new ObjectOutputStream(out);
			InputStream in = s.getInputStream();
			ObjectInputStream inObject = new ObjectInputStream(in);
			
			Task t = (Task) inObject.readObject();
			t = Process(t); // get the value even if java passes the argument by copy
			outObject.writeObject(t);
			// close things
			outObject.close();
			out.close();
			inObject.close();
			in.close();
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}