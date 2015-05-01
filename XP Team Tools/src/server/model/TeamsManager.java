package server.model;

import java.util.LinkedList;
import java.util.List;

import client.model.Team;
/**
 * A singleton team-manager.
 * It manage the teams ensuring the uniqueness
 * @see Team
 * @author alberto
 *
 */
public class TeamsManager {
	
	private List<Team> teamList = new LinkedList<Team>();

	private static TeamsManager instance = new TeamsManager();

	private TeamsManager() {
	}

	public void add(Team team) {
		if (!has(team.getName())) {
			teamList.add(team);
		}
	}
	/**
	 * Returns true if the {@link Team} registered
	 * has the same team name passed as parameter
	 * @param teamName
	 * @return
	 */
	public boolean has(String teamName) {
		for (Team team : teamList) {
			if(team.getName().equals(teamName)) {
				return true;
			}
		}
		return false;
	}

	public Team get(int index) {
		return teamList.get(index);
	}

	/**
	 * Used as key
	 * 
	 * @param chat
	 * @return
	 */
	public int indexOf(String teamName) {
		int size = teamList.size();
		for (int i = 0; i < size; i++) {
			if(teamList.get(i).getName().equals(teamName)) {
				return i;
			}
		}
		return -1;
	}

	public void remove(String teamName) {
		if (has(teamName)) {
			teamList.remove(indexOf(teamName));
		}
	}
	
	public void addTeamMemb(String teamName, String teamMemb) {
		teamList.get(indexOf(teamName)).addMember(teamMemb);
	}

	public int size() {
		return teamList.size();
	}
	
	public static TeamsManager getInstance() {
		return instance;
	}

}
