### Ring Buffer

- Ring Buffer (or Circular Buffer) is a bounded circular data structure that is used for buffering data between two or more threads.
- A Ring Buffer is implemented using a fixed-size array that wraps around at the boundaries.
- The first thing we need to know is the capacity – the fixed maximum size of the buffer. Next, we'll use two monotonically increasing sequences:
  + Write Sequence: starting at -1, increments by 1 as we insert an element
  + Read Sequence: starting at 0, increments by 1 as we consume an element
- We can map a sequence to an index in the array by using a mod operation:
```
arrayIndex = sequence % capacity
```
- Insert an element:
```
buffer[++writeSequence % capacity] = element
```
- Consume an element:
```
element = buffer[readSequence++ % capacity]
```

### Empty and Full Buffers

- As we wrap around the array, we will start overwriting the data in the buffer. If the buffer is full, we can choose to either overwrite the oldest data regardless of whether the reader has consumed it or prevent overwriting the data that has not been read.
- **The buffer is full if the size of the buffer is equal to its capacity**, where its size is equal to the number of unread elements:
```
size = (writeSequence - readSequence) + 1
isFull = (size == capacity)
```
- **If the write sequence lags behind the read sequence, the buffer is empty:**
```
isEmpty = writeSequence < readSequence
```
