package de.unistuttgart.dsass2022.ex04.p2;

/**
 * A node for an AVL-Tree (-> {@link #IAVLTree}). Holds a value, a left & right child-node and its balance.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 * @param <T>
 */
public class AVLNode<T extends Comparable<T>> implements IAVLNode<T> {

	private int balance;
	private T value;
	private IAVLNode<T> leftChild, rightChild;
	
	/**
	 * Constructor for an AVLNode that holds no child-node neighter any value.
	 */
	public AVLNode() {
		balance = 0;
		value = null;
		leftChild = null;
		rightChild = null;
	}

	/**
	 * Constructor for an AVLNode that holds no child-node but a set value.
	 */
	public AVLNode(final T value){
		balance = 0;
		this.value = value;
		leftChild = null;
		rightChild = null;
	}
	
	@Override
	public void setValue(final T val) {
		value = val;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setLeftChild(final IAVLNode<T> left) {
		leftChild = left;
	}

	@Override
	public IAVLNode<T> getLeftChild() {
		return leftChild;
	}
	
	@Override
	public void setRightChild(final IAVLNode<T> right) {
		rightChild = right;
	}

	@Override
	public IAVLNode<T> getRightChild() {
		return rightChild;		
	}

	@Override
	public void setBalance(final int bal) {
		balance = bal;
	}

	@Override
	public int getBalance() {
		return balance;
	}

}
