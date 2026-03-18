package org.example.command;

import org.example.domain.PreparationComponent;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CompListCommand extends BaseCommand {

    public CompListCommand(SolutionService solutionService,
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

        try {
            List<PreparationComponent> comps = componentService.getByPreparationId(preparationId);

            if (comps.isEmpty()) {
                System.out.println("Компоненты не найдены");
                return;
            }

            System.out.printf("%-3s %-6s %-8s %s%n", "ID", "Batch", "Qty", "Unit");
            for (PreparationComponent c : comps) {
                System.out.printf("%-3d %-6d %-8.2f %s%n",
                        c.getId(), c.getBatchId(), c.getQuantity(), c.getUnit());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}