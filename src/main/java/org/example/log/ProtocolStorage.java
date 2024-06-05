package org.example.log;
import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


/**
 * структура данных для хранения записей
 * @param <T>
 */
public class ProtocolStorage<T> {

    private final ArrayDeque<T> records = new ArrayDeque<>();
    private final int maxSize;
    private final ReentrantLock lock = new ReentrantLock();

    public ProtocolStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * добавление записи в начало, как только количество записей достигнет maxSize
     * происходит удаление всех записей
     * @param record
     */
    public void addRecord(T record) {
        lock.lock();
        try {
            records.push(record);
            if (records.size() > maxSize) {
                records.clear();
                records.push(record);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * удаление записи из начала
     */
    public void removeRecord() {
        lock.lock();
        try {
            if (!records.isEmpty()) {
                records.removeFirst();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * получение сегмента смежных данных
     * @param startIndex
     * @param endIndex
     * @return
     */
    public Iterable<T> getRecords(int startIndex, int endIndex) {
        lock.lock();
        try {
            T[] result = (T[]) new Object[endIndex - startIndex + 1];
            int index = 0;
            for (T record : records) {
                if (index >= startIndex && index <= endIndex) {
                    result[index - startIndex] = record;
                }
                index++;
            }
            return List.of(result);
        } finally {
            lock.unlock();
        }
    }

    /**
     * получение размера данных
     * @return
     */
    public int size() {
        lock.lock();
        try {
            return records.size();
        } finally {
            lock.unlock();
        }
    }
}
