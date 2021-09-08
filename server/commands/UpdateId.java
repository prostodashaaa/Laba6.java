package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Flat;
import sharedClasses.Serialization;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды update, которая обновляет значение элемента коллекции по его id.
 */

public class UpdateId extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public UpdateId() {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", 1, true);
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
            long id = Long.parseLong(getArgument());
            List<Flat> flats = priorityQueue.getCollection().stream().
                    filter(city -> city.getId() == id).
                    collect(Collectors.toList());
            if (flats.size() > 0) {
                priorityQueue.getCollection().remove(flats.get(0));
                Flat flat = getFlat();
                flat.setId(id);
                priorityQueue.addToCollection(flat);
                result.append("Обновление элемента успешно завершено.");
            } else result.append("Элемент с id ").append(id).append(" не существует.");
        } catch (NumberFormatException e) {
            result.append("Неправильный формат id.");
        }
        return Serialization.serializeData(result.toString());
    }
}