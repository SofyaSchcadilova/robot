package org.example.log;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ограниченная очередь
 * - старые записи вытесняются
 *  * - потокобезопасная
 *  * - есть возможность доступа к части данных
 * @param <T>
 */
public class BoundedLog<T> {
    private final int maxSize;
    private final T[] log;
    private int head;
    private int tail;
    private final ReentrantLock lock = new ReentrantLock();

    public BoundedLog(int maxSize) {
        this.maxSize = maxSize;
        this.log = (T[]) new Object[maxSize];
        this.head = 0;
        this.tail = 0;
    }

    public void add(T entry) {
        lock.lock();
        try {
            log[tail] = entry;
            tail = (tail + 1) % maxSize;
            if (tail == head) {
                head = (head + 1) % maxSize;
            }
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return (tail - head + maxSize) % maxSize;
        } finally {
            lock.unlock();
        }
    }

    public Iterable<T> getSegment(int startIndex, int endIndex) {
        lock.lock();
        try {
            int segmentLength = endIndex - startIndex + 1;
            LinkedList<T> segment = new LinkedList<>();
            for (int i = 0; i < segmentLength; i++) {
                segment.add(log[(head + startIndex + i) % maxSize]);
            }
            return segment;
        } finally {
            lock.unlock();
        }
    }
}
