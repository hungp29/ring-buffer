package com.hungp.learn.ringbuffer;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

import static org.junit.Assert.assertArrayEquals;

public class ProducerConsumerLiveTest {

    private final String[] numbers = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

    @Test
    public void givenRingBuffer_whenInterleavingProducerConsumer_thenElementsMatch() throws ExecutionException, InterruptedException, TimeoutException {
        RingBuffer<String> rb = new RingBuffer<>(numbers.length);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Producer<>(rb, numbers));
        Future<String[]> consumed = executorService.submit(new Consumer<>(rb, numbers.length));

        Object[] numberConsumed = consumed.get(5L, TimeUnit.SECONDS);
        String[] numberConsumedString = Arrays.stream(numberConsumed).toArray(String[]::new);

        assertArrayEquals(numberConsumedString, numbers);
    }

    static class Producer<T> implements Runnable {
        private RingBuffer<T> rb;
        private T[] elements;

        public Producer(RingBuffer<T> rb, T[] elements) {
            this.rb = rb;
            this.elements = elements;
        }

        @Override
        public void run() {
            for (int i = 0; i < elements.length;) {
                if (rb.offer(elements[i])) {
                    System.out.println("Producer: " + elements[i]);
                    i++;
                    LockSupport.parkNanos(5);
                }
            }
        }
    }

    static class Consumer<T> implements Callable<T[]> {
        private RingBuffer<T> rb;
        private int expectedCount;

        public Consumer(RingBuffer<T> rb, int expectedCount) {
            this.rb = rb;
            this.expectedCount = expectedCount;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T[] call() throws Exception {
            T[] elements = (T[]) new Object[expectedCount];
            for (int i = 0; i < expectedCount;) {
                T element = rb.poll();
                if (element != null) {
                    elements[i++] = element;
                    LockSupport.parkNanos(5);
                    System.out.println("Consumer: " + element);
                }
            }
            return elements;
        }
    }
}
