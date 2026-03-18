package org.example.command;

import org.example.domain.Preparation;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrepShowCommand extends BaseCommand {

    public PrepShowCommand(SolutionService solutionService,
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

        long id;
        try {
            id = Long.parseLong(args.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        try {
            Preparation p = preparationService.getById(id);
            int count = componentService.countByPreparationId(id);

            System.out.printf("""
                Preparation #%d
                solution_id: %d
                finalQty: %.2f %s
                components: %d%n""",
                    p.getId(), p.getSolutionId(),
                    p.getFinalQuantity(), p.getFinalUnit(),
                    count);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}