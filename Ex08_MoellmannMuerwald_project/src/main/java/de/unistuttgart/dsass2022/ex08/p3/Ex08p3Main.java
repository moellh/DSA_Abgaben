package de.unistuttgart.dsass2022.ex08.p3;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Testing Class for FordFulkerson and TreeTraversal
 * Underlying Graph: Assignment 08, Exercise 2b.
 */
public class Ex08p3Main {

	private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>(9);
	
	public static void main(final String[] args) {

		graph.add(new ArrayList<>(Arrays.asList(0,0,0,9,0,3,0,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,4,0,0,0,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,5,0,7,18,0,0,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,0,0,7,3,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,10,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,20)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,5)));
		graph.add(new ArrayList<>(Arrays.asList(12,0,10,0,0,0,0,0,0)));
		graph.add(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0)));
		final int source = 7;
		final int destination = 8;

		final IFordFulkerson fordFulkerson = new FordFulkerson();
		System.out.println(fordFulkerson.calculate(graph, source, destination));
		final ArrayList<ArrayList<Integer>> flow = fordFulkerson.flow();
		System.out.println("final flow");
		printMatrix(flow);
	}

	public static void printMatrix(final ArrayList<ArrayList<Integer>> graph) {
		for(final ArrayList<Integer> row : graph) {
			row.stream().map(s -> s.toString()+" ").forEach(System.out::print);;
			System.out.println();
		}
	}

}
