package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Flat;
import sharedClasses.Serialization;

/**
 * Класс для команды add_if_max, которая добавляет новый элемент в коллекцию, если его значение превышает значение
 * наибольшего элемента этой коллекции.
 */

public class AddIfMax extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AddIfMax() {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", 0, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        Flat flat = this.getFlat();
        flat.setId(priorityQueue.generateId());
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().peek() != null) {
            if (flat.getArea() > priorityQueue.getCollection().peek().getArea()) {
                priorityQueue.addToCollection(flat);
                result.append("В коллекцию добавлен новый элемент.");
            } else result.append("В коллекцию не добавлен элемент.");
        } else {
            priorityQueue.addToCollection(flat);
            result.append("В коллекцию добавлен новый элемент.");
        }
        return Serialization.serializeData(result.toString());
    }
}
