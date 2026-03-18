package org.example.command;

import org.example.domain.Solution;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolListCommand extends BaseCommand {

    public SolListCommand(SolutionService solutionService,
                          PreparationService preparationService,
                          ComponentService componentService,
                          Scanner scanner,
                          DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        String query = null;
        if (args.startsWith("--q")) {
            String[] parts = args.split("\\s+", 2);
            if (parts.length > 1) {
                query = stripQuotes(parts[1]);
            }
        }

        List<Solution> solutions;
        try {
            if (query != null && !query.isEmpty()) {
                solutions = solutionService.searchByName(query);
            } else {
                solutions = new ArrayList<>(solutionService.getAll());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (solutions.isEmpty()) {
            System.out.println("Растворы не найдены");
            return;
        }

        System.out.printf("%-3s %-20s %-8s %-6s %s%n", "ID", "Name", "Conc", "Unit", "Solvent");
        for (Solution s : solutions) {
            System.out.printf("%-3d %-20s %-8.2f %-6s %s%n",
                    s.getId(),
                    truncate(s.getName(), 20),
                    s.getConcentration(),
                    s.getConcentrationUnit(),
                    truncate(s.getSolvent(), 20));
        }
    }
}