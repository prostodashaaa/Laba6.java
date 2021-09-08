package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;

import java.io.Serializable;

/**
 * Класс для команды exit, которая завершает программу без сохранения в файл коллекции.
 */

public class CommandExit extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandExit() {
        super("exit", "завершить программу (без сохранения в файл)", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        String result = "Коллекция сохранена в файл, работа приложения завершается.";
        try {
            commandsControl.getCommands().get("save").doCommand(ioForClient, commandsControl, priorityQueue);
        } catch (Exception e) {
            result = "Возникла ошибка при сохранении коллекции.";
        }
        return Serialization.serializeData(result);
    }
}
