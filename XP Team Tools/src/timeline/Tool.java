package timeline;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import boards.Task;
import boards.TasksManager;

public class Tool {

	private TasksManager taskManager = new TasksManager();
	private Timeline timeline = new Timeline();

	public void addTask(String taskName, String description) {
		this.taskManager.addTask(taskName, description);
		this.timeline.addEvent(new Event("Created task: " + taskName,
				getCurrentDate()));
	}

	public int getEventsNumber() {
		return this.timeline.getEventsNumber();
	}

	public void deleteTask(String taskName) {
		this.taskManager.deleteTask(taskName);
		timeline.addEvent(new Event("Deleted task: " + taskName,
				getCurrentDate()));
	}

	public Event getEvent(String eventName) {
		return this.timeline.getEvent(eventName);
	}

	public void moveTaskToState(String taskName, String targetState) {
		this.taskManager.moveTaskToState(taskName, targetState);
		this.timeline.addEvent(new Event("Changed state of task " + taskName
				+ ": now it is " + targetState, this.getCurrentDate()));
	}

	public void addParticipantTo(String taskName, String participant) {
		checkTask(taskName);
		taskManager.getTask(taskName).addParticipant(participant);
		timeline.addEvent(new Event("Added "+participant+" to task: "+taskName, getCurrentDate()));
	}

	private void checkTask(String taskName) {
		Task task = taskManager.getTask(taskName);
		if (task == null) {
			taskManager.addTask(taskName);
		}
	}

	private String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
		Calendar cal = Calendar.getInstance();
		String creationDate = format.format(cal.getTime());
		return creationDate;
	}
}
