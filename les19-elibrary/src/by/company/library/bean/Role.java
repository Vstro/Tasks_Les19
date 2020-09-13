package by.company.library.bean;

public enum Role {
	ADULT, JUNIOR;
	
	// Наибольшая длина строки элемента
	public static int maxLength() {
		return JUNIOR.toString().length();
	}
}