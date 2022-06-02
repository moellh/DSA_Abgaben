package de.unistuttgart.dsass2022.ex05.p2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PrefixTreeNode implements IPrefixTreeNode{
	
	private final Map<String, IPrefixTreeNode> edges;
	private String prefix;
	
	public PrefixTreeNode() {
		edges = new HashMap<>();
		prefix = "";
	}
	
	public PrefixTreeNode(final String prefix) {
		edges = new HashMap<>();
		this.prefix = prefix;
	}
	
	public PrefixTreeNode(final IPrefixTreeNode node) {
		this.prefix = node.getPrefix();
		edges = new HashMap<>();
		for(final String key : node.getNext()) {
			this.setNext(key, node.getNode(key));
		}
	}

	@Override
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Set<String> getNext() {
		return edges.keySet();
	}

	@Override
	public void setNext(final String string ,final IPrefixTreeNode node) {
		edges.put(string, node);
	}


	@Override
	public IPrefixTreeNode getNode(final String string) {
		return edges.get(string);
	}


	@Override
	public void removeChildren() {
		edges.clear();
	}
	
}