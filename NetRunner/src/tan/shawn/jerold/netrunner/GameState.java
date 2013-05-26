package tan.shawn.jerold.netrunner;

import java.util.ArrayList;

import cards.Card;

public class GameState 
{
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
		public class Server
		{
			ArrayList<Card> _ice;
			ArrayList<Card> _installs;	
		}
		
		public class ServerArchive extends Server
		{
			ArrayList<Card> _discards;
			int _numRevealed;
			int _numNotRevealed;
		}
		public ServerArchive _serverArchive = new ServerArchive();
		
		public class ServerResearch extends Server
		{
			ArrayList<Card> _deck;
		}
		public ServerResearch _serverResearch = new ServerResearch();
		
		public class ServerHQ extends Server
		{
			ArrayList<Card> _hand;
			int _maxHandSize;
		}
		public ServerHQ _serverHQ = new ServerHQ();
		
		public class ServerRemote extends Server
		{ 
			Boolean _assetAgendaInstalled;
		}
		public ArrayList<ServerRemote> _serverRemote = new ArrayList<GameState.CorpState.ServerRemote>();
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
		
		public void DealMeatDamage(int damage)
		{
			//TODO: Meat Damage Implementation
			// Check meat damage reduction
			// Randomly pick card from grip
			// Trash card
			// Add card to archive
		}
		
	}
	public RunnerState _runnerState = new RunnerState();
}
