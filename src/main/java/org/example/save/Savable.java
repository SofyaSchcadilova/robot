package org.example.save;

/**
 * интерфейс, отвечающий за способность окна сохранять и восстанавливать состаяние
 */
public interface Savable {
    /**
     * получение префикса объекта
     * @return prefix
     */
    String getPrefix();
}
