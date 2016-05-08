import java.io.Serializable;

// represents a point in a 2d world
public class P implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	int x,y;
	
	public P(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int x(){
		return this.x;
	}
	
	public int y(){
		return this.y;
	}
	
	public P minus(P point){
		return new P(Math.abs(this.x - point.x),Math.abs(this.x - point.x));
	}
	
}
