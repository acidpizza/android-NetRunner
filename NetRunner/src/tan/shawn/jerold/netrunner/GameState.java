package tan.shawn.jerold.netrunner;

import java.io.Serializable;
import java.util.ArrayList;

import cards.Card;

public class GameState implements Serializable
{
	private static final long serialVersionUID = 7386075871278144765L;

	public GameState()
	{
		
	}
	
	public enum PlayerSide
	{
		Corporation, Runner;
	}
	PlayerSide _playerID; // Which side the player is on.
	PlayerSide _curPlayerTurn; // Which side's turn it is now.
	
	public class SideState implements Serializable
	{
		private static final long serialVersionUID = 1612896925770220849L;
		
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
		private static final long serialVersionUID = -2023853242287901867L;

		public int _page;
		
		public ArrayList<Card> _discards;
		public int _numDiscardsRevealed;
		public int _numDiscardsNotRevealed;
		
		public ArrayList<Card> _deck;
		
		public ArrayList<Card> _hand;
		public int _maxHandSize;
		
		public class Server implements Serializable
		{
			private static final long serialVersionUID = -1039936668508851097L;
			
			public ArrayList<Card> _ice = new ArrayList<Card>();
			public ArrayList<Card> _installs = new ArrayList<Card>();	
		}
		public ArrayList<Server> _servers = new ArrayList<GameState.CorpState.Server>();
		
		public CorpState()
		{
			_servers.add(new Server()); // Archives
			_servers.add(new Server()); // R&D
			_servers.add(new Server()); // HQ
			_servers.add(new Server()); // 1st Remote
		}
		
	    public int GetMaxIce(int page)
	    {
	    	int maxIce = 0;
	    	
			for(int i=0; i<3; i++)
			{
				if((page*3 + i) < _servers.size()) // if server exists
				{
					maxIce = Math.max(maxIce, _servers.get(page*3 + i)._ice.size());
				}
			}
			
	        return maxIce;
	    }
	    
	    public int GetMaxInstalls(int page)
	    {
	    	int maxInstalls = 0;
	    	
	    	for(int i=0; i<3; i++)
			{
				if((page*3 + i) < _servers.size()) // if server exists
				{
					maxInstalls = Math.max(maxInstalls, _servers.get(page*3 + i)._installs.size());
				}
			}

			return maxInstalls;
	    }
		
	    public void resetCorp()
		{
	    	int serverSize = _corpState._servers.size();
			for(int i=0; i < serverSize; i++)
			{
				_corpState._servers.get(i)._ice.clear();
				_corpState._servers.get(i)._installs.clear();
			}
		}
	}
	public CorpState _corpState = new CorpState();
	
	public class RunnerState extends SideState
	{
		private static final long serialVersionUID = -3835428212875308119L;
		
		public ArrayList<Card> _rigResources = new ArrayList<Card>();
		public ArrayList<Card> _rigHardware = new ArrayList<Card>();
		public ArrayList<Card> _rigPrograms = new ArrayList<Card>();
		
		public ArrayList<Card> _heap = new ArrayList<Card>(); // Trash
		public ArrayList<Card> _stack = new ArrayList<Card>(); // Draw
		public ArrayList<Card> _grip = new ArrayList<Card>(); // Hand
		
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
		
		
	    public int GetMaxRig()
	    {
	    	int maxRig = 0;
	    	
			maxRig = Math.max(maxRig, _rigResources.size());
			maxRig = Math.max(maxRig, _rigHardware.size());
			maxRig = Math.max(maxRig, _rigPrograms.size());

			return maxRig;
	    }
	    
	    public void resetRunner()
	    {
	    	_rigResources.clear();
	    	_rigHardware.clear();
	    	_rigPrograms.clear();
	    	
	    	_heap.clear();
	    	_stack.clear();
	    	_grip.clear();
	    }
	    
	}
	public RunnerState _runnerState = new RunnerState();
	
	public void reset()
	{
		_corpState.resetCorp();
		_runnerState.resetRunner();
	}
}
