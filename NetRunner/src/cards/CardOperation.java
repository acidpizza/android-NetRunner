package cards;

import java.util.Collections;
import java.util.List;

import tan.shawn.jerold.netrunner.GameState;
import tan.shawn.jerold.netrunner.R;

public abstract class CardOperation extends Card
{
	private static final long serialVersionUID = 1363014333113070885L;
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
	private static final long serialVersionUID = -9100397657971746872L;

	public CardOperation_AggressiveNegotiation()
	{
		_drawableID = R.drawable.wey_op1;
		_cost = 1;
	}
	
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
			Collections.sort((List<Card>) gameState._corpState._deck);
			
			//TODO: Aggressive Negotiation Implementation
			// Choose one card from Deck
			// Remove card from Deck
			// Add card to Hand
			
			Collections.shuffle((List<?>) gameState._corpState._deck);
			return true;
		}
	}
}

class CardOperation_BeanstalkRoyalties extends CardOperation
{
	private static final long serialVersionUID = -3020426210433672447L;

	public CardOperation_BeanstalkRoyalties()
	{
		_drawableID = R.drawable.wey_op2;
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
	private static final long serialVersionUID = -4553153305937641146L;

	public CardOperation_ScorchedEarth()
	{
		_drawableID = R.drawable.wey_op3;
		_cost = 3;
		_operationType = OperationType.BlackOps;
	}
	
	@Override
	public boolean operation(GameState gameState)
	{
		if(gameState._runnerState._tags > 0 && !gameState._corpState.PayCredits(_cost))
		{
			gameState._runnerState.TakeMeatDamage(4);
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
	private static final long serialVersionUID = 3767917660271578117L;

	public CardOperation_ShipmentFromKaguya()
	{
		_drawableID = R.drawable.wey_op4;
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