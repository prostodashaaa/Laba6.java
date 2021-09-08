package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Flat;
import sharedClasses.Serialization;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды count_by_number_of_rooms, которая выводит количество элементов,
 * значение поля numberOfRooms которых равно заданному
 */

public class CountByNumberOfRooms extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CountByNumberOfRooms() {
        super("count_by_number_of_rooms", "вывести количество элементов, значение поля numberOfRooms которых равно заданному", 1, false);
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
        try {
            if (priorityQueue.getCollection().isEmpty()) {
                result.append("Коллекция пуста; количесвто элементов поля numberOfRooms установить невозможно.");
            }
            else {
                long numberOfRooms = Long.parseLong(getArgument());
                List<Flat> flats = priorityQueue.getCollection().stream().
                        filter(flat -> flat.getNumberOfRooms() == numberOfRooms).collect(Collectors.toList());
                int count = flats.size();
                result.append("Количество значений поля numberOfRooms, равных заданному: ").append(count);
            }
        } catch (NumberFormatException e) {
            result.append("Неправильный формат numberOfRooms.");
        }
        return Serialization.serializeData(result.toString());
    }
}