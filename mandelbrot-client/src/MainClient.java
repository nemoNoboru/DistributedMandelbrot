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

		double sizeX = t.getEndZ().re() - t.getInitZ().re();
		double sizeY = t.getEndZ().im() - t.getInitZ().im();
		double stepX = sizeX / result.x();
		double stepY = sizeY / result.y();
		System.out.println("Processing task");
		
		for (int i = 0 ; i < t.getMatrix().length ; i++ ){
			for (int j = 0 ; j < t.getMatrix()[0].length ; j++ ){
				double X = t.getInitZ().re() + ((t.getInit().x()+i) * stepX);
				double Y = t.getInitZ().im() + ((t.getInit().y()+j) * stepY);
				//System.out.println("calculando "+ X +" "+ Y);
				Complex C = new Complex(X,Y);
				Complex Z = new Complex(0,0);
				for (int iter = 0; iter < 300; iter++ ){
					Z = Z.times(Z).plus(C);
					if(2<Z.abs()){
						t.getMatrix()[i][j] = iter;
						break;
					} else {
						t.getMatrix()[i][j] = iter;
					}
				}
			}
		}
		return t;
	}
	
	
	public static void main(String[] args) {
		String address = "localhost";
		if(args.length > 0){
			address = args[0];
		}
		Socket s = null;
		OutputStream out = null;
		ObjectOutputStream outObject = null ;
		InputStream in = null;
		ObjectInputStream inObject = null;
		
		try {
			s = new Socket(address,1338);
			out = s.getOutputStream();
			outObject = new ObjectOutputStream(out);
			in = s.getInputStream();
			inObject = new ObjectInputStream(in);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		while(true){
			Task t;
			try {
				t = (Task) inObject.readObject();
				Process(t);
				outObject.writeObject(t);
			} catch (ClassNotFoundException | IOException e) {
				try {
					in.close();
					out.close();
					inObject.close();
					outObject.close();
					s.close();
					System.out.println("Seems done, check the server");
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}

	}

}