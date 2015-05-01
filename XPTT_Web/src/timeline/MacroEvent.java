package timeline;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.exceptions.InvalidDateException;
import model.exceptions.NoSuchEventException;
import model.exceptions.UnEditableEventException;
import filtering.Filter;

/**
 * MacroEvent is a particular extension of {@link Event} superclass that can
 * collect itself multiple inner events, using an instance of {@link Timeline}
 * interface. A macro event is meant to represent a large number of situations
 * occuring in a specified time range. Within this range, other minor events can
 * happen, and they are therefore collected in the macro event object itself.
 * Since this class performs any required operation to manage a collection of
 * Event instances, it does implement {@link Timeline} interface to encourage
 * polimorfism
 * 
 * @author simone, lele, incre, andrea
 *
 */
public class MacroEvent extends Event implements Timeline {

	private Timeline timeline;
	private GregorianCalendar fromDate;
	private GregorianCalendar toDate;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param name
	 *            : the name of the event
	 * @param fromDate
	 *            : the start of the time range
	 * @param toDate
	 *            : the end of the time range
	 * @param timeline
	 *            : the concrete instance of {@link Timeline} interface to
	 *            perform collection
	 * @throws InvalidDateException
	 *             : if the time range is considered invalid
	 */
	public MacroEvent(String name, GregorianCalendar fromDate,
			GregorianCalendar toDate, Timeline timeline)
			throws InvalidDateException {
		super(name, toDate, true);
		this.timeline = timeline;
		this.validateFromDate(fromDate);
		this.fromDate = fromDate;
		this.validateToDate(toDate);
		this.toDate = toDate;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#addEvent(timeline.Event)
	 */
	public void addEvent(Event event) throws InvalidDateException {
		validateDate(event.getDate());
		event.setUneditable();
		this.timeline.addEvent(event);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#deleteEvent(int)
	 */
	public void deleteEvent(int eventId) throws NoSuchEventException,
			UnEditableEventException {
		this.timeline.deleteEvent(eventId);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#moveEvent(int, java.util.GregorianCalendar)
	 */
	public void moveEvent(int eventId, GregorianCalendar newDate)
			throws UnEditableEventException, NoSuchEventException,
			InvalidDateException {
		this.timeline.moveEvent(eventId, newDate);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#getEvent(int)
	 */
	public Event getEvent(int eventId) throws NoSuchEventException {
		return this.timeline.getEvent(eventId);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#getEvents(filtering.Filter)
	 */
	public ArrayList<Event> getEvents(Filter<Event> filter) {
		return this.timeline.getEvents(filter);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see timeline.Timeline#getEventsNumber()
	 */
	public int getEventsNumber() {
		return this.timeline.getEventsNumber();
	}

	private void validateDate(GregorianCalendar date)
			throws InvalidDateException {
		if (isValidDate(date)) {
			throw new InvalidDateException(date);
		}
	}

	private boolean isValidDate(GregorianCalendar date) {
		return !(date.after(this.fromDate) && date.before(this.toDate));
	}

	private void validateFromDate(GregorianCalendar fromDate)
			throws InvalidDateException {
		if (fromDate.before(GregorianCalendar.getInstance())) {
			throw new InvalidDateException(fromDate);
		}
	}

	private void validateToDate(GregorianCalendar toDate)
			throws InvalidDateException {
		if (toDate.before(this.fromDate)) {
			throw new InvalidDateException(fromDate);
		}

	}
}
