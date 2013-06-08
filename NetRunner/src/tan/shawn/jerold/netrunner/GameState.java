package tan.shawn.jerold.netrunner;

import java.io.Serializable;
import java.util.ArrayList;

import cards.Card;

public class GameState implements Serializable
{
	/**
	 * Needed for implmenting Serializable
	 */
	private static final long serialVersionUID = 1L;

	public GameState()
	{
		
	}
	
	public enum PlayerSide
	{
		Corporation, Runner;
	}
	PlayerSide _playerSide; // Which side the player is on.
	PlayerSide _playerTurn; // Which side's turn it is now.
	
	public class SideState
	{
		int _credits = 0;
		int _clicksLeft = 0;
		public ArrayList<Card> _scoredAgendas = new ArrayList<Card>(); 
		public Boolean _AgendaScoredThisRound = false;
		
		public void AddCredits(int credits)
		{
			_credits += credits;
		}
		public boolean PayCredits(int credits)
		{
			if(_credits >= credits)
			{
				// Sufficient credits to pay
				_credits -= credits;
				return true;
			}
			else
			{
				// Insufficient credits to pay
				return false;
			}
		}
		
		public int GetCurrentScore()
		{
			int score = 0;
			for (Card agenda : _scoredAgendas) 
			{
				//TODO: Add the score for each agenda
				score += 1;
			}
			return score;
		}
	}
	
	public class CorpState extends SideState
	{	
		public int _page;
		
		public ArrayList<Card> _discards;
		public int _numDiscardsRevealed;
		public int _numDiscardsNotRevealed;
		
		public ArrayList<Card> _deck;
		
		public ArrayList<Card> _hand;
		public int _maxHandSize;
		
		public class Server
		{
			public ArrayList<Card> _ice = new ArrayList<Card>();
			public ArrayList<Card> _installs = new ArrayList<Card>();	
		}
		public ArrayList<Server> _server = new ArrayList<GameState.CorpState.Server>();
		
		public CorpState()
		{
			_server.add(new Server()); // Archives
			_server.add(new Server()); // R&D
			_server.add(new Server()); // HQ
			_server.add(new Server()); // 1st Remote
		}
		
	    public int GetMaxIce(int page)
	    {
	    	int maxIce = 0;
	    	
			for(int i=0; i<3; i++)
			{
				if((page*3 + i) < _server.size()) // if server exists
				{
					maxIce = Math.max(maxIce, _server.get(page*3 + i)._ice.size());
				}
			}
			
	        return maxIce;
	    }
	    
	    public int GetMaxInstalls(int page)
	    {
	    	int maxInstalls = 0;
	    	
	    	for(int i=0; i<3; i++)
			{
				if((page*3 + i) < _server.size()) // if server exists
				{
					maxInstalls = Math.max(maxInstalls, _server.get(page*3 + i)._installs.size());
				}
			}

			return maxInstalls;
	    }
		
	}
	public CorpState _corpState = new CorpState();
	
	public class RunnerState extends SideState
	{
		public int _tags = 0;
		public void AddTags(int tags)
		{
			_tags += tags;
		}
		public void RemoveTags(int tags)
		{
			if(tags > _tags)
			{
				// Removing more tags than present
				_tags = 0;
			}
			else
			{
				_tags -= tags;
			}
		}
		
		public int _maxHandSize;
		
		public void TakeMeatDamage(int damage)
		{
			//TODO: Meat Damage Implementation
			// Check meat damage reduction
			// Randomly pick card from grip
			// Trash card
			// Add card to archive
		}
		
	}
	public RunnerState _runnerState = new RunnerState();
	
	public void reset()
	{
		for(int i=0; i<_corpState._server.size(); i++)
		{
			_corpState._server.get(i)._ice.clear();
			_corpState._server.get(i)._installs.clear();
		}
	}
}
