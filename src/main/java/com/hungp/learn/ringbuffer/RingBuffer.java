package com.hungp.learn.ringbuffer;

public class RingBuffer<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private int capacity;
    private T[] data;
    private volatile int readSequence;
    private volatile int writeSequence;

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) {
        this.capacity = capacity < 1 ? DEFAULT_CAPACITY : capacity;
        this.data = (T[]) new Object[this.capacity];
        this.readSequence = 0;
        this.writeSequence = -1;
    }

    public boolean offer(T element) {
        if (!isFull()) {
            data[++writeSequence % capacity] = element;
            return true;
        }
        return false;
    }

    public T poll() {
        if (!isEmpty()) {
            return data[readSequence++ % capacity];
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return writeSequence - readSequence + 1;
    }

    public boolean isEmpty() {
        return writeSequence < readSequence;
    }

    public boolean isFull() {
        return getSize() >= capacity;
    }
}
