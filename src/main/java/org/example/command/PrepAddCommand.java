package org.example.command;

import org.example.domain.Preparation;
import org.example.domain.Units;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrepAddCommand extends BaseCommand {

    public PrepAddCommand(SolutionService solutionService,
                          PreparationService preparationService,
                          ComponentService componentService,
                          Scanner scanner,
                          DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Ошибка: укажите ID раствора");
            return;
        }

        long solutionId;
        try {
            solutionId = Long.parseLong(args.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        if (!solutionService.exists(solutionId)) {
            System.out.println("Ошибка: раствор с id " + solutionId + " не найден");
            return;
        }

        System.out.println("Добавление приготовления:");

        System.out.print("Объём/масса итоговая: ");
        String quantityStr = scanner.nextLine().trim();

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: количество должно быть числом");
            return;
        }

        System.out.print("Единицы (mL|g): ");
        String unitStr = scanner.nextLine().trim();
        Units unit = Units.fromString(unitStr);
        if (unit == null) {
            return;
        }

        System.out.print("Комментарий (можно пусто): ");
        String comment = scanner.nextLine().trim();

        try {
            Preparation prep = preparationService.add(solutionId, quantity, unit, comment, "SYSTEM", Instant.now());
            System.out.println("OK preparation_id=" + prep.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}