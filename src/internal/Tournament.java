package internal;

import internal.util.StateManager;
import java.util.List;

/**
 *
 * @author Andrew Blaine
 */
public class Tournament {
	private enum GameState { INIT, MATCH_IN_SESSION, MATCH_OVER, TOURNAMENT_OVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);
	
	private final MatchFactory matchFactory;
	private final ContestantManager contestantManager;
	
	private Match match;

	public Tournament(MatchFactory matchFactory, ContestantManager contestantManager) {
		this.matchFactory = matchFactory;
		this.contestantManager = contestantManager;
		if (!contestantManager.hasMoreMatchups()) {
			// Well, this is no fun...
			stateManager.setCurState(GameState.TOURNAMENT_OVER);
		}
	}
	
	public void update(final long deltaMs) {
		switch(stateManager.getCurState()) {
			case INIT:
				if (stateManager.isStateChange()) {
					Matchup matchup = contestantManager.getNextMatchup();
					match = matchFactory.createMatch(matchup);
					stateManager.setCurState(GameState.MATCH_IN_SESSION);
				}
				break;
			case MATCH_IN_SESSION:
				if (match.isGameOver()) {
					match.update(deltaMs);//TEMP: One last one.
					stateManager.setCurState(GameState.MATCH_OVER);
					break;
				}
				match.update(deltaMs);
				break;
			case MATCH_OVER:
				if (stateManager.isStateChange()) {
					if (contestantManager.hasMoreMatchups()) {
						stateManager.setCurState(GameState.INIT);
					} else {
						stateManager.setCurState(GameState.TOURNAMENT_OVER);
					}
				}
				break;
			case TOURNAMENT_OVER://TODO: Print results
				System.out.println(Othello.SYSTEM + "WOOOOO, tourny over!");
				System.exit(0);
				break;
		}
	}


}
