package de.unistuttgart.dsass2022.ex04.p2;

/**
 * An AVL-Tree consisting of AVL-Nodes(-> {@link #IAVLNode}). Has a root node.
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 * @param <T>
 */
public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	/**
	 * Direction of a child-node
	 */
	private enum Direction {
		RIGHT, LEFT;
	}
	
	private volatile IAVLNode<T> root;
	private boolean rebalanceNeeded = false;
	
	/**
	 * Constructor for an AVLtree that has a null-node as a root.
	 */
	public AVLTree() {
		this.root = new AVLNode<T>();
	}

	@Override
	public void insert(final T t) {
		if(root.getValue() != null) {
			root = insertNode(root, t);
		}else {
			root.setValue(t);
		}
	}	

	/**
	 * Method that recursively inserts a value {@link #t} in a subtree with root {@link #parent} and balances 
	 * the tree according to AVL-tree-rules with left and right rotations.
	 *
	 * @param parent the root node of the subtree to insert value t into
	 * @param t the value to be inserted
	 * @result the root of the resulting new subtree with t inserted
	 */
	private IAVLNode<T> insertNode(final IAVLNode<T> parent, final T t) {
		final T parentValue = parent.getValue();
		
		if(parentValue.compareTo(t) == 0) {
			rebalanceNeeded = false;
			return parent;
		}
		
		if(t.compareTo(parentValue) > 0) {
			final IAVLNode<T> rightChild = parent.getRightChild();
			if(rightChild != null){
				parent.setRightChild(insertNode(rightChild, t));
				
				if(rebalanceNeeded){
					return rebalance(parent, Direction.RIGHT);
				} 
			} else {
				insertNodeAsLeaf(parent, t, Direction.RIGHT);
			}
		}else {
			final IAVLNode<T> leftChild = parent.getLeftChild();
			if(leftChild != null){
				parent.setLeftChild(insertNode(leftChild, t));
				
				if(rebalanceNeeded){
					return rebalance(parent, Direction.LEFT);
				}
			} else {
				insertNodeAsLeaf(parent, t, Direction.LEFT);
			}
		}
		return parent;
	}

	/**
	 * Rebalances subtree of parent-node {@link #direction} makes it necessary.
	 * 
	 * @param parent as 'old' parent node
	 * @param direction
	 * @return new parent node
	 */
	private IAVLNode<T> rebalance(final IAVLNode<T> parent, final Direction direction) {
		switch(parent.getBalance()){
			case -1:
				if(direction == Direction.RIGHT){
					return rebalanceRight(parent);
				} else {
					parent.setBalance(0);
					rebalanceNeeded = false;
				}
				break;
			case 0: 
				if(direction == Direction.RIGHT){
					parent.setBalance(-1);
				} else {
					parent.setBalance(1);
				}
				break;		
			case 1: 
				if(direction == Direction.RIGHT){
					parent.setBalance(0);
					rebalanceNeeded = false;
				} else {
					return rebalanceLeft(parent);
				}
				break;
		}
		return parent;
	}
	
	/**
	 * Inserts node with value {@link #t} as child node of {@link #parent}.
	 * Requires parent-node to have no child-node at insertion-position. Otherwise
	 * this node will be lost.
	 * 
	 * @param parent
	 * @param t value of new leaf-node
	 * @param direction
	 */
	private void insertNodeAsLeaf(final IAVLNode<T> parent, final T t, final Direction direction) {
		final IAVLNode<T> newNode = new AVLNode<T>(t);
		if(direction == Direction.RIGHT){
			parent.setRightChild(newNode);
			parent.setBalance(parent.getBalance()-1);
			rebalanceNeeded = (parent.getBalance() <= -1);
		} else {
			parent.setLeftChild(newNode);
			parent.setBalance(parent.getBalance()+1);
			rebalanceNeeded = (parent.getBalance() >= 1);
		}
	}
	/**
	 * Rebalances a tree if the AVL-Balance of {@link #node} is -2,
	 * i.e. performs one left rotation or one double rotation (right - left)
	 *
	 * @param node which AVL-balance is -2
	 * @result the root of the resulting rebalanced tree
	 */
	private IAVLNode<T> rebalanceRight(final IAVLNode<T> node) {
		IAVLNode<T> tempNode;
		if(node.getRightChild().getBalance() == -1){
			tempNode = rotateLeft(node);
			tempNode.getLeftChild().setBalance(0);
		} else {
			final int b = node.getRightChild().getLeftChild().getBalance();
			node.setRightChild(rotateRight(node.getRightChild()));
			tempNode = rotateLeft(node);
			tempNode.getRightChild().setBalance((b == 1) ? -1 : 0);
			tempNode.getLeftChild().setBalance((b == -1) ? 1 : 0);
		}
		tempNode.setBalance(0);
		rebalanceNeeded = false;
		return tempNode;
	}
	/**
	 * Rebalances a tree if the AVL-Balance of {@link #node} is +2,
	 * i.e. performs one right rotation or one double rotation (left - right)
	 *
	 * @param node which AVL-balance is +2
	 * @result the root of the resulting rebalanced tree
	 */
	private IAVLNode<T> rebalanceLeft(final IAVLNode<T> node) {
		IAVLNode<T> tempNode;
		if(node.getLeftChild().getBalance() == 1){
			tempNode = rotateRight(node);
			tempNode.getRightChild().setBalance(0);
		} else {
			final int b = node.getLeftChild().getRightChild().getBalance();
			node.setLeftChild(rotateLeft(node.getLeftChild()));
			tempNode = rotateRight(node);
			tempNode.getLeftChild().setBalance((b == 1) ? -1 : 0);
			tempNode.getRightChild().setBalance((b == -1) ? 1 : 0);
		}
		tempNode.setBalance(0);
		rebalanceNeeded = false;
		return tempNode;	
	}

	/** Simple Right-Rotation of parent-node.
	 * <p>
	 * Declares left child-node of parent-node as new parent-node of this subtree 
	 * and applies its left subtree as the new subtree the old parent-node. 
	 * 
	 * @param oldParentNode
	 * @return newParentNode
	 */
	private IAVLNode<T> rotateRight(final IAVLNode<T> oldParentNode) {
		final IAVLNode<T> newParentNode = oldParentNode.getLeftChild();
		oldParentNode.setLeftChild(newParentNode.getRightChild());
		newParentNode.setRightChild(oldParentNode);
		return newParentNode;
	}
	
	/** Simple Left-Rotation of parent-node.
	 * <p>
	 * Declares right child-node of parent-node as new parent-node of this subtree 
	 * and applies its right subtree as the new subtree the old parent-node. 
	 * 
	 * @param oldParentNode
	 * @return newParentNode
	 */
	private IAVLNode<T> rotateLeft(final IAVLNode<T> oldParentNode) {
		final IAVLNode<T> newParentNode = oldParentNode.getRightChild();
		oldParentNode.setRightChild(newParentNode.getLeftChild());
		newParentNode.setLeftChild(oldParentNode);
		return newParentNode;
	}

	@Override
	public IAVLNode<T> getRootNode() {
		return this.root;
	}
	
}
