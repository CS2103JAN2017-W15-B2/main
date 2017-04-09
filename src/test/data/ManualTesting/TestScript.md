# Werkbook Test Script

1. Launching Werkbook
2. Loading sample data
3. Command suggestions
4. Adding tasks
5. Selecting tasks
6. Listing tasks
7. Editing tasks
8. Finding tasks
9. Marking tasks
10. Deleting tasks
11. Clearing all tasks
12. Undoing and redoing
13. Logging into Google
14. Exporting to Google tasks
15. Importing from Google tasks
16. Logging out of Google
17. Changing the save location
18. Showing the help page
19. Exiting the program

## 1. Launching Werkbook
#### Actions
1. Execute `werkbook.jar`

#### Results
App launches with an initial tasks containing guides on how to start with the app.

## 2. Loading sample data
#### Actions
1. Close the app if its running
2. Create a new folder `data` in the same folder as `werkbook.jar` if it does not exist
3. Rename `SampleData.xml` to `tasklist.xml` and move it to the `data` folder, replacing any existing `tasklist.xml`
4. Execute `werkbook.jar`

#### Results
App launches, listing 50+ sample tasks.

## 3. Command suggestions
### Showing command suggestions
#### Actions
1. Type `a` in the command box.

#### Results
A list of commands containing `a` shows in a list below the command box.

### Selecting a command suggestion
#### Actions
2. Press <kbd>Down</kbd> on the keyboard

#### Results
`add` will be selected, autofilling the command box. Repeating pressing <kbd>Up</kbd> or <kbd>Down</kbd> will select different commands in the list and autofill the command box accordingly.

## 4. Adding tasks
### Adding a floating task without description
#### Actions
1. Enter `add Contact the client`

#### Results
A new task with the title `Contact the client` will be added to the task list and is selected (expanded). It should appear near the bottom of the task list with the other floating tasks in alphabetical order.

### Adding a floating task with description
#### Actions
1. Enter `add Call event organizer (Michael: 81234567)`

#### Results
A new task with the title `Call event organizer` will be added. In the expanded view the description `Michael: 81234567` can be seen

### Adding a deadlined task
#### Actions
1. Enter `add Complete progress report by Friday`

#### Results
A new task with the title `Complete progress report` will be added. In the expanded view the deadline entered can be seen (e.g. `By: 29/04/2017 1245`). In the unexpanded view (select another task first) the deadline can be seen in a more natural format (e.g. `By: 6 days from now`).

Note: For dates entered without a time specified, the default time will be the current time.

### Adding an event
#### Actions
1. Enter `add Attend Google I/O from 17 May 9am to 20 May 9pm`

#### Results
A new task with the title `Attend Google I/O` will be added. In the expanded view the dates entered can be seen (e.g. `From: 17/05/2017 0900` and `To: 20/05/2017 2100`). In the unexpanded view (select another task first) the dates can be seen in a more natural format (e.g. `From: 1 month from now` and `To:  month from now`).

### Adding a task with only a start time
#### Actions
1. Enter `add Attend a Microsoft conference from next Monday 9am`

#### Results
Result box will show `End Date/Time must be specified if Start Date/Time is specified`, since tasks with only start dates are not supported.

## 5. Selecting tasks
#### Actions
1. Enter `select 3`

#### Results
The task list will scroll to the task at index 3 and expand it. In the expanded view more detailed dates and description of the task can be seen.

## 6. Listing tasks
### Listing incomplete/complete tasks
#### Actions
1. Enter `list incomplete`

#### Results
The task list will show only incomplete tasks. Similarly typing `list complete` will show only completed tasks.

### Listing all tasks
#### Actions
1. Enter `list`

#### Results
The task list will show all tasks, showing the most recent tasks on top and floating tasks at the bottom.

## 7. Editing tasks
### Edit name only
#### Actions
1. Enter `edit 1 Prepare resume for job application`

#### Results
The first task will have the name modified to `Prepare resume for job application`. It will automatically be selected, showing that other fields remain the same.

### Edit description only
#### Actions
1. Enter `edit 1 (Tailor it for Microsoft)`

#### Results
The description of the first task will now be `Tailor it for Microsoft`. It will automatically be selected, showing that other fields remain the same.

### Edit time only
#### Actions
1. Enter `edit 1 next week`

