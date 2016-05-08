
import java.io.Serializable;


public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public P getInit() {
		return init;
	}

	public void setInit(P init) {
		this.init = init;
	}

	public P getEnd() {
		return end;
	}

	public void setEnd(P end) {
		this.end = end;
	}

	public Complex getInitZ() {
		return initZ;
	}

	public void setInitZ(Complex initZ) {
		this.initZ = initZ;
	}

	public Complex getEndZ() {
		return endZ;
	}

	public void setEndZ(Complex endZ) {
		this.endZ = endZ;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	private P init, end;
	private Complex initZ, endZ;
	private int[][] matrix;
	
	public Task( P init, P end, Complex initZ, Complex endZ ){
		P result = end.minus(init);
		matrix = new int[result.x][result.y];
		this.init = init;
		this.end = end;
		this.initZ = initZ;
		this.endZ = endZ;
	}
}
