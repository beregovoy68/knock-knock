package net.readify.knockknock;

public enum TriangleType {
	SCALENE("Scalene"),
	ISOSCELES("Isosceles"),
	EQUILATERAL("Equilateral"),
	ERROR("Error");
	
	private String name;

	/**
	 * @param name - Triangle type name
	 */
	private TriangleType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
