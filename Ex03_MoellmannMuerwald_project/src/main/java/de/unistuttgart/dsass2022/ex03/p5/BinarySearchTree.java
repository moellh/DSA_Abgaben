package de.unistuttgart.dsass2022.ex03.p5;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements IBinarySearchTreeIterable<T> {

	private volatile IBinaryTreeNode<T> root;

	public BinarySearchTree() {
		this.root = null;
	}

	@Override
	public void insert(final T t) {
		this.root = this.insert(this.root, t);
	}

	private IBinaryTreeNode<T> insert(final IBinaryTreeNode<T> node, final T t) {
		if (node == null) {
			final IBinaryTreeNode<T> newNode = new BinaryTreeNode<>();
			newNode.setValue(t);
			return newNode;
		}
		if (t.compareTo(node.getValue()) < 0) {
			node.setLeftChild(this.insert(node.getLeftChild(), t));
		} else if (t.compareTo(node.getValue()) > 0) {
			node.setRightChild(this.insert(node.getRightChild(), t));
		}
		return node;
	}

	@Override
	public IBinaryTreeNode<T> getRootNode() {
		return this.root;
	}

	@Override
	public boolean isFull() {
		return nodeHasFullNodes(root);
	}
	
	/**
	 * Returns whether subtree of {@link #parentNode} is a 'full' tree
	 * 
	 * @param parentNode
	 * @return true if substree of {@link #parentNode}.
	 */
	private boolean nodeHasFullNodes(final IBinaryTreeNode<T> parentNode) {
		if( parentNode == null || (parentNode.getLeftChild() == null && parentNode.getRightChild() == null) ) {
			return true;
		}
		
		if( parentNode.getLeftChild() != null && parentNode.getRightChild() != null ) {
			return nodeHasFullNodes(parentNode.getLeftChild()) && nodeHasFullNodes(parentNode.getRightChild());
		}
		
		return false;
	}

	@Override
	public Iterator<T> iterator(final TreeTraversalType traversalType) {
		switch(traversalType) {
		case PREORDER: return new PreorderIterator(this);
		case INORDER: return new InorderIterator(this);
		case POSTORDER: return new PostorderIterator(this);
		default: return new LevelorderIterator(this);
		}
	}
	
	/**
	 * Iterates through values of this BinarySearchTree in Preorder-traversal:
	 * <p>
	 * Regarding a parent-node:<br>
	 * * 1. value of PARENT-node<br>
	 * * 2. values of LEFT subtree in Preorder-traversal<br>
	 * * 3. values of RIGHT subtree in Preorder-traversal
	 * 
	 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
	 */
	private class PreorderIterator implements Iterator<T>{
		Deque<IBinaryTreeNode<T>> stack = new LinkedList<>();

		/**
		 * Creates an Iterator of {@link #tree} that iterates in Preorder-traversal.
		 * @param tree
		 */
		public PreorderIterator(final IBinarySearchTree<T> tree) {
			if(tree.getRootNode() != null){ 
				stack.push(tree.getRootNode());
			}
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			final IBinaryTreeNode<T> node = stack.pop();
			final T value = node.getValue();
			if(node.getRightChild() != null){
				stack.push(node.getRightChild());
			}
			if(node.getLeftChild() != null){
				stack.push(node.getLeftChild());
			}
			return value;
		}
		
	}
	
	/**
	 * Iterates through values of this BinarySearchTree in Inorder-traversal:
	 * <p>
	 * Regarding a parent-node:<br>
	 * * 1. values of LEFT subtree in Inorder-traversal<br>
	 * * 2. value of PARENT-node<br>
	 * * 3. values of RIGHT subtree in Inorder-traversal
	 * 
	 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
	 */
	private class InorderIterator implements Iterator<T>{
		Deque<IBinaryTreeNode<T>> stack = new LinkedList<>();

		/**
		 * Creates an Iterator of {@link #tree} that iterates in Inorder-traversal.
		 * @param tree
		 */
		public InorderIterator(final BinarySearchTree<T> binarySearchTree) {
			pushSubtreeToStack(binarySearchTree.getRootNode());
		} 
	
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			final IBinaryTreeNode<T> node = stack.pop();
			return node.getValue();
		}
		
		/**
		 * Pushes nodes of currentNode's subtree to {@link #stack} in Inorder-traversal.
		 * 
		 * @param currentNode
		 */
		private void pushSubtreeToStack(final IBinaryTreeNode<T> currentNode) {
			if(currentNode.getRightChild() != null) {
				pushSubtreeToStack(currentNode.getRightChild());
			}
			stack.push(currentNode);
			if(currentNode.getLeftChild() != null) {
				pushSubtreeToStack(currentNode.getLeftChild());
			}
		}
		
	}
	
	/**
	 * Iterates through values of this BinarySearchTree in Postorder-traversal:
	 * <p>
	 * Regarding a parent-node:<br>
	 * * 1. values of LEFT subtree in Postorder-traversal<br>
	 * * 2. values of RIGHT subtree in Postorder-traversal<br>
	 * * 3. value of PARENT-node
	 * 
	 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
	 */
	private class PostorderIterator implements Iterator<T>{
		Deque<IBinaryTreeNode<T>> stack = new LinkedList<>();

		/**
		 * Creates an Iterator of {@link #tree} that iterates in Postorder-traversal.
		 * @param tree
		 */
		public PostorderIterator(final IBinarySearchTree<T> tree) {
			pushSubtreeToStack(tree.getRootNode());
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			final IBinaryTreeNode<T> node = stack.pop();
			return node.getValue();
		}
		
		/**
		 * Pushes nodes of currentNode's subtree to {@link #stack} in Postorder-traversal.
		 * 
		 * @param currentNode
		 */
		private void pushSubtreeToStack(final IBinaryTreeNode<T> currentNode) {
			stack.push(currentNode);
			if(currentNode.getRightChild() != null) {
				pushSubtreeToStack(currentNode.getRightChild());
			}
			if(currentNode.getLeftChild() != null) {
				pushSubtreeToStack(currentNode.getLeftChild());
			}
		}
		
	}
	
	/**
	 * Iterates through values of this BinarySearchTree in Levelorder-traversal:
	 * <p>
	 * Regarding the root-node:<br>
	 * * 1. value of root-node<br>
	 * * 2. values 2nd niveau nodes<br>
	 * * 3. values 3rd niveau nodes<br>
	 * * ...
	 * 
	 * @author Alina Mürwald (2838377), Henrik Möllmann (3537334)
	 */
	private class LevelorderIterator implements Iterator<T>{
		Queue<IBinaryTreeNode<T>> queue = new LinkedList<>();		
		
		/**
		 * Creates an Iterator of {@link #tree} that iterates in Levelorder-traversal.
		 * @param tree
		 */
		public LevelorderIterator(final IBinarySearchTree<T> tree){
			queue.add(tree.getRootNode());
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			final IBinaryTreeNode<T> node = queue.poll();
			if(node.getLeftChild() != null) {
				queue.add(node.getLeftChild());
			}
			if(node.getRightChild() != null) {
				queue.add(node.getRightChild());
			}
			
			return node.getValue();
		}
		
	}

}
