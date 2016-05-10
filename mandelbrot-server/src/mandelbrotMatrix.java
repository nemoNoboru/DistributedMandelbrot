public class mandelbrotMatrix {
	private int matrix [][];
	
	public mandelbrotMatrix(int x, int y){
		matrix = new int[x][y];
	}
	
	public void set(int x, int y, int value){
		this.matrix[x][y] = value;  
	}
	
	public void setMatrix(int x, int y, int[][] m){
		System.out.println("added chunk to main");
		for(int i = x; i < m.length; i++ ){
			for (int j = y; j < m[0].length ; j++ ){
				this.matrix[i][j] = m[i][j];
			}
		}
	}
	
	public void printSTDOUT(){
		for(int i = 0; i < matrix.length; i++ ){
			for (int j = 0; j < matrix[0].length ; j++ ){
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public String write(){ //write to disk
		return "unimplemented";
	}
}
