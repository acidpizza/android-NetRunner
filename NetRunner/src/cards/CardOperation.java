package cards;

import java.util.Collections;
import java.util.List;

import tan.shawn.jerold.netrunner.GameState;

public abstract class CardOperation extends Card
{
	int _cost = 0;
	
	public enum OperationType
	{
		None, Transaction, BlackOps;
	}
	OperationType _operationType = OperationType.None;
	
	public abstract boolean operation(GameState gameState);
	
}

class CardOperation_AggressiveNegotiation extends CardOperation
{
	public CardOperation_AggressiveNegotiation()
	{
		_cost = 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean operation(GameState gameState)
	{
		if(!gameState._corpState._AgendaScoredThisRound && !gameState._corpState.PayCredits(_cost))
		{
			// Agenda was not scored this round
			return false;
		}
		else
		{
			Collections.sort((List<Card>) gameState._corpState._serverResearch);
			
			//TODO: Aggressive Negotiation Implementation
			// Choose one card from _serverResearch
			// Remove card from _serverResearch
			// Add card to _serverHQ
			
			Collections.shuffle((List<?>) gameState._corpState._serverResearch);
			return true;
		}
	}
}

class CardOperation_BeanstalkRoyalties extends CardOperation
{
	public CardOperation_BeanstalkRoyalties()
	{
		_operationType = OperationType.Transaction;
	}
	
	@Override
	public boolean operation(GameState gameState)
	{
		if(!gameState._corpState.PayCredits(_cost))
		{
			// Unable to pay cost
			return false;
		}
		else
		{
			gameState._corpState.AddCredits(3);
			return true;
		}
	}
}

class CardOperation_ScorchedEarth extends CardOperation
{
	public CardOperation_ScorchedEarth()
	{
		_cost = 3;
		_operationType = OperationType.BlackOps;
	}
	
	@Override
	public boolean operation(GameState gameState)
	{
		if(gameState._runnerState._tags > 0 && !gameState._corpState.PayCredits(_cost))
		{
			gameState._runnerState.DealMeatDamage(4);
			return true;
		}
		else
		{	
			// Cannot do operation because runner is not tagged
			return false;
		}
	}
}

class CardOperation_ShipmentFromKaguya extends CardOperation
{
	public CardOperation_ShipmentFromKaguya()
	{
	}
	
	@Override
	public boolean operation(GameState gameState)
	{
		//TODO: Implement Advancement logic
		// Highlight cards that can be advanced
		// Select up to 2 cards
		// Advance 1 for each card
		return true;
	}
}