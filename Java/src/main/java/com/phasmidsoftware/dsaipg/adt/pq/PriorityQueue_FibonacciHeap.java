package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.*;

public class PriorityQueue_FibonacciHeap<K> implements PriorityQueue<K> {

    private Node<K> min;
    private int size;
    private final Comparator<K> comparator;

    public PriorityQueue_FibonacciHeap(int capacity, boolean max, Comparator<K> comparator) {
        this.min = null;
        this.size = 0;
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return min == null;
    }

    public int size() {
        return size;
    }

    public void give(K key) {
        Node<K> node = new Node<>(key);
        if (min == null) {
            min = node;
            node.next = node;
            node.prev = node;
        } else {
            node.next = min.next;
            node.prev = min;
            min.next.prev = node;
            min.next = node;
            if (comparator.compare(key, min.key) < 0) {
                min = node;
            }
        }
        size++;
    }

    public K take() throws PQException {
        if (isEmpty()) throw new PQException("Empty");

        Node<K> z = min;
        if (z.child != null) {
            Node<K> child = z.child;
            do {
                Node<K> next = child.next;
                child.prev.next = child.next;
                child.next.prev = child.prev;
                child.next = min.next;
                child.prev = min;
                min.next.prev = child;
                min.next = child;
                child.parent = null;
                child = next;
            } while (child != z.child);
        }

        z.prev.next = z.next;
        z.next.prev = z.prev;

        if (z == z.next) {
            min = null;
        } else {
            min = z.next;
            consolidate();
        }
        size--;
        return z.key;
    }

    private void consolidate() {
        int maxDegree = (int) Math.ceil(Math.log(size) / Math.log(2)) + 1;
        List<Node<K>> degreeTable = new ArrayList<>(Collections.nCopies(maxDegree, null));

        List<Node<K>> nodes = new ArrayList<>();
        Node<K> x = min;
        if (x != null) {
            do {
                nodes.add(x);
                x = x.next;
            } while (x != min);
        }

        for (Node<K> w : nodes) {
            Node<K> curr = w;
            int d = curr.degree;
            while (d < degreeTable.size() && degreeTable.get(d) != null) {
                Node<K> other = degreeTable.get(d);
                if (comparator.compare(curr.key, other.key) > 0) {
                    Node<K> temp = curr;
                    curr = other;
                    other = temp;
                }
                link(other, curr);
                degreeTable.set(d, null);
                d++;
            }
            if (d < degreeTable.size()) {
                degreeTable.set(d, curr);
            }
        }

        min = null;
        for (Node<K> node : degreeTable) {
            if (node != null) {
                if (min == null) {
                    min = node;
                    node.next = node;
                    node.prev = node;
                } else {
                    node.next = min.next;
                    node.prev = min;
                    min.next.prev = node;
                    min.next = node;
                    if (comparator.compare(node.key, min.key) < 0) {
                        min = node;
                    }
                }
            }
        }
    }

    private void link(Node<K> child, Node<K> parent) {
        child.prev.next = child.next;
        child.next.prev = child.prev;

        child.parent = parent;
        if (parent.child == null) {
            parent.child = child;
            child.next = child;
            child.prev = child;
        } else {
            child.next = parent.child.next;
            child.prev = parent.child;
            parent.child.next.prev = child;
            parent.child.next = child;
        }
        parent.degree++;
    }

    public void heapConstructor() {}

    public K peek(int k) {
        return min != null ? min.key : null;
    }

    public boolean getMax() {
        return false;
    }

    private static class Node<K> {
        K key;
        Node<K> parent;
        Node<K> child;
        Node<K> next;
        Node<K> prev;
        int degree;

        Node(K key) {
            this.key = key;
            this.degree = 0;
        }
    }
}