import java.net.*;
import java.io.*;

public class MainClient {

	/* 1) Crear un socket conectandose al server
	 * aceptar un paquete task del socket
	 * calcularlo usando la clase Client
	 * devolver un paquete usando un socket.
	 */
	
	private static Task Process(Task t){
		P result = t.getSize();
		// work in progress
		double sizeX = t.getEndZ().re() - t.getInitZ().re();
		double sizeY = t.getEndZ().im() - t.getInitZ().im();
		double stepX = sizeX / result.x();
		double stepY = sizeY / result.y();
		System.out.println("stepX "+ stepX );
		
		for (int i = 0 ; i < t.getMatrix().length ; i++ ){
			for (int j = 0 ; j < t.getMatrix()[0].length ; j++ ){
				double X = t.getInitZ().re() + ((t.getInit().x()+i) * stepX);
				double Y = t.getInitZ().im() + ((t.getInit().y()+j) * stepY);
				System.out.println("calculando "+ X +" "+ Y);
				Complex C = new Complex(X,Y);
				Complex Z = new Complex(0,0);
				for (int iter = 0; iter < 2; iter++ ){
					Z = Z.times(Z).plus(C);
					if(2<Z.abs()){
						t.getMatrix()[i][j] = 1;
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
		//while(true){
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
				Thread.sleep(1000);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	//}

}