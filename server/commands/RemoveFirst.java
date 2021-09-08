package server.commands;


import server.IOForClient;
import sharedClasses.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;

/**
 * Класс для команды remove_head, которая удаляет первый элемент из коллекции.
 */

public class RemoveFirst extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveFirst() {
        super("remove_first", "удалить первый элемент коллекции", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста.");
        else {
            priorityQueue.removeFromQueue();
            result.append("Удаление элемента успешно завершено.");
        }
        return Serialization.serializeData(result.toString());
    }
}
