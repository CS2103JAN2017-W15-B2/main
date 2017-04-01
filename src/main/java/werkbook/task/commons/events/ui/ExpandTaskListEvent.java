//@@author A0130183U
package werkbook.task.commons.events.ui;

import werkbook.task.commons.events.BaseEvent;
import werkbook.task.model.task.ReadOnlyTask;

/**
 * Expands each task card for more details about the task
 */
public class ExpandTaskListEvent extends BaseEvent {

	public final ReadOnlyTask newSelection;

    public ExpandTaskListEvent(ReadOnlyTask newSelection) {
    	this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
//@@ author