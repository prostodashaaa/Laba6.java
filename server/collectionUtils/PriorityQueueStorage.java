package server.collectionUtils;

import sharedClasses.Flat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Класс для хранения коллекции.
 */
public class PriorityQueueStorage implements StorageInterface<Flat> {
    /**
     * Путь к исходному файлу.
     */
    private final String filePath;
    /**
     * Дата создания.
     */
    private final LocalDate creationDate;
    /**
     * Список id.
     */
    private final HashSet<Long> idSet;
    /**
     * Коллекция.
     */
    private final PriorityQueue<Flat> priorityQueue = new PriorityQueue<>(10, (c1, c2) -> c2.getArea() - c1.getArea());

    /**
     * Конструктор.
     *
     * @param args путь к исходному файлу.
     */
    public PriorityQueueStorage(String args) {
        filePath = args;
        idSet = new HashSet<>();
        creationDate = LocalDate.now();
    }

    /**
     * Метод, проверяющий элемент коллекции на допустимые знаечения полей.
     *
     * @param flat проверяемый объект.
     */
    public void checkElement(Flat flat) throws NumberFormatException {
        if (flat.getName().equals("") || flat.getCoordinates() == null ||
                flat.getCoordinates().getY() == null || flat.getHeight() == null || flat.getView() == null || flat.getTransport() == null)
            throw new NullPointerException();
        if (flat.getCoordinates().getX() > 900 || flat.getCoordinates().getY() <= -989 || flat.getArea() <= 0 ||
                flat.getNumberOfRooms() > 8 || flat.getNumberOfRooms() <= 0 || flat.getHeight() <= 0 ||
                flat.getHouse().getYear() <= 0 || flat.getHouse().getYear() > 568 || flat.getHouse().getNumberOfFlatsOnFloor() <=0 ||
                flat.getHouse().getNumberOfLifts() <=0)
            throw new NumberFormatException();
    }

    /**
     * Метод, возвращающий список занятых id.
     *
     * @return список занятых id.
     */
    public HashSet<Long> getIdSet() {
        return idSet;
    }

    /**
     * Метод, генерирующий id.
     *
     * @return сгенерированное id.
     */
    public long generateId() throws IllegalStateException {
        long id = 1;
        long count = 0;
        IllegalStateException e = new IllegalStateException();
        if (idSet.isEmpty()) {
            System.out.println("");
        }
        else if (Collections.max(idSet) == Long.MAX_VALUE) {
            id = 1;
            count += 1;
        } else id = Collections.max(idSet) + 1;
        while (!idSet.add(id)) {
            if (id == Long.MAX_VALUE) {
                id = 1;
                count += 1;
            } else id += 1;
            if (count == 2) throw e;
        }
        return id;
    }

    /**
     * Метод, возвращающий путь к файлу, откуда была считана коллекция.
     *
     * @return путь к файлу, откуда была считана коллекция.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Метод, возвращающий дату создания коллекции.
     *
     * @return дата создания коллекции.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, возвращающий коллекцию.
     *
     * @return коллекция.
     */
    public PriorityQueue<Flat> getCollection() {
        return priorityQueue;
    }

    public void addToCollection(Flat c) {
        priorityQueue.add(c);
    }

    public Flat pollFromQueue() {
        return priorityQueue.poll();
    }

    public Flat removeFromQueue() {
        return priorityQueue.remove();
    }

}
