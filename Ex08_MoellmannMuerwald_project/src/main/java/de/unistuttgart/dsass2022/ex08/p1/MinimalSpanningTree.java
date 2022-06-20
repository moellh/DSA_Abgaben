package de.unistuttgart.dsass2022.ex08.p1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;



public class MinimalSpanningTree{


	/**
	 * This method calculates the minimal spanning tree using the kruskal algorithm.
	 * @param graph the graph object to calculate the MST for
	 * @return a set of edges, which belong to the MST of the given graph
	 */
	public static Set<IEdge> kruskal(final IWeightedGraph graph){
		final SortedSet<IEdge> sortedEdges = getSortedEdges(graph);
		final Set<IEdge> minimalSpanningTreeEdges = new HashSet<>();
		final List<Set<Long>> alreadyAcyclicNodes = getNodeSets(graph);
		while(!sortedEdges.isEmpty() && minimalSpanningTreeEdges.size() < graph.numberOfNodes()-1) {
			final IEdge edge = sortedEdges.first();
			final Set<Long> originSet = findSet(alreadyAcyclicNodes, edge.getSource());
			final Set<Long> destinationSet = findSet(alreadyAcyclicNodes, edge.getDestination());
			if(!originSet.equals(destinationSet)) {
				originSet.addAll(destinationSet);
				System.out.println("------\n" + alreadyAcyclicNodes.size());
				alreadyAcyclicNodes.remove(destinationSet);
				System.out.println(alreadyAcyclicNodes.size());
				minimalSpanningTreeEdges.add(edge);
			}
			sortedEdges.remove(edge);
		}
		
		return minimalSpanningTreeEdges;
	}
	
	private static List<Set<Long>> getNodeSets(final IWeightedGraph graph) {
		final List<Set<Long>> alreadyAcyclicNodes = new ArrayList<>();
		final Iterator<Long> nodeIterator = graph.nodeIDIterator();
		while(nodeIterator.hasNext()) {
			alreadyAcyclicNodes.add(new HashSet<>(Arrays.asList(nodeIterator.next())));
		}
		return alreadyAcyclicNodes;
	}
	
	private static Set<Long> findSet(final List<Set<Long>> alreadyAcyclicNodes, final long node) {
		final Iterator<Set<Long>> iterator = alreadyAcyclicNodes.iterator();
		while(iterator.hasNext()) {
			final Set<Long> acyclicNodes = iterator.next();
			if(acyclicNodes.contains(node)) {
				return acyclicNodes;
			}
		}
		throw new IllegalArgumentException();
	}

	private static SortedSet<IEdge> getSortedEdges(final IWeightedGraph graph) {
		final SortedSet<IEdge> sortedEdges = new TreeSet<>();
		
		final Iterator<IEdge> edgeIterator = graph.edgeIterator();
		while(edgeIterator.hasNext()) {
			sortedEdges.add(edgeIterator.next());
		}
		
		return sortedEdges;
	}
}
