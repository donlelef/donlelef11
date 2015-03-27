package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import timeline.Event;
import timeline.Timeline;

public class TimelineTest {

	Timeline timeline = new Timeline();

	@Test
	public void timelineCreationTest() {
		assertEquals(1, timeline.getEventsNumber());
	}

	@Test
	public void addEventTest() {
		timeline.addEvent(new Event("Briefing", new GregorianCalendar(2015, 02, 20, 23, 3, 50)));
		assertEquals(2, timeline.getEventsNumber());
	}

	@Test
	public void dropEventTest() {
		timeline.addEvent(new Event("Briefing", new GregorianCalendar(2015, 02, 20, 23, 3, 50)));
		timeline.dropEvent("Briefing");
		assertEquals(1, timeline.getEventsNumber());
	}

	@Test
	public void dateDisplayTest() {
		timeline.addEvent(new Event("Briefing", new GregorianCalendar(2015, 02, 20, 23, 3, 50)));
		assertEquals(new GregorianCalendar(2015, 02, 20, 23, 3, 50), timeline.getEvent("Briefing").getDate());
	}

//	@Test
	/*
	 * May fail because of computing dalays
	 */
//	public void creationEventTest() {
//		Calendar cal = (GregorianCalendar) Calendar.getInstance();
//		assertEquals(cal, timeline.getEvent("creation").getDate());
//	}

	@Test
	public void timeChangeTest() {
		timeline.addEvent(new Event("Riunione sulla timeline", new GregorianCalendar(2015, 02, 20, 23, 3, 50)));
		timeline.moveEvent("Riunione sulla timeline", new GregorianCalendar(2015, 02, 20, 23, 3, 50));
		assertEquals(new GregorianCalendar(2015, 02, 20, 23, 3, 50),
				timeline.getEvent("Riunione sulla timeline").getDate());
	}

	@Test
	public void participantAdditionTest() throws Exception {
		timeline.addEvent(new Event("Briefing", new GregorianCalendar(2015, 02, 20, 23, 3, 50)));
		timeline.getEvent("Briefing").addParticipant("Simone Colucci");
		assertTrue(timeline.getEvent("Briefing").getParticipants().contains(
				"Simone Colucci"));
	}
}