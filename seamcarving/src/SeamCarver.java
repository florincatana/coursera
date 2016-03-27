import edu.princeton.cs.algs4.Picture;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class SeamCarver {
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		throw new NotImplementedException();
	}
	
	// current picture
	public Picture picture() {
		throw new NotImplementedException();
	}
	
	// width of current picture
	public int width() {
		throw new NotImplementedException();
		
	}
	
	// height of current picture
	public int height() {
		throw new NotImplementedException();
	}
	
	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		throw new NotImplementedException();
		
	}
	
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		throw new NotImplementedException();
		
	}
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		throw new NotImplementedException();		
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		throw new NotImplementedException();
		
	}
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		throw new NotImplementedException();
	}
}
