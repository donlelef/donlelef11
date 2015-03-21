package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import boards.Task;
import boards.TasksManager;

public class TasksManagerTest {

	TasksManager manager = new TasksManager();

	@Test
	public void newTaskTest() {
		manager.addTask("Timeline", "Componente che deve...");
		assertEquals(1, manager.getTasksNumber());
	}

	@Test
	public void taskCreatedIsTODO() {
		manager.addTask("Timeline", "Componente che deve...");
		assertEquals("TODO", manager.getTask("Timeline").getState());
	}
	
	@Test
	public void changeTaskStateTest() throws Exception {
		manager.addTask("Timeline");
		manager.moveTaskToState("Timeline", "ACCEPTED");
		assertEquals("ACCEPTED", manager.getTask("Timeline").getState());
	}


	@Test
	public void dropTaskTest() {
		manager.addTask("Timeline", "Componente che deve...");
		manager.deleteTask("Timeline");
		assertEquals(null, manager.getTask("Timeline"));
	}

	@Test
	public void displayTasksTest() {
		manager.addTask("Timeline", "Componente che deve...");
		assertEquals("Timeline" + "Componente che deve..." + "TODO", manager
				.getTask("Timeline").toString()
				+ manager.getTask("Timeline").getDescription()
				+ manager.getTask("Timeline").getState());
	}
	
	@Test
	public void findTasksByState() {
		ArrayList<String> tasks = new ArrayList<String>();
		manager.addTask("Timeline");
		manager.addTask("Board");
		manager.addTask("Chat");
		manager.moveTaskToState("Timeline", "IN PROGRESS");
		tasks.add("Timeline");
		ArrayList<String> inProgressTasks = new ArrayList<String>();
		for (Task task : manager.getTasks("IN PROGRESS")) {
			inProgressTasks.add(task.toString());
		}
		assertEquals(tasks, inProgressTasks);
	}
}
