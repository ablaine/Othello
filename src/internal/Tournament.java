package internal;

import internal.output.IOutput;
import internal.util.IGameOverObserver;
import internal.util.StateManager;

/**
 *
 * @author Andrew Blaine
 */
public class Tournament implements IGameOverObserver {
	public enum GameState { TOURNAMENT_INIT, MATCH_INIT, MATCH_IN_SESSION, MATCH_OVER, TOURNAMENT_OVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.TOURNAMENT_INIT);

	private final IOutput output;
	
	private final MatchFactory matchFactory;
	private final MatchupManager matchupManager;

	private Match match;

	public Tournament(IOutput output, MatchFactory matchFactory, MatchupManager matchupManager) {
		this.output = output;
		this.matchFactory = matchFactory;
		this.matchupManager = matchupManager;
		if (!matchupManager.hasMoreMatchups()) {
			// Well, this is no fun...
			stateManager.setCurState(GameState.TOURNAMENT_OVER);
		}
	}

	public void update(final long deltaMs) {
		switch(stateManager.getCurState()) {
			case TOURNAMENT_INIT:
				if (stateManager.isStateChange()) {
					output.update(stateManager.getCurState(), matchupManager);
					stateManager.setCurState(GameState.MATCH_INIT);
				}
				break;
			case MATCH_INIT://Prepare a match
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
						stateManager.setCurState(GameState.MATCH_INIT);
					} else {
						stateManager.setCurState(GameState.TOURNAMENT_OVER);
					}
				}
				break;
			case TOURNAMENT_OVER:
				if (stateManager.isStateChange()) {
					output.update(stateManager.getCurState(), matchupManager);
					System.exit(0);
				}
				break;
		}
	}

	@Override
	public void updateGameOver() {
		stateManager.setCurState(GameState.MATCH_OVER);
	}
}
