import java.awt.Color;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.princeton.cs.algs4.Accumulator;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;


public class SeamCarver {
	private static final List<Integer> DIRECTION = Arrays.asList(-1, 0, 1);
	private static final double DEFAULT_ENERGY = 1000;
	private Picture picture;
	
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new NullPointerException("picture");
		this.picture = new Picture(picture);
	}
	
	// current picture
	public Picture picture() {
		return new Picture(this.picture);
	}
	
	// width of current picture
	public int width() {
		return picture.width();
	}
	
	// height of current picture
	public int height() {
		return picture.height();
	}
	
	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1)
			throw new IndexOutOfBoundsException("x");
		if (y < 0 || y > height() - 1)
			throw new IndexOutOfBoundsException("y");
		
		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
			return DEFAULT_ENERGY;
		}
		
		return Math.sqrt(calculateGradient(picture.get(x, y - 1), picture.get(x, y + 1))
		        + calculateGradient(picture.get(x - 1, y), picture.get(x + 1, y)));
	}
	
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		int W = width();
		int H = height();
		
		List<Map.Entry<Double, Integer>> workingList = java.util.stream.IntStream.range(0, H)
				.mapToObj(x -> new AbstractMap.SimpleEntry<Double, Integer>(0d, 0))
				.collect(java.util.stream.Collectors.toList());
		List<List<Map.Entry<Double, Integer>>> energies = new ArrayList<List<Map.Entry<Double, Integer>>>();
		energies.add(workingList);
		
		for (int x = 0; x < W; x++) {
			final int X = x;
			workingList = java.util.stream.IntStream.range(0, H)
					.mapToObj(z -> DIRECTION.stream().map(d -> z + d).filter(y -> y >= 0 && y < H)
						.map(y -> new AbstractMap.SimpleEntry<Double, Integer>(
			                energies.get(X).get(y).getKey() + energy(X, y), y))
						.min(Comparator.comparing(s -> s.getKey()))
						.get())
					.collect(java.util.stream.Collectors.toList());
			energies.add(workingList);
		}
		
		int minPosition = java.util.stream.IntStream.range(0, H).boxed().min(
		    Comparator.comparing(s -> energies.get(W).get(s).getKey())).get();
		int pos = energies.get(W).get(minPosition).getValue();
		Stack<Integer> s = new Stack<Integer>();
		s.push(energies.get(W).get(minPosition).getValue());
		for (int x = W - 1; x > 0; x--) {
			s.push(energies.get(x).get(pos).getValue());
			pos = energies.get(x).get(pos).getValue();
		}
		Collections.reverse(s);
		int ret[] = s.stream().mapToInt(Integer::valueOf).toArray();
		return ret;
	}
	
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		int W = width();
		int H = height();
		
		List<Map.Entry<Double, Integer>> workingList = java.util.stream.IntStream.range(0, W)
				.mapToObj(x -> new AbstractMap.SimpleEntry<Double, Integer>(0d, 0))
				.collect(java.util.stream.Collectors.toList());
		List<List<Map.Entry<Double, Integer>>> energies = new ArrayList<List<Map.Entry<Double, Integer>>>();
		energies.add(workingList);
		
		for (int y = 0; y < H; y++) {
			final int Y = y;
			workingList = java.util.stream.IntStream.range(0, W)
					.mapToObj(z -> DIRECTION.stream()
						.map(d -> z + d).filter(x -> x >= 0 && x < W)
						.map(x -> new AbstractMap.SimpleEntry<Double, Integer>(
			                energies.get(Y).get(x).getKey() + energy(x, Y), x))
						.min(Comparator.comparing(s -> s.getKey()))
						.get())
					.collect(java.util.stream.Collectors.toList());
			energies.add(workingList);
		}
		
		int minPosition = java.util.stream.IntStream
				.range(0, W).boxed()
				.min(Comparator.comparing(s -> energies.get(H).get(s).getKey()))
				.get();
		int pos = energies.get(H).get(minPosition).getValue();
		Stack<Integer> s = new Stack<Integer>();
		s.push(pos);
		for (int y = H - 1; y > 0; y--) {
			s.push(energies.get(y).get(pos).getValue());
			pos = energies.get(y).get(pos).getValue();
		}
		Collections.reverse(s);
		int ret[] = s.stream().mapToInt(Integer::valueOf).toArray();
		return ret;
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		validateSeam(seam, false);
		
		Picture newPicture = new Picture(width(), height() - 1);
		for (int x = 0; x < width(); x++) {
			for (int y = 0, newy = 0; y < height(); y++) {
				if (y != seam[x]) {
					newPicture.set(x, newy, picture.get(x, y));
					newy++;
				}
			}
		}
		this.picture = newPicture;
	}
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		validateSeam(seam, true);
		
		Picture newPicture = new Picture(width() - 1, height());
		for (int y = 0; y < height(); y++) {
			for (int x = 0, newx = 0; x < width(); x++) {
				if (x != seam[y]) {
					newPicture.set(newx, y, picture.get(x, y));
					newx++;
				}
			}
		}
		
		this.picture = newPicture;
	}
	
	private double calculateGradient(Color first, Color second) {
		int r = Math.abs(first.getRed() - second.getRed());
		int g = Math.abs(first.getGreen() - second.getGreen());
		int b = Math.abs(first.getBlue() - second.getBlue());
		
		return (double) (r * r + g * g + b * b);
	}
	
	private void validateSeam(int[] seam, boolean isVertical) {
		if (seam == null)
			throw new NullPointerException("seam");
		
		if (isVertical && seam.length != height())
			throw new IllegalArgumentException("seam - vertical length should be " + height());
		
		if (isVertical && width() == 1)
			throw new IllegalArgumentException("width - 1");
		
		if (!isVertical && seam.length != width())
			throw new IllegalArgumentException("seam - horizontal length should be " + width());
		
		if (!isVertical && height() == 1)
			throw new IllegalArgumentException("height - 1");
		
		int min = 0;
		int max = height() - 1;
		if (isVertical)
			max = width() - 1;
		
		for (int i = 0; i < seam.length; i++) {
			if (i != 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
				throw new IllegalArgumentException("seam - " + i);
			}
			if (seam[i] < min || seam[i] > max) {
				throw new IllegalArgumentException("seam - " + i);
			}
		}
	}
	
	public static void main(String args[]) {
		
		Arrays.asList(Arrays.asList(0, 0, 14, 14), Arrays.asList(0, 6, 8, 14), Arrays.asList(6, 6, 8, 8))
			.stream().forEach( l -> {
					Accumulator stats = new Accumulator();
					l.stream().forEach(e -> stats.addDataValue(e));
			        StdOut.printf("N      = %d\n",   stats.count());
			        StdOut.printf("mean   = %.5f\n", stats.mean());
			        StdOut.printf("stddev = %.5f\n", stats.stddev());
			        StdOut.printf("var    = %.5f\n", stats.var());
			});
			        
	}
}
