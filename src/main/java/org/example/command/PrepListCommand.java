package org.example.command;

import org.example.domain.Preparation;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PrepListCommand extends BaseCommand {

    public PrepListCommand(SolutionService solutionService,
                           PreparationService preparationService,
                           ComponentService componentService,
                           Scanner scanner,
                           DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        String[] parts = args.split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) {
            System.out.println("Ошибка: укажите ID раствора");
            return;
        }

        long solutionId;
        try {
            solutionId = Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        int limit = Integer.MAX_VALUE;
        if (parts.length >= 3 && parts[1].equals("--last")) {
            try {
                limit = Integer.parseInt(parts[2]);
                if (limit <= 0) {
                    System.out.println("Ошибка: N должно быть положительным числом");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: N должно быть числом");
                return;
            }
        }

        try {
            List<Preparation> preps;
            if (limit < Integer.MAX_VALUE) {
                preps = preparationService.getLastBySolutionId(solutionId, limit);
            } else {
                preps = preparationService.getBySolutionId(solutionId);
            }

            if (preps.isEmpty()) {
                System.out.println("Приготовления не найдены");
                return;
            }

            System.out.printf("%-3s %-8s %-5s %-16s %s%n", "ID", "FinalQty", "Unit", "Time", "Comment");
            for (Preparation p : preps) {
                System.out.printf("%-3d %-8.2f %-5s %-16s %s%n",
                        p.getId(),
                        p.getFinalQuantity(),
                        p.getFinalUnit(),
                        timeFormatter.format(p.getPreparedAt()),
                        p.getComment().isEmpty() ? "-" : truncate(p.getComment(), 20));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}