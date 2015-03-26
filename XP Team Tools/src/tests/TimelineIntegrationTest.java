package tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.InvalidStateException;
import model.TeamManager;
import model.TeamSettings;

import org.junit.Test;

import timeline.Event;

public class TimelineIntegrationTest {

	TeamSettings settings = new TeamSettings();
	TeamManager teamManager = new TeamManager(settings);

	@Test
	public void taskAdditionCreatesEvent() {
		teamManager.addTask("Nuovo task", "Integrare task in timeline");
		assertEquals(2, teamManager.getEventsNumber());
	}

	@Test
	public void taskDeletionCreatesEvent() {
		teamManager.addTask("Timer", "creare un timer che...");
		settings.setPossibleStates("TODO", "IN PROGRESS", "ACCEPTED", "DONE");
		teamManager.addTask("Timeline", "creare una classe che...");
		teamManager.deleteTask("Timer");
		assertEquals(4, teamManager.getEventsNumber());
	}

	@Test
	public void automaticEventsTest() {
		String currentDate = getCurrentDate();
		teamManager.addTask("Timer", "");
		assertEquals("Created task: Timer",
				teamManager.getEvent("Created task: Timer").toString());
		assertEquals(currentDate, teamManager.getEvent("Created task: Timer")
				.getDate());
	}

	@Test
	public void taskModifyTest() throws InvalidStateException {
		settings.setPossibleStates("TODO", "IN PROGRESS", "DONE");
		teamManager.addTask("Timer", "");
		teamManager.moveTaskToState("Timer", "DONE");
		for (Event event : teamManager.getEvents()) {
			System.err.println(event.toString());
			;
		}
		assertEquals(3, teamManager.getEventsNumber());
		assertEquals("Changed state of task Timer: now it is DONE", teamManager
				.getEvent("Changed state of task Timer: now it is DONE")
				.toString());
		assertEquals(
				getCurrentDate(),
				teamManager.getEvent(
						"Changed state of task Timer: now it is DONE")
						.getDate());
	}

	@Test
	public void participantAdditionCreatesEvent() throws Exception {
		assertEquals(1, teamManager.getEventsNumber());
		settings.addTeamMember("usk");
		teamManager.addDeveloperTo("Timeline", "usk");
		assertEquals(getCurrentDate(),
				teamManager.getEvent("Added usk to task: Timeline").getDate());
		assertEquals(2, teamManager.getEventsNumber());
	}

	@Test
	public void participantAdditionFailsWhenInvalidParticipant()
			throws Exception {
		settings.addTeamMember("sumo");
		try {
			teamManager.addDeveloperTo("Timeline", "ziobrando");
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	/*@Test
	public void memberAdditionCreatesEvent() throws Exception {
		assertEquals(1, teamManager.getEventsNumber());
		settings.addTeamMember("usk");
		Event event = teamManager.getEvent("Added member: usk");
		assertEquals(2, teamManager.getEventsNumber());
		assertEquals(getCurrentDate(), event.getDate());
	}*/

	private String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
		Calendar cal = Calendar.getInstance();
		String currentDate = format.format(cal.getTime());
		return currentDate;
	}

	@Test
	public void eventsByTargetMember() throws Exception {
		settings.setPossibleStates("TODO", "IN PROGRESS", "DONE");
		settings.addTeamMember("sumo");
		teamManager.addDeveloperTo("Timer", "sumo");
		teamManager.moveTaskToState("Timer", "DONE");
		teamManager.deleteTask("Timer");
		assertEquals(3, teamManager.getEvents("sumo").size());
	}

}
