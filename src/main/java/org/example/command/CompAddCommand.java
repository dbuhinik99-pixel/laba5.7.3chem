package org.example.command;

import org.example.domain.PreparationComponent;
import org.example.domain.Units;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CompAddCommand extends BaseCommand {

    public CompAddCommand(SolutionService solutionService,
                          PreparationService preparationService,
                          ComponentService componentService,
                          Scanner scanner,
                          DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Ошибка: укажите ID приготовления");
            return;
        }

        long preparationId;
        try {
            preparationId = Long.parseLong(args.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        if (!preparationService.exists(preparationId)) {
            System.out.println("Ошибка: приготовление с id " + preparationId + " не найдено");
            return;
        }

        System.out.println("Добавление компонента:");

        System.out.print("Batch ID: ");
        String batchIdStr = scanner.nextLine().trim();

        long batchId;
        try {
            batchId = Long.parseLong(batchIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Batch ID должен быть числом");
            return;
        }

        System.out.print("Количество: ");
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

        try {
            PreparationComponent comp = componentService.add(preparationId, batchId, quantity, unit);
            System.out.println("OK component_id=" + comp.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}