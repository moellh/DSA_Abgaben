package de.unistuttgart.dsass2022.ex04.p2;


public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	private volatile IAVLNode<T> root;
	
	
	public AVLTree() {
		this.root = new AVLNode<T>();
	
	}

	@Override
	public void insert(T t) {
		
	}	

	@Override
	public IAVLNode<T> getRootNode() {
		return this.root;
	}
	
}
