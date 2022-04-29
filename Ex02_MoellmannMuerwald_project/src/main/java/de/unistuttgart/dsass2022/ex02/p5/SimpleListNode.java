package de.unistuttgart.dsass2022.ex02.p5;

/**
 * Implementation for a Node of a simple Linked List. 
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 * @param <T> Type of Elements
 */
public class SimpleListNode<T extends Comparable<T>> implements ISimpleListNode<T>{
	private T element;
	private ISimpleListNode<T> next;
	
	/**
	 * Constructor for SimpleListNode
	 * 
	 * @param element
	 * @param nextNode
	 */
	public SimpleListNode(final T element, final ISimpleListNode<T> nextNode) {
		this.element = element;
		this.next = nextNode;
	}   
	
	/**
	 * Constructor for not-initialized SimpleListNode
	 */
	public SimpleListNode() {
		element = null;
		next = null;
	}

	@Override
	public T getElement() {
		return element;
	}

	@Override
	public void setElement(final T element) {
		this.element = element;
	}

	@Override
	public ISimpleListNode<T> getNext() {
		return next;
	}

	@Override
	public void setNext(final ISimpleListNode<T> node) {
		this.next = node;
	}

}
