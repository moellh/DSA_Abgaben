package de.unistuttgart.dsass2022.ex07.p2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Class for calculating the shortest path in a graph from a startnode to an arbitrary destiation.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 */
public class ShortestPath implements IShortestPath {

	private final IWeightedGraph graph;
	private final long startNode;

	/*
	 * syntactic sugar function to conveniently create shortest path objects from
	 * graphs
	 */
	public static ShortestPath calculateFor(final IWeightedGraph g, final long startNode) {
		return new ShortestPath(g, startNode);
	}

	/**
	 * Initializes the shortest path for weighted graph <tt>graph</tt> from starting
	 * node <tt>startNode</tt>. Calls the dijkstra(graph, startNode) method to
	 * execute the Dijkstra algorithm.
	 * 
	 * @param graph     the weighted graph
	 * @param startNode the starting node
	 */
	public ShortestPath(final IWeightedGraph graph, final long startNode) {
		this.graph = graph;
		this.startNode = startNode;
		dijkstra(this.graph, this.startNode);
	}

	@Override
	public void dijkstra(final IWeightedGraph graph, final long startnode) {
		allNodesWithInfiniteDistance(graph);
		graph.getNode(startnode).setDistance(0);
		final PriorityQueue<Node> queue = priorityQueueOfAllNodes(graph);
		while(!queue.isEmpty()) {
			final INode nodeWithMinimalDistance = queue.remove();
			final Iterator<IEdge> outgoingEdges = graph.outgoingEdges(nodeWithMinimalDistance.getID());
			while(outgoingEdges.hasNext()) {
				final IEdge outgoingEdge = outgoingEdges.next();
				final Node destination = graph.getNode(outgoingEdge.getDestination());
				final double newDistance = nodeWithMinimalDistance.getDistance() + outgoingEdge.getWeight();
				if(queue.contains(destination) && newDistance < destination.getDistance()) {
					setDistanceInPriorityQueue(newDistance, destination, queue);
				}
			}
		}
	}
	
	/**
	 * Sets distance of all graph-nodes to <tt>Double.POSITIVE_INFINITE</tt>.
	 * 
	 * @param graph
	 */
	private void allNodesWithInfiniteDistance(final IWeightedGraph graph) {
		final Iterator<Node> nodes = graph.nodeIterator();
		while(nodes.hasNext()) {
			final Node node = nodes.next();
			node.setDistance(Double.POSITIVE_INFINITY);
		}
	}
	
	/**
	 * Returns a {@link java.util.PriorityQueue#PriorityQueue PriorityQueue} that holds all nodes of {@link #graph}.
	 * Polling a node from this queue will remove that node with the lowest distance-attribute.
	 * 
	 * 
	 * @param graph
	 * @return PriorityQueue with nodes of {@link #graph}
	 * @see {@link Node#compareTo(Node o)}
	 */
	private PriorityQueue<Node> priorityQueueOfAllNodes(final IWeightedGraph graph) {
		final PriorityQueue<Node> queue = new PriorityQueue<>();
		final Iterator<Node> nodes = graph.nodeIterator();
		while(nodes.hasNext()) {
			final Node node = nodes.next();
			queue.add(node);
		}
		return queue;
	}
	
	/**
	 * Sets the distance of {@link #node} which is inside {@link #queue}.
	 * <br>(This requires special treatment, therefore this method.)
	 * 
	 * @param newDistance
	 * @param node
	 * @param queue
	 */
	private void setDistanceInPriorityQueue(final double newDistance, final Node node, final PriorityQueue<Node> queue) {
		if(!queue.contains(node)) {
			throw new IllegalArgumentException();
		}
		queue.remove(node);
		node.setDistance(newDistance);
		queue.add(node);
	}
	
	@Override
	public double distanceTo(final long destination) {
		return graph.getNode(destination).getDistance();
	}

	@Override
	public boolean existsPathTo(final long destination) {
		final INode destinationNode = graph.getNode(destination);
		return destinationNode.getDistance() == Double.POSITIVE_INFINITY;
	}

	@Override
	public Iterable<IEdge> pathTo(final long destination) {
		final LinkedList<IEdge> pathEdges = new LinkedList<>();
		
		INode destinationNode = graph.getNode(destination);
		while(startNode != destinationNode.getID()) {
			try {
				final IEdge edge = getPathPredecessorEdge(destinationNode);
				pathEdges.addFirst(edge);
				destinationNode = graph.getNode(edge.getSource());
			} catch (final IllegalStateException e) {
				e.printStackTrace();
			}
		}
		
		return pathEdges;
	}
	
	/**
	 * Returns the Edge from {@link #node}' predecessor to {@link #node} according to its shortest path to startNode.
	 * Does not work for nodes of that are not part of this graph and that are not part of this graph-component.
	 * 
	 * @param node
	 * @return predecessor edge to this node
	 * @throws Exception
	 */
	private IEdge getPathPredecessorEdge(final INode node) throws IllegalStateException {
		final Iterator<IEdge> allEdges = graph.edgeIterator();
		while(allEdges.hasNext()) {
			final IEdge edge = allEdges.next();
			final INode source = graph.getNode(edge.getSource());
			final INode destination = graph.getNode(edge.getDestination());
			final boolean fittingDistance = source.getDistance() + edge.getWeight() - destination.getDistance() < 0.000001d;
			if(destination == node && fittingDistance) {
				return edge;
			}
		}
		throw new IllegalStateException();
	}
}
