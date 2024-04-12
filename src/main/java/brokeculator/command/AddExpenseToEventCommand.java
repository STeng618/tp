package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.errors.ErrorMessages;
import brokeculator.event.Event;
import brokeculator.event.EventExpenseDataIntegrityManager;
import brokeculator.expense.Expense;
import brokeculator.frontend.UI;

public class AddExpenseToEventCommand extends Command {

    private final int expenseIdx;
    private final int eventIdx;

    public AddExpenseToEventCommand(int expenseIdx, int eventIdx) {
        this.expenseIdx = expenseIdx;
        this.eventIdx = eventIdx;
    }
    
    @Override
    public void execute(Dashboard dashboard, UI ui) {
        boolean isValidExpenseIdx = dashboard.getExpenseManager().isExpenseIndexValid(expenseIdx);
        boolean isValidEventIdx = dashboard.getEventManager().isEventIdxValid(eventIdx);
        if (!isValidExpenseIdx || !isValidEventIdx) {
            ui.prettyPrint(ErrorMessages.INVALID_INDEX);
            return;
        }

        Expense expense = dashboard.getExpenseManager().getExpense(expenseIdx) ;       
        Event newOwningEvent = dashboard.getEventManager().getEvent(eventIdx);        

        assert newOwningEvent != null && expense != null : "Event or Expense is null";

        boolean isExpensedOwnedByEvent = newOwningEvent.containsExpense(expense);
        if (isExpensedOwnedByEvent) {
            ui.prettyPrint("Expense already belongs to the event");
            return;
        }

        Event originalOwningEvent = expense.getOwningEvent();
        if (originalOwningEvent != null) {
            ui.prettyPrint("Expense belonged to another event. Moving it to the new event.");
        }
        EventExpenseDataIntegrityManager.buildConnection(expense, newOwningEvent);
        ui.prettyPrint("Expense added to event successfully");
    }
}
