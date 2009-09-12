package internal;

import internal.util.GameOverObserver;
import internal.util.StateManager;
import java.util.List;

/**
 *
 * @author Andrew Blaine
 */
public class Tournament implements GameOverObserver {
	private enum GameState { INIT, MATCH_IN_SESSION, MATCH_OVER, TOURNAMENT_OVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);
	
	private final MatchFactory matchFactory;
	private final MatchupManager matchupManager;
	
	private Match match;

	public Tournament(MatchFactory matchFactory, MatchupManager matchupManager) {
		this.matchFactory = matchFactory;
		this.matchupManager = matchupManager;
		if (!matchupManager.hasMoreMatchups()) {
			// Well, this is no fun...
			stateManager.setCurState(GameState.TOURNAMENT_OVER);
		}
	}
	
	public void update(final long deltaMs) {
		switch(stateManager.getCurState()) {
			case INIT:
				if (stateManager.isStateChange()) {
					Matchup matchup = matchupManager.getNextMatchup();
					match = matchFactory.createMatch(matchup);
					match.registerObserver(this);
					stateManager.setCurState(GameState.MATCH_IN_SESSION);
				}
				break;
			case MATCH_IN_SESSION:
				match.update(deltaMs);
				break;
			case MATCH_OVER:
				if (stateManager.isStateChange()) {
					if (matchupManager.hasMoreMatchups()) {
						stateManager.setCurState(GameState.INIT);
					} else {
						stateManager.setCurState(GameState.TOURNAMENT_OVER);
					}
				}
				break;
			case TOURNAMENT_OVER://TODO: Print results
				System.out.println(Othello.SYSTEM + "Tournement has ended.");
				System.exit(0);
				break;
		}
	}

	@Override
	public void updateGameOver() {
		stateManager.setCurState(GameState.MATCH_OVER);
	}
}
