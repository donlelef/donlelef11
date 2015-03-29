package filtering.chechers;

import filtering.Checker;
import boards.UserStory;

public class TargetStateUserStoryChecker implements Checker<UserStory> {

	private String state;
	
	public TargetStateUserStoryChecker(String state) {
		this.state=state;
	}
	
	@Override
	public boolean check(UserStory tobeChecked) {
		if(tobeChecked.getState().compareTo(state)==0){
			return true;
		}
		return false;
	}

	
}