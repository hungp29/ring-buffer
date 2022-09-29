package com.hungp.learn.ringbuffer;

import org.junit.Test;

import static org.junit.Assert.*;

public class RingBufferUnitTest {

    @Test
    public void givenRingBuffer_whenAnElementIsEnqueued_thenSizeIsOne() {
        RingBuffer<String> rb = new RingBuffer<>(5);

        assertTrue(rb.offer("One"));
        assertEquals(rb.getSize(), 1);
    }

    @Test
    public void givenRingBuffer_whenAnElementIsDeQueued_thenElementMatchesEnqueuedElement() {
        RingBuffer<String> rb = new RingBuffer<>(5);

        String element = "One";
        rb.offer(element);

        assertEquals(rb.poll(), element);
    }

    @Test
    public void givenRingBuffer_whenAnElementIsEnqueuedAndDequeued_thenBufferSizeIsZero() {
        RingBuffer<String> rb = new RingBuffer<>(5);

        rb.offer("One");
        rb.poll();

        assertEquals(rb.getSize(), 0);
        assertTrue(rb.isEmpty());
    }

    @Test
    public void givenRingBuffer_whenBufferIsFullFilled_thenNoMoreElementCanBeEnqueued() {
        RingBuffer<String> rb = new RingBuffer<>(2);

        rb.offer("One");
        rb.offer("Two");

        assertFalse(rb.offer("Three"));
    }

    @Test
    public void givenRingBuffer_whenBufferIsEmpty_thenReturnNull() {
        RingBuffer<String> rb = new RingBuffer<>(5);

        assertNull(rb.poll());
    }
}
