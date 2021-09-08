package server.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Flat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * Класс для команды save, которая сохраняет в файл коллекцию.
 */

public class CommandSave extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Поле, использующееся для временного хранения коллекции.
     */
    private transient final PriorityQueue<Flat> dop = new PriorityQueue<>(10, (c1, c2) -> (c2.getArea() - c1.getArea()));

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandSave() {
        super("save", "сохранить коллекцию в файл", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter(priorityQueue.getFilePath());
        printWriter.write("id,name,x,y,creationDate,area,numberOfRooms,height,view,transport,houseName,year,numberOfFlatsOnFloor,numberOfLifts" + "\n");
        while (!priorityQueue.getCollection().isEmpty()) {
            Flat flat = priorityQueue.pollFromQueue();
            printWriter.write(flat.getId() + ",");
            printWriter.write(flat.getName() + ",");
            printWriter.write(flat.getCoordinates().getX() + ",");
            printWriter.write(flat.getCoordinates().getY() + ",");
            printWriter.write(flat.getCreationDate() + ",");
            printWriter.write(flat.getArea() + ",");
            printWriter.write(flat.getHeight() + ",");
            printWriter.write(flat.getNumberOfRooms() + ",");
            if (flat.getView() == null) printWriter.write(",");
            else printWriter.write(flat.getView() + ",");
            if (flat.getTransport() == null) printWriter.write(",");
            else printWriter.write(flat.getTransport() + ",");
            printWriter.write(flat.getHouse().getName() + ",");
            printWriter.write(flat.getHouse().getYear() + ",");
            printWriter.write(flat.getHouse().getNumberOfFlatsOnFloor() + ",");
            printWriter.write(flat.getHouse().getNumberOfLifts() + ",");
            printWriter.write("\n");
            dop.add(flat);
        }
        printWriter.flush();
        while (!dop.isEmpty()) {
            priorityQueue.addToCollection(dop.poll());
        }
        return null;
    }
}
