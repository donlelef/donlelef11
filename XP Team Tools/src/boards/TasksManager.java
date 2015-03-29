package boards;

import java.util.ArrayList;
import java.util.HashMap;

import model.NameAlreadyInUseException;
import filtering.Filter;

public class TasksManager {

	private HashMap<String, Task> tasks = new HashMap<String, Task>();

	public void addTask(String taskName, String description) throws NameAlreadyInUseException {
		checkTaskName(taskName);
		Task task = new Task(taskName, description);
		tasks.put(task.toString(), task);
	}

	public void addTask(String taskName) throws NameAlreadyInUseException {
		this.addTask(taskName, "");		
	}

	public void deleteTask(String taskName) {
		tasks.remove(taskName);
	}

	public Task getTask(String taskName) {
		return tasks.get(taskName);
	}

	public int getTasksNumber() {
		return tasks.size();
	}

	public void moveTaskToState(String taskName, String targetState) {
		this.tasks.get(taskName).moveTaskToState(targetState);
	}
	
	public ArrayList<Task> getTasks(Filter<Task> filter){
		return filter.filter(this.getAllTasks());
	}
	
	private ArrayList<Task> getAllTasks() {
		ArrayList<Task> list = new ArrayList<Task>();
		list.addAll(this.tasks.values());
		return list;
	}
	
	private void checkTaskName(String taskName) throws NameAlreadyInUseException {
		if(tasks.containsKey(taskName)){
			throw new NameAlreadyInUseException(taskName);
		}
	}
	
	/*
	 * The following method is outdated! Filtering functionality has been moved to TeamManager	
	 */
//	public ArrayList<Task> getTasks(String targetState) {
//		ArrayList<Task> list = new ArrayList<Task>();
//		for (Task task : tasks.values()) {
//			if (task.getState().compareTo(targetState)==0) {
//				list.add(task);
//			}
//		}
//		return list;
//	}

}