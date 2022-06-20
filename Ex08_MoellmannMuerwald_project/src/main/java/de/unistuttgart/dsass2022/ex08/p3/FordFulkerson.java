package de.unistuttgart.dsass2022.ex08.p3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.xml.dtm.ref.ExpandedNameTable;
/**
 * Class for calculating the maximum flow of a graph using the Ford Fulkerson Algorithm.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 */
public class FordFulkerson implements IFordFulkerson{
	private ArrayList<ArrayList<Integer>> remainingNetwork = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> flow = new ArrayList<>();
	private boolean calculated = false;
	
	@Override
	public int calculate(final ArrayList<ArrayList<Integer>> graph, final int s, final int t) {	
		for(ArrayList<Integer> row : graph) {
			remainingNetwork.add((ArrayList<Integer>) row.clone());
			final ArrayList<Integer> rowOfZeros = (ArrayList<Integer>) row.clone();
			Collections.fill(rowOfZeros, 0);
			flow.add((ArrayList<Integer>) rowOfZeros);
		}
		
		int[] path = TreeTraversal.dfs(remainingNetwork, s);
		boolean noMorePaths = (path[t] == -1);
		while(!noMorePaths){
			final int pathMaxPossibleFlow = findMaxPossibleFlow(path, t);
			updateFlowAndRemainingNetwork(path,pathMaxPossibleFlow, t);
			path = TreeTraversal.dfs(remainingNetwork, s);
			if(path[t] == -1) {
				noMorePaths = true;
			}
		}
		
		calculated = true;
		return flow.get(s).stream().mapToInt(Integer::intValue).sum();
	}
	/**
	 * Updates Flow matrix and remainingNetwork matrix after adding a path.
	 * @param path that was added
	 * @param pathMaxPossibleFlow - maximum possible flow when following that path
	 * @param destination of path
	 */
	private void updateFlowAndRemainingNetwork(final int[] path, final int pathMaxPossibleFlow, final int destination) {
		int successor = destination;
		int predecessor = path[destination];
		while(predecessor != -1){
			final int newCapacity = remainingNetwork.get(predecessor).get(successor) - pathMaxPossibleFlow;
			remainingNetwork.get(predecessor).set(successor, newCapacity);
			final int newFlow = flow.get(predecessor).get(successor) + pathMaxPossibleFlow;
			flow.get(predecessor).set(successor, newFlow);
			successor = predecessor;
			predecessor = path[successor];
		}
		
	}
	/**
	 * Finds maximum possible Flow along a path in the graph, 
	 * i.e. the minimal capacity along each edge in the given path.
	 * 
	 * @param path
	 * @param destination
	 * @return
	 */
	private int findMaxPossibleFlow(final int[] path, final int destination) {
		int maxFlow = Integer.MAX_VALUE;
		int successor = destination;
		int predecessor = path[destination];
		while(predecessor != -1){
			maxFlow = Integer.min(remainingNetwork.get(predecessor).get(successor),maxFlow);
			successor = predecessor;
			predecessor = path[successor];
		}
		return maxFlow;
	}

	@Override
	public ArrayList<ArrayList<Integer>> flow() {
		if(!calculated) {
			throw new IllegalStateException();
		}
		return flow;
	}
	
}
