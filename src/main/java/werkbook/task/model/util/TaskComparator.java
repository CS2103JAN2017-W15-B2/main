package werkbook.task.model.util;

import java.util.Comparator;
import java.util.Date;

import werkbook.task.model.task.ReadOnlyTask;

//@@author A0139903B
/**
 * A Comparator that compares two tasks based on their date time If a start date
 * time does not exist, it will compare it against the end date time If there
 * are not start date times, it will compare task names lexicographically
 */
public class TaskComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        return compareTasks(firstTask, secondTask);
    }

    /**
     * Compares the details of two tasks
     * Firstly by date time, names, and lastly task type.
     * @param firstTask
     * @param secondTask
     */
    private int compareTasks(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        int result = 0;

        result = compareDateTime(firstTask, secondTask, result);
        result = compareNames(firstTask, secondTask, result);
        result = compareTaskType(firstTask, secondTask, result);

        return result;
    }

    /**
     * Compares the task type of two tasks
     * If the first task has a date time and the second doesn't then it should be ordered in front
     * @param firstTask ReadOnlyTask to be compared to
     * @param secondTask ReadOnlyTask to be compared with
     * @param result result of comparison to be set
     * @return -1 if first task has a date time and second task does not, 1 for the opposite
     */
    private int compareTaskType(ReadOnlyTask firstTask, ReadOnlyTask secondTask, int result) {
        // If first has time and second doesn't
        if (firstTask.getEndDateTime().isPresent() && !secondTask.getEndDateTime().isPresent()) {
            result = -1;
        }

        // If first doesn't have time, and second has time
        if (!firstTask.getEndDateTime().isPresent() && secondTask.getEndDateTime().isPresent()) {
            result = 1;
        }
        return result;
    }

    /**
     * Basic lexicographic comparison of two task names
     * @param firstTask ReadOnlyTask to be compared to
     * @param secondTask ReadOnlyTask to be compared with
     * @param result result of comparison to be set
     * @return -1 if first task name is lexicographically smaller than the second
     */
    private int compareNames(ReadOnlyTask firstTask, ReadOnlyTask secondTask, int result) {
        // If comparing floating tasks
        if (!firstTask.getEndDateTime().isPresent() && !secondTask.getEndDateTime().isPresent()) {
            result = firstTask.getName().toString().compareTo(secondTask.getName().toString());
        }
        return result;
    }

    /**
     * Compares the date time between two task if they exist
     * @param firstTask ReadOnlyTask to be compared to
     * @param secondTask ReadOnlyTask to be compared with
     * @param result result of comparison to be set
     * @return -1 if first date is earlier than second date, 1 if it is later
     */
    private int compareDateTime(ReadOnlyTask firstTask, ReadOnlyTask secondTask, int result) {
        // If both tasks have an end date time
        if (firstTask.getEndDateTime().isPresent() && secondTask.getEndDateTime().isPresent()) {
            // If first task has a start date time, use start date time
            Date firstDate = firstTask.getStartDateTime().isPresent()
                    ? firstTask.getStartDateTime().value.get() : firstTask.getEndDateTime().value.get();
            Date secondDate = secondTask.getStartDateTime().isPresent()
                    ? secondTask.getStartDateTime().value.get() : secondTask.getEndDateTime().value.get();
            result = DateTimeUtil.getDifference(firstDate, secondDate);
        }
        return result;
    }

}
