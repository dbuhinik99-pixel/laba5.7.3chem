package org.example.command;

import org.example.domain.Units;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrepUpdateCommand extends BaseCommand {

    public PrepUpdateCommand(SolutionService solutionService,
                             PreparationService preparationService,
                             ComponentService componentService,
                             Scanner scanner,
                             DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        String[] parts = args.split("\\s+", 2);
        if (parts.length < 2) {
            System.out.println("Ошибка: укажите ID и поля для обновления");
            return;
        }

        long id;
        try {
            id = Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        String[] updates = parts[1].split("\\s+");
        Double finalQuantity = null;
        Units finalUnit = null;
        String comment = null;
        boolean hasUpdates = false;

        for (String update : updates) {
            String[] kv = update.split("=", 2);
            if (kv.length != 2) {
                System.out.println("Ошибка: неверный формат. Используйте field=value");
                return;
            }

            String field = kv[0].toLowerCase();
            String value = stripQuotes(kv[1]);

            switch (field) {
                case "finalquantity":
                case "finalQuantity":
                case "quantity":
                    try {
                        finalQuantity = Double.parseDouble(value);
                        hasUpdates = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: finalQuantity должно быть числом");
                        return;
                    }
                    break;
                case "finalunit":
                case "finalUnit":
                case "unit":
                    finalUnit = Units.fromString(value);
                    if (finalUnit == null) {
                        return;
                    }
                    hasUpdates = true;
                    break;
                case "comment":
                    comment = value;
                    hasUpdates = true;
                    break;
                default:
                    System.out.println("Ошибка: неизвестное поле '" + field + "'");
                    return;
            }
        }

        if (!hasUpdates) {
            System.out.println("Ошибка: не указаны поля для обновления");
            return;
        }

        try {
            preparationService.update(id, finalQuantity, finalUnit, comment);
            System.out.println("OK");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}