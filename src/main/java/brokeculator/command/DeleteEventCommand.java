package brokeculator.command;

import brokeculator.dashboard.Dashboard;
import brokeculator.frontend.UI;

public class DeleteEventCommand extends Command {
    private final int idx;

    public DeleteEventCommand(int idx) {
        this.idx = idx;
    }

    @Override
    public void execute(Dashboard dashboard) {
        boolean isValidEventIdx = dashboard.getEventManager().isEventIdxValid(idx);
        if (!isValidEventIdx) {
            UI.prettyPrint("Invalid event index");
            return;
        }
        dashboard.getEventManager().removeEvent(idx);
        UI.prettyPrint("Event deleted");
    }
}