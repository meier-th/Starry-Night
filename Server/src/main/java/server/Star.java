package server;

import java.awt.Color;

public class Star implements Comparable <Star> {

	private int shine;
	private String name;
	private boolean visibleFromEarth;
	private boolean visibleFromMoon;
	private int[] coordinates = new int[2];
	private Color colour;

	@Override
	public int compareTo(Star otherSt) {
		return this.getShine() - otherSt.getShine();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Star(int shine, String name, int x, int y, boolean earth, boolean moon, Color colour) {
		this.name = name;
		this.shine = shine;
		this.coordinates[0] = x;
		this.coordinates[1] = y;
		this.visibleFromEarth = earth;
		this.visibleFromMoon = moon;
		this.colour = colour;
	}

	public void setShine(int shine) {
		this.shine = shine;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVisibleFromEarth(boolean visible) {
		this.visibleFromEarth = visible;
	}

	public void setVisibleFromMoon (boolean visible) {
		this.visibleFromMoon = visible;
	}

	public void setCoordinates(int x, int y) {
		this.coordinates[0] = x;
		this.coordinates[1] = y;
	}

	public void setColor(Color colour) {
		this.colour = colour;
	}

	public int getShine() {
		return this.shine;
	}

	public String getName() {
		return this.name;
	}

	public boolean visibleFromEarth() {
		return this.visibleFromEarth;
	}

	public boolean visibleFromMoon() {
		return this.visibleFromMoon;
	}

	public Color getColour() {
		return this.colour;
	}

	public int[] getCoordinates() {
		return this.coordinates;
	}
}
