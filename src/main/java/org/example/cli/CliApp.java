package org.example.cli;

import org.example.command.*;
import org.example.IdGenerator;
import org.example.service.impl.ComponentService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.SolutionService;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliApp {
    private final SolutionService solutionService;
    private final PreparationService preparationService;
    private final ComponentService componentService;
    private final Scanner scanner;
    private boolean running = true;
    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());

    private final Map<String, Command> commands = new HashMap<>();

    public CliApp() {
        IdGenerator idGenerator = new IdGenerator();
        this.solutionService = new SolutionService(idGenerator);
        this.preparationService = new PreparationService(solutionService, idGenerator);
        this.componentService = new ComponentService(preparationService, idGenerator);
        this.scanner = new Scanner(System.in);

        initializeCommands();
    }

    private void initializeCommands() {
        // Все команды получают одни и те же зависимости
        commands.put("help", new HelpCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("exit", new ExitCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("sol_create", new SolCreateCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("sol_list", new SolListCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("sol_show", new SolShowCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("prep_add", new PrepAddCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("comp_add", new CompAddCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("prep_list", new PrepListCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("prep_show", new PrepShowCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("comp_list", new CompListCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("prep_update", new PrepUpdateCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
        commands.put("prep_delete", new PrepDeleteCommand(solutionService, preparationService, componentService, scanner, timeFormatter));
    }

    public void start() {
        System.out.println("Система учета растворов");
        System.out.println("Введите 'help' для списка команд");

        while (running) {
            try {
                System.out.print("\n> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String args = parts.length > 1 ? parts[1] : "";

                Command command = commands.get(commandName);
                if (command != null) {
                    command.execute(args);

                    // Для exit команды
                    if (commandName.equals("exit")) {
                        running = false;
                    }
                } else {
                    System.out.println("Ошибка: неизвестная команда. Введите 'help'");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
