package de.unistuttgart.dsass2022.ex08.p3;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Class for Traversal of a Tree (or Graph).
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 */
public class TreeTraversal {
	
	enum Color {
		BLACK, BLUE, WHITE;
	}
	
	
	/**
	 * This method traverses the tree using depth first search. To eliminate any ambiguity choosing the next node,
	 * the node with the smallest index is visited next.
	 * @param weights adjacency matrix defining the graph. Since only you are using this method
	 * calculating the ford_fulkerson algorithm, you can expect weights to be rectangular
	 * @param s the id of the node to start the dfs on
	 * @return array with predecessors. I.e. pi[5] = 2 means, that node 2 is predecessor of node 5.
	 */
	public static int[] dfs(final ArrayList<ArrayList<Integer>> weights, final int s) throws IllegalArgumentException{
		final Color[] colors = new Color[weights.size()];
		final int[] predecessors = new int[weights.size()];
		Arrays.fill(predecessors, -1);
		for(int i = 0; i<weights.size(); i++) {
			colors[i] = Color.WHITE;
		}
		dfsVisit(weights, s, colors, predecessors);
		return predecessors;
	}
	
	/**
	 * This method traverses a graph using depth first search. 
	 * @param weights adjacency matrix of graph
	 * @param i index of starting node
	 * @param colors current color-status of all nodes (white, blue or black)
	 * @param predecessors array of currently known predecessors 
	 */
	private static void dfsVisit(final ArrayList<ArrayList<Integer>> weights, final int i, final Color[] colors, final int[] predecessors) {
		colors[i] = Color.BLUE;
		for(int j = 0; j<weights.size(); j++) {
			if(weights.get(i).get(j) > 0 && colors[j] == Color.WHITE) {
				predecessors[j] = i;
				dfsVisit(weights, j, colors, predecessors);
			}
		}
		colors[i] = Color.BLACK;
	} 
}
