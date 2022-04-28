package de.unistuttgart.dsass2022.ex02.p4;

public class MainTest {

	public static void main(final String[] args) {
		
		for(int i = 1; i<10; i++) {
			System.out.println("for n=" + i + " \tcould1: " + Complexity.couldBeBetter1(i) + "  is1: " + Complexity.isDoneBetter1(i) 
			+ " \t-> Same?: " + (Complexity.couldBeBetter1(i) == Complexity.isDoneBetter1(i)));
		}
		
		System.out.println("\n-----\n");
		
		for(int i = 1; i<10; i++) {
			System.out.println("for n=" + i + " \tcould2: " + Complexity.couldBeBetter2(i) + "  is2: " + Complexity.isDoneBetter2(i) 
			+ " \t-> Same?: " + (Complexity.couldBeBetter2(i) == Complexity.isDoneBetter2(i)));
		}
		
		System.out.println("\n-----\n");
		
		for(int i = 1; i<10; i++) {
			System.out.println("for n=" + i + " \tcould3: " + Complexity.couldBeBetter3(i) + "  is3: " + Complexity.isDoneBetter3(i) 
			+ " \t-> Same?: " + (Complexity.couldBeBetter3(i) == Complexity.isDoneBetter3(i)));
		}
		
	}
	
}
