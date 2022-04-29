package de.unistuttgart.dsass2022.ex02.p5;

/**
 * Implementation of a linked list that uses bubbleSort for ({@link #sort()}})
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 * @param <T> Type of Elements
 */
public class SimpleList<T extends Comparable<T>> implements ISimpleList<T> {
	
	private final ISimpleListNode<T> head;
	private int size;

	/**
	 * Constructor for an empty SimpleList.
	 */
	public SimpleList() {
		head = new SimpleListNode<T>();
		size = 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void prepend(final T element) {
		final ISimpleListNode<T> newSecondNode = head.getNext();
		final ISimpleListNode<T> newFirstNode = new SimpleListNode<T>(element, newSecondNode);
		head.setNext(newFirstNode);
		size++;
	}

	@Override
	public T getElement(final int index) {
		if(index >= getSize()) {
			throw new IndexOutOfBoundsException();
		}
		ISimpleListNode<T> node = head.getNext();
		for(int i = 0; i < index; i++) {
			node = node.getNext();
		}
		return node.getElement();
	}

	@Override
	public void sort() {
		boolean noSwapsThisIteration = false;
		while(!noSwapsThisIteration) {
			noSwapsThisIteration = true;
			ISimpleListNode<T> previousNode = head;
			for(int i = 0; i<size-1; i++) {
				final ISimpleListNode<T> currentNode = previousNode.getNext();
				final ISimpleListNode<T> nextNode = currentNode.getNext();
				if(currentNode.getElement().compareTo(nextNode.getElement()) > 0) {
					swapConsecutiveNodes(previousNode);
					noSwapsThisIteration = false;
				}
				
				previousNode = previousNode.getNext();
			}
		}
	}
	
	/**
	 * Swaps the two nodes following {@link #previousNode}, if both of them are not null.
	 * 
	 * @param previousNode
	 */
	private void swapConsecutiveNodes(final ISimpleListNode<T> previousNode) {
		final ISimpleListNode<T> currentNode = previousNode.getNext();
		final ISimpleListNode<T> nextNode = currentNode.getNext();
		
		assert(currentNode != null && nextNode != null);
		
		previousNode.setNext(nextNode);
		currentNode.setNext(nextNode.getNext());
		nextNode.setNext(currentNode);
	}
	
}
