# A0140462R-unused
###### \LogicManagerTest.java
``` java
// unused because list command is tested in GUI
   @Test
   public void execute_list_completeTasks() throws Exception {
       // set up expected results
       TestDataHelper helper = new TestDataHelper();
       TaskList expectedTaskList = helper.generateTaskListWithCompleteTasks(2);
       List<Task> addedTasksList = helper.generateCompleteTaskList(2);
       List<? extends ReadOnlyTask> expectedList = expectedTaskList.getTaskList();

       // add 2 more incomplete tasks to expectedTaskList because only the
       // model will contain all the tasks, but only show the completed tasks
       expectedTaskList.addTask(helper.generateTask(3));
       expectedTaskList.addTask(helper.generateTask(4));

       helper.addToModel(model, addedTasksList);
       //add 2 more incomplete tasks
       model.addTask(helper.generateTask(3));
       model.addTask(helper.generateTask(4));

       assertCommandSuccess("list complete", ListCommand.MESSAGE_SHOW_COMPLETE_SUCCESS,
                             expectedTaskList, expectedList);
   }

       Task generateCompleteTask(int seed) throws Exception {
           return new Task(new Name("Task " + seed), new Description("" + Math.abs(seed)),
                   new StartDateTime("10/10/2016 0900"), new EndDateTime("10/10/2016 1000"),
                   new UniqueTagList("Complete"), clock);
       }

       TaskList generateTaskListWithCompleteTasks(int numGenerated) throws Exception {
           TaskList taskList = new TaskList();
           addtoTaskList(taskList, generateCompleteTaskList(numGenerated));
           return taskList;
       }

       List<Task> generateCompleteTaskList(int numGenerated) throws Exception {
           List<Task> tasks = new ArrayList<>();
           for (int i = 1; i <= numGenerated; i++) {
               tasks.add(generateCompleteTask(i));
           }
           return tasks;
       }
```
