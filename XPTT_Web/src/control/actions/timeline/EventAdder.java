package control.actions.timeline;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeline.Event;

public class EventAdder extends DateHandlerAction {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * control.actions.EventHttpAction#perform(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		addEventToTimeline(request);
		super.redirect(response);
	}

	private void addEventToTimeline(HttpServletRequest request) {
		String eventName = request.getParameter("eventName");
		try {
			if (eventName.trim().equals("")) {
				throw new Exception("You should give a name to your event");
			}
			super.getTimeline(request).addEvent(
					new Event(request.getParameter("eventName"), super
							.generateEventDate(request), true));
		} catch (Exception e) {
			request.getSession().setAttribute("exception", e);
		}
	}
}