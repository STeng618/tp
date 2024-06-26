package brokeculator.parser;

import brokeculator.command.CategoryCommand;
import brokeculator.command.Command;
import brokeculator.command.InvalidCommand;

public class CategoryParser {
    public static final int SUBCOMMAND_INDEX = 1;
    public static final String ADD_SUBCOMMAND = "add";
    public static final String DELETE_SUBCOMMAND = "delete";
    public static final String LIST_SUBCOMMAND = "list";

    public static Command parseInput(String userInput) {
        String[] userInputAsArray = userInput.trim().split("\\s+");
        if (userInputAsArray.length < 2) {
            return new InvalidCommand("Please specify a subcommand." + System.lineSeparator()
                    + "Format: category <subcommand: list|add|delete> <string_value_if_add_or_delete_specified>");
        }
        boolean isAddSubcommand = userInputAsArray[SUBCOMMAND_INDEX].equals(ADD_SUBCOMMAND)
                                  && userInputAsArray.length > 2;
        boolean isDeleteSubcommand = userInputAsArray[SUBCOMMAND_INDEX].equals(DELETE_SUBCOMMAND)
                                     && userInputAsArray.length > 2;
        boolean isListSubcommand = userInputAsArray[SUBCOMMAND_INDEX].equals(LIST_SUBCOMMAND)
                                   && userInputAsArray.length == 2;
        if (isAddSubcommand) {
            int addFieldBeginIndex = userInput.indexOf(ADD_SUBCOMMAND) + ADD_SUBCOMMAND.length();
            return new CategoryCommand(ADD_SUBCOMMAND, userInput.substring(addFieldBeginIndex).trim());
        }
        if (isDeleteSubcommand) {
            int deleteFieldBeginIndex = userInput.indexOf(DELETE_SUBCOMMAND) + DELETE_SUBCOMMAND.length();
            return new CategoryCommand(DELETE_SUBCOMMAND, userInput.substring(deleteFieldBeginIndex).trim());
        }
        if (isListSubcommand) {
            return new CategoryCommand(LIST_SUBCOMMAND);
        }
        return new InvalidCommand("Invalid category command" + System.lineSeparator()
                + "Format: category <subcommand: list|add|delete> <string_value_if_add_or_delete_specified>");
    }
}