#### Results
The first task will have the day and time modified to that of next week's. It will automatically be selected, showing that other fields remain the same.

### Edit multiple fields
#### Actions
1. Enter `edit 1 Submit resume (urgent) by tomorrow`

#### Results
The first task will now have the name `Submit resume` with a description of `urgent` and a due date of tomorrow (relative to when you perform this action).

### Removing multiple fields
#### Actions
1. Enter `edit 1 Submit resume in () by`

#### Results
The first task will now have the name `Submit resume` with no description and no due date, becoming a floating task which will be sent near the bottom.

### Removing task title
#### Actions
1. Enter `edit 1`

#### Results
An error message will show up: `At least one field to edit must be provided`. Task title is mandatory and must be provided, it cannot be blank.

## 8. Finding tasks
#### Actions
1. Enter `find client`

#### Results
The task list will be filtered to only display tasks with `client` in its name. Enter `list` to show all tasks again.

## 9. Marking tasks
### Marking a task from the default list
#### Actions
1. Enter `mark 1`

#### Results
The first task will reverse the state of the first task, from Complete to Incomplete or Incomplete to Complete. Completion is denoted by a strikethrough in the task title.

### Marking a task from a filtered list
#### Actions
1. Enter `list incomplete`
2. Enter `mark 1`

#### Results
Task will disappear from the list of incomplete task. The task after it will be selected and its details expanded. Similar result when done with `list complete` command.

## 10. Deleting tasks
#### Actions
1. Enter `delete 1`

#### Results
The first task will be deleted

## 11. Clearing all tasks
#### Actions
1. Enter `clear`

#### Results
All tasks will be deleted (i.e. cleared).

## 12. Undoing and redoing
### Undo
#### Actions
1. Enter `undo`
2. Enter `undo` again

#### Results
The previous commands will be undone. If you are following this script, the first `undo` will cause the previous `clear` command will be undone, listing all the tasks that were previously in the task list. The second `undo` will undo the deletion of the one task.

### Redo
#### Actions
1. Enter `redo`
2. Enter `redo` again

#### Results
Redo the previous `undo` commands. If you are following this script, the first `redo` will result in the one task being deleted, and the second `redo` will clear the task list. Before continuing, please enter `undo` so there are tasks in the task list.

### Redo
#### Actions
1. Enter `redo`

#### Results
If you are following this script, there is nothing more to redo and the result box will show `No undos have been performed`.

### Undo upon starting the app
#### Actions
1. Close the app
2. Execute `werkbook.jar`
3. Enter `undo`

#### Results
Result box will show `No previous action has been performed` since there were no actions performed before undo.

## 13. Logging into Google
#### Actions
1. Enter `glogin`
2. A browser window should open asking for authorization to manage your tasks. Click Allow.

#### Results
You will be logged in to Google. You will now be able to perform the next few commands.

## 14. Exporting to Google tasks
#### Actions
1. Enter `gexport` (This command takes a while and will make the app look like it crashed, give it a minute)
2. Navigate to `https://mail.google.com/tasks/canvas` in your browser

#### Results
You should be able to see a new task list labeled `Werkbook` in Google tasks. Clicking on it you should be able to see all the tasks that were previously in the app. Note that this command clears all the tasks in the list labeled `Werkbook` if it exist before adding the tasks from the app in.

## 15. Importing from Google tasks
#### Actions
1. In Google Tasks, create a new task called `Prepare the design documents`
2. Go back to the app and enter `gimport`
3. Enter `find design documents`

#### Results
You should be able to find the task you added from Google Task imported into the app. Note that importing tasks will clear your current task list and replace it with the list on Google Tasks.

## 16. Logging out of Google
#### Actions
1. Enter `glogout`

#### Results
This logs you out of Google, preventing you from using `gimport`, `gexport` and this command.

## 17. Changing the save location
#### Actions
1. Create a new folder `Dropbox` in the same folder as `werkbook.jar`
2. Enter `save Dropbox`
3. Navigate to the `Dropbox` folder

#### Results
This changes the save location to the `Dropbox` folder, `tasklist.xml` should exist there. Note that the folder must be created before entering it into the command.

## 18. Showing the help page
#### Actions
1. Enter `help`

#### Results
This opens up the help window, which shows the user guide page from github. Note that this does not work offline.

## 19. Exiting the program
#### Actions
1. Enter `exit`

#### Results
The app window will close.
