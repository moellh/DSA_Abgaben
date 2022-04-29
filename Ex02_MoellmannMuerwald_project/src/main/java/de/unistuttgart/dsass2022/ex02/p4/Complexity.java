package de.unistuttgart.dsass2022.ex02.p4;
 

/**
 * Contains 3 Number-Returning Operations based on a positive value n.
 * There is a worse and better version for each operation.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 */
public class Complexity {
	
	
	/**
	 * Returns (2*n + 2) using a Loop.
	 * It is worse than {@link #isDoneBetter1(int)}
	 * 
	 * @param n positive integer n
	 * @return 2*n + 1
	 */
	public static int couldBeBetter1(final int n) {
		int result = 0;
		for (int i = 0; i <= n; i++) {
			result += 2;
		}
		return result;
	}
	
	
	/**
	 * Returns some number but is worse than {@link #isDoneBetter2(int)}.
	 * 
	 * @param n positive integer n
	 * @return some new number based on n
	 */
	public static int couldBeBetter2(final int n) {
		int temp = 1;
		int result = 1;
		for (int i = 1; i <= n; i++) {
			result = 0;
			for (int j = 1; j <= i; j++) {
				result += temp;
			}
			temp = result;
		}
		return result;
	}
	
	/**
	 * Returns the value of the Fibonacci-Algorithm with Paramter n.
	 * It is worse than {@link #isDoneBetter3(int)}
	 * 
	 * @param n positive integer
	 * @return fibonacci-number based on n
	 */
	public static int couldBeBetter3(final int n) {
		if (n < 0) throw new IllegalArgumentException("n has to be positive!");
		else if (n <= 1) return 1;
		else return couldBeBetter3(n-1)+ couldBeBetter3(n-2);
	}
	
	
	/**
	 * Returns (2*n + 2) using by Multiplying and Adding.
	 * It is also better than {@link #couldBeBetter1(int)}.
	 * 
	 * @param n positive integer
	 * @return same number as {@link #couldBeBetter1(int)}, but faster
	 */
	public static int isDoneBetter1(final int n) {
		return 2*n + 2;
	}
	
	
	/**
	 * Returns some number but is better than {@link #couldBeBetter2(int)}
	 * 
	 * @param n positive integer
	 * @return same number as {@link #couldBeBetter2(int)}, but faster
	 */
	public static int isDoneBetter2(final int n) {
		int temp = 1;
		int result = 1;
		for (int i = 1; i <= n; i++) {
			result = temp*i;
			temp = result;
		}
		return result;
	}

	
	/**
	 * Returns the value of the Fibonacci-Algorithm with Paramter n.
	 * It is also better than {@link #couldBeBetter3(int)}
	 * 
	 * @param n positive integer
	 * @return fibonacci-number based on n
	 */
	public static int isDoneBetter3(final int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n has to be positive!");
		}
		
		int lowerNumber = 1;
		int higherNumber = 1;
		
		for(int i = 0; i<n-1; i++) {
			final int tempNumber = higherNumber;
			higherNumber += lowerNumber;
			lowerNumber = tempNumber;
		}
		
		return higherNumber;
	}

}
