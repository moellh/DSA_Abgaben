package de.unistuttgart.dsass2022.ex05.p2;

import java.util.Collection;
import java.util.Optional;

public class PrefixTree implements IPrefixTree {
	private final IPrefixTreeNode root;
	private int size; 
	
	public PrefixTree() {
		root = new PrefixTreeNode();
		size = 0;
	}

	public IPrefixTreeNode getRoot() {
		return root;
	}

	@Override
	public void insert(final String word) {
		if(word == null) {
			throw new IllegalArgumentException("word is null");
		}
		
		if(size == 0) {
			root.setPrefix(word);
			size++;
		} else {
			insertWord(word, root);
		}
	}

	
	
	private void insertWord(final String insertedWord, final IPrefixTreeNode subtreeRoot) {
		if(subtreeRoot.getPrefix().equals(insertedWord)) {
			return;
		}
		
		if(insertedWord.startsWith(subtreeRoot.getPrefix())) {
			final String firstCharacterOfInsertedWord = insertedWord.substring(0, 1);
			if(subtreeRoot.getNext().contains(firstCharacterOfInsertedWord)) {
				insertWord(insertedWord.substring(1), subtreeRoot.getNode(firstCharacterOfInsertedWord));
			}else {
				final prefixTreeNode
				subtreeRoot.setNext(firstCharacterOfInsertedWord, subtreeRoot);
			}
		}else {
			
		}
	}
	
	

	@Override
	public int size() {
		return size;
	}


}
