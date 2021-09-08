package server.commands;

import java.util.HashMap;

/**
 * Класс для создания объектов комманд и их хранения.
 */

public class CommandsControl {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Поле, использующееся для хранения объектов команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    {
        commands.put("help", new Help());
        commands.put("show", new Show());
        commands.put("info", new Info());
        commands.put("add", new Add());
        commands.put("update", new UpdateId());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new CommandClear());
        commands.put("save", new CommandSave());
        commands.put("execute_script", new ExecuteScript());
        commands.put("exit", new CommandExit());
        commands.put("remove_head", new RemoveHead());
        commands.put("add_if_max", new AddIfMax());
        commands.put("remove_first", new RemoveFirst());
        commands.put("count_by_number_of_rooms", new CountByNumberOfRooms());
        commands.put("count_less_than_house", new CountLessThanHouse());
        commands.put("filter_greater_than_number_of_rooms", new FilterGreaterThanNumberOfRooms());
    }

    /**
     * Метод, возвращающий карту команд.
     *
     * @return карту команд.
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
