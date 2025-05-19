package com.example.BakeryX.queue;

public class SimpleQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public SimpleQueue() {
        front = rear = null;
        size = 0;
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public T[] toArray(T[] a) {
        int index = 0;
        Node<T> current = front;
        while (current != null && index < a.length) {
            a[index++] = current.data;
            current = current.next;
        }
        return a;
    }
}
