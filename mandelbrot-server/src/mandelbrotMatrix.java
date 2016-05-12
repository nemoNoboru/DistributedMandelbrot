import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class mandelbrotMatrix {
	private int matrix [][];
	
	public mandelbrotMatrix(int x, int y){
		matrix = new int[x][y];
	}
	
	public void set(int x, int y, int value){
		this.matrix[x][y] = value;  
	}
	
	public void setMatrix(Task t){
		//System.out.println("added chunk to main");
		for(int i = 0; i < t.getMatrix().length; i++ ){
			for (int j = 0; j < t.getMatrix()[0].length ; j++ ){
				this.matrix[i+t.getInit().x()][j+t.getInit().y()] = t.getMatrix()[i][j];
			}
		}
	}
	
	public void printSTDOUT(){
		for(int i = 0; i < matrix.length; i++ ){
			for (int j = 0; j < matrix[0].length ; j++ ){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printToFile(){
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("output.pgm"));
			System.setOut(out);
			System.out.println("P2");
			System.out.println(matrix.length+" "+matrix.length);
			System.out.println(300);
			this.printSTDOUT();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String write(){ //write to disk
		return "unimplemented";
	}
}
