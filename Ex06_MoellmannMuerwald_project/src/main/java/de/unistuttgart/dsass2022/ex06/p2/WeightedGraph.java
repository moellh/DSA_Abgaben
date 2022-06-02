package de.unistuttgart.dsass2022.ex06.p2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementation of a WeightedGraph that holds Integers as its Nodes.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 * @param <N> as Node Meta-Data
 * @param <E> as Edge Meta-Data
 */
public class WeightedGraph<N,E> implements IWeightedGraph<N,E> {

	private boolean isAlreadyCreated;
	private int numNodes;
	private int numEdges;
	private final HashMap<Integer ,ArrayList<IEdge<E>>> adjacencyList;
	
	/**
	 * Initializes an empty graph without nodes and edges.
	 */
	public WeightedGraph() {
		this.numNodes = 0;
		this.numEdges = 0;
		this.adjacencyList = new HashMap<>();
		this.isAlreadyCreated = false;
	}
	
	
	@Override
	public void createFromEdgeList(final ArrayList<Integer> list) throws UnsupportedOperationException{
		if(list.size()<2){
			throw new IllegalArgumentException();
		}
		if(isAlreadyCreated){
			throw new UnsupportedOperationException();
		}
		
		for(int i = 0; i<list.get(0); i++) {
			addNode(i,null);
		}
		
		for(int i = 2; i<list.get(1)*2+2; i+=2) {
			final int source = list.get(i);
			final int destination = list.get(i+1);
			if(source >= list.get(0) || destination >= list.get(0) || source < 0 || destination < 0) {
				throw new IllegalArgumentException();
			}
			final ArrayList<IEdge<E>> edgesOfSource = adjacencyList.get(source);
			if(!edgesOfSource.contains(destination)) {
				addEdge(source, destination, 1);
			}
		}
		
		isAlreadyCreated = true;
		
	}
	
	
	@Override
	public ArrayList<Integer> toEdgeList(){

		final ArrayList<Integer> edgeList = new ArrayList<>();
		edgeList.add(numNodes);
		edgeList.add(numEdges);
		final Iterator<IEdge<E>> allEdgesIterator = this.edgeIterator();
		while(allEdgesIterator.hasNext()){
			final IEdge<E> currentEdge = allEdgesIterator.next();
			edgeList.add(currentEdge.getSource());
			edgeList.add(currentEdge.getDestination());
		}
		
		return edgeList;
	}
	
	@Override
	public void createFromNodeList(final ArrayList<Integer> list) throws UnsupportedOperationException{
		if(list.size()<2 || list.size() < list.get(0) + list.get(1) + 2){
			throw new IllegalArgumentException();
		}
		if(isAlreadyCreated){
			throw new UnsupportedOperationException();
		}

		for(int i = 0; i<list.get(0); i++) {
			addNode(i,null);
		}
		
		int currentNodeIndex = 2;
		
		for(int i = 0; i < numNodes; i++){
			final int nodeIndex = currentNodeIndex;
			final int numberOfEdges = list.get(nodeIndex);
			currentNodeIndex = currentNodeIndex + numberOfEdges + 1;
			if(list.get(nodeIndex)>0){
				for(int j = nodeIndex+1; j < currentNodeIndex; j++){
					final IEdge<E> edge = new Edge(i, list.get(j), 1.0);
					this.addEdge(edge);
				}
			}
		}
		
		isAlreadyCreated = true;
	}
	
	@Override
	public ArrayList<Integer> toNodeList(){
		final ArrayList<Integer> nodeList = new ArrayList<>();
		nodeList.add(numNodes);
		nodeList.add(numEdges);
		
		for(int i = 0; i < numNodes; i++){
			final Iterator<IEdge<E>> currentEdgesIterator = this.outgoingEdges(i);
			int currentNumberOfEdges = 0;
			final ArrayList<Integer> destinationNodeList = new ArrayList<>();
			while(currentEdgesIterator.hasNext()){
				currentNumberOfEdges++;
				destinationNodeList.add(currentEdgesIterator.next().getDestination());
			}
			nodeList.add(currentNumberOfEdges);
			if(!destinationNodeList.isEmpty()){
				nodeList.addAll(destinationNodeList);
			}
		}
		
		return nodeList;
	}
	
	@Override
	public int numberOfNodes() {
		return this.numNodes;
	}

	@Override
	public int numberOfEdges() {
		return this.numEdges;
	}
	
	/**
	 * Adds a new node with the specified meta data to this graph.
	 * @param nodeMetaData	meta data of the added node
	 * @return the new node's index
	 */
	public int addNode(final int nodeID ,final N nodeMetaData){
		this.adjacencyList.put(nodeID, new ArrayList<>(1));
		return this.numNodes++;
	}
	
	/**
	 * Adds a new edge from node index <tt>src</tt> to node index <tt>dst</tt>.
	 * @param src index of the source node
	 * @param dst index of the destination node
	 * @param weight weight of the edge
	 * @return the created edge object.
	 */
	public Edge<E> addEdge(final int src, final int dst, final double weight){
		final Edge<E> toAdd = new Edge<>(src, dst, weight);
		this.addEdge(toAdd);
		return toAdd;
	}

	/**
	 * Adds the specified edge to this graph.
	 * @param edge	the edge that should be added
	 */
	public void addEdge(final IEdge<E> edge) {
		final int source = edge.getSource();
		this.adjacencyList.get(source).add(edge);
		this.numEdges++;
	}
	
	@Override
	public Iterator<IEdge<E>> edgeIterator() {
		final ArrayList<IEdge<E>> edgeList = new ArrayList<IEdge<E>>(numEdges);
		for (int i = 0; i < this.numNodes; i++) {
			edgeList.addAll(this.adjacencyList.get(i));
		}
		return edgeList.iterator();
	}

	@Override
	public Iterator<IEdge<E>> outgoingEdges(final int src) {
		return this.adjacencyList.get(src).iterator();
	}

}
