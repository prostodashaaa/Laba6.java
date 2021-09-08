package server.collectionUtils;

import sharedClasses.Flat;
import sharedClasses.House;
import sharedClasses.Coordinates;
import sharedClasses.Transport;
import sharedClasses.View;

import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс для чтения данных из файла и создания элементов коллекции.
 */

public class Parser implements ParserInterface {
    private final PriorityQueueStorage priorityQueue;

    /**
     * Конструктор.
     *
     * @param priorityQueue объект класса, в котором будет хранится коллекция.
     */
    public Parser(PriorityQueueStorage priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    /**
     * Метод, осуществляющий парсинг файла.
     *
     * @param lines входящий поток данных из файла.
     */
    public void parseFile(InputStreamReader lines) throws ParseException, NumberFormatException {
        Scanner scanner = new Scanner(lines);
        String[] nextLine;
        HashMap<String, Integer> fields = new HashMap<>();
        nextLine = scanner.nextLine().split(",");
        int i = 0;
        for (String str : nextLine) {
            fields.put(str, i);
            i++;
        }
        while (scanner.hasNext()) {
            nextLine = scanner.nextLine().split(",", -1);
            Flat flat = new Flat();
            flat.setId(Long.parseLong(nextLine[fields.get("id")]));
            if (!priorityQueue.getIdSet().add(flat.getId())) throw new NumberFormatException();
            flat.setName(nextLine[fields.get("name")]);
            flat.setCoordinates(new Coordinates(Float.parseFloat(nextLine[fields.get("x")]), Float.parseFloat(nextLine[fields.get("y")])));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            flat.setCreationDate(Instant.ofEpochMilli(formatter.parse(nextLine[fields.get("creationDate")]).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            flat.setArea(Integer.parseInt(nextLine[fields.get("area")]));
            flat.setNumberOfRooms(Long.parseLong(nextLine[fields.get("numberOfRooms")]));
            if (!nextLine[fields.get("height")].equals(""))
                flat.setHeight(Integer.parseInt(nextLine[fields.get("height")]));
            else flat.setHeight(null);
            flat.setView(View.valueOf(nextLine[fields.get("view")]));
            flat.setTransport(Transport.valueOf(nextLine[fields.get("transport")]));
            flat.setHouse(new House(nextLine[fields.get("houseName")],
                    Integer.parseInt(nextLine[fields.get("year")]),
                    Long.parseLong(nextLine[fields.get("numberOfFlatsOnFloor")]),
                    Integer.parseInt(nextLine[fields.get("numberOfLifts")])));
            priorityQueue.checkElement(flat);
            priorityQueue.addToCollection(flat);
        }
    }
}
