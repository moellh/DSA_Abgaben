package de.unistuttgart.dsass2022.ex03.p5;

/**
 * A basic Binary-Tree Node that hold a value of type {@link #T}, a left and a right node.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 * @param <T>
 */
public class BinaryTreeNode<T extends Comparable<T>> implements IBinaryTreeNode<T> {
	
	private T value;
	private IBinaryTreeNode<T> leftNode, rightNode;
	
	/**
	 * Creates an empty Binary-Tree Node that does not hold a value neighter a left or right node.
	 * Those properties need to be assigned otherwise this BinaryTreeNode equals a <strong>nullNode<strong>.  
	 */
	public BinaryTreeNode() {
		value = null;
		leftNode = null;
		rightNode = null;
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
	public void setLeftChild(final IBinaryTreeNode<T> left) {
		leftNode = left;
	}

	@Override
	public IBinaryTreeNode<T> getLeftChild() {
		return leftNode;
	}

	@Override
	public void setRightChild(final IBinaryTreeNode<T> right) {
		rightNode = right;
	}

	@Override
	public IBinaryTreeNode<T> getRightChild() {
		return rightNode;
	}


}
