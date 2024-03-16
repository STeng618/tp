package brokeculator;
import brokeculator.expense.ExpenseManager;
import brokeculator.frontend.UI;
import brokeculator.storage.FileManager;
import brokeculator.command.Command;
import brokeculator.storage.parsing.GeneralInputParser;
public class Logic {
    private GeneralInputParser mainParser;
    private FileManager fileManager;
    private ExpenseManager expenseManager;
    public Logic(GeneralInputParser mainParser, FileManager fileManager, ExpenseManager expenseManager) {
        this.mainParser = mainParser;
        this.fileManager = fileManager;
        this.expenseManager = expenseManager;
    }
    public void run() {
        loadExpensesFromFile();
        while (true) {
            try {
                String userInput = UI.getUserInput();
                Command command = mainParser.getCommandFromUserInput(userInput);
                command.execute();
                saveExpensesToFile();
            } catch (Exception e) {
                UI.print("error occured, sus. " + e.getMessage());
            }
        }
    }
    private void loadExpensesFromFile() {
        boolean hasNoFileErrors = fileManager.openFile();
        if (!hasNoFileErrors) {
            UI.print("continuing without file");
            return;
        }
        while (fileManager.hasNextLine()) {
            String line = fileManager.readNextLine();
            Command loadCommand = mainParser.getCommandFromFileInput(line);
            loadCommand.execute();
        }
        //after obtaining a clean expense list, we save it back to the file to remove any corrupted data
        saveExpensesToFile();
    }
    private void saveExpensesToFile() {
        String expenseListToSave = expenseManager.getExpensesStringRepresentations();
        fileManager.save(expenseListToSave);
    }
}
