package org.example.command;

import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HelpCommand extends BaseCommand {

    public HelpCommand(SolutionService solutionService,
                       PreparationService preparationService,
                       ComponentService componentService,
                       Scanner scanner,
                       DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        System.out.println("""
            Доступные команды:
            sol_create                     - создать раствор
            sol_list [--q ТЕКСТ]           - список растворов
            sol_show <id>                   - информация о растворе
            prep_add <id_раствора>          - добавить приготовление
            comp_add <id_приготовления>      - добавить компонент
            prep_list <id_раствора> [--last N] - список приготовлений
            prep_show <id_приготовления>     - информация о приготовлении
            comp_list <id_приготовления>     - список компонентов
            prep_update <id> field=value ... - обновить приготовление
            prep_delete <id>                 - удалить приготовление
            help                           - справка
            exit                           - выход
            """);
    }
}