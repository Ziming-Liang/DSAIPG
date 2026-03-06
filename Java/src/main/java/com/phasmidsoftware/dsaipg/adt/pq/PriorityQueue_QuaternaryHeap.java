package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Comparator;

public class PriorityQueue_QuaternaryHeap<K> implements PriorityQueue<K> {

    private Comparator<K> comparator;
    private K[] heap;
    private int size;
    private boolean floyd;

    public PriorityQueue_QuaternaryHeap(int n, boolean max, Comparator<K> comparator, boolean floyd) {
        this.comparator = comparator;
        this.floyd = floyd;
        this.heap = (K[]) new Object[n + 1];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void give(K key) {
        if (size == heap.length - 1) size--;
        heap[++size] = key;
        swimUp(size);
    }

    public K take() throws PQException {
        if (isEmpty()) throw new PQException("Empty");
        K result = heap[1];
        heap[1] = heap[size--];
        if (floyd) snake(1); else sink(1);
        return result;
    }

    void sink(int k) {
        while (k * 4 - 2 <= size) {
            int child = k * 4 - 2;
            int best = child;
            for (int i = child + 1; i <= Math.min(child + 3, size); i++) {
                if (comparator.compare(heap[i], heap[best]) < 0) best = i;
            }
            if (comparator.compare(heap[k], heap[best]) <= 0) break;
            swap(k, best);
            k = best;
        }
    }

    void snake(int k) {
        while (k * 4 - 2 <= size) {
            int child = k * 4 - 2;
            int best = child;
            for (int i = child + 1; i <= Math.min(child + 3, size); i++) {
                if (comparator.compare(heap[i], heap[best]) < 0) best = i;
            }
            swap(k, best);
            k = best;
        }
        swimUp(k);
    }

    void swimUp(int k) {
        while (k > 1) {
            int p = (k + 2) / 4;
            if (comparator.compare(heap[k], heap[p]) >= 0) break;
            swap(k, p);
            k = p;
        }
    }

    void swap(int i, int j) {
        K temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void heapConstructor() {}

    public K peek(int k) {
        return heap[k];
    }

    public boolean getMax() {
        return false;
    }
}