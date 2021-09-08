package server.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Flat;
import sharedClasses.Serialization;

import java.util.Comparator;
import java.util.List;

/**
 * Класс для команды filter_greater_than_number_of_rooms, которая выводит элементы коллекции,
 * значение поля numberOfRooms которых больше заданного.
 */

public class FilterGreaterThanNumberOfRooms extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public FilterGreaterThanNumberOfRooms() {
        super("filter_greater_than_number_of_rooms", "вывести элементы, значение поля numberOfRooms которых больше заданного", 1, false);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста.").append('\n');
        else{
            long numberOfRooms = Long.parseLong(getArgument());
            priorityQueue.getCollection().stream().
                    filter(flat -> flat.getNumberOfRooms() > numberOfRooms).
                    forEach(flat -> result.append(flat.toString()).append('\n'));
        }
        return Serialization.serializeData(result.toString());
    }
}
