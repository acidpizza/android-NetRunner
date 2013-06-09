package cards;

import java.io.Serializable;
import java.util.ArrayList;

import tan.shawn.jerold.netrunner.R;

public class Card implements Comparable<Card>, Serializable
{
	private static final long serialVersionUID = -6480854425344930585L;

	public int _drawableID; // graphic assigned to this card
	
	public enum CardType
	{
		ID, 									// Corp or Runner ID
		Operation, Agenda, Ice, Upgrade, Asset, // Corp cards 
		Hardware, Resource, Program, Event; 	// Runner cards
	}
	public CardType _cardType;
	
	public Boolean _isTapped;
	public Boolean _isRevealed;
	
	public int _advancement; // initialise to negative if cannot be advanced
	
	// For sorting based on drawable ID
	@Override
	public int compareTo(Card anotherCard)
	{
	     return(_drawableID - anotherCard._drawableID);
	}
	
	public static void GetCorpDeck(ArrayList<Card> cardList, int number)
	{
		if(number >= 1)
			cardList.add(new CardOperation_AggressiveNegotiation());
		
		if(number >= 2)
			cardList.add(new CardOperation_BeanstalkRoyalties());
		
		if(number >= 3)
			cardList.add(new CardOperation_ScorchedEarth());
		
		if(number >= 4)
			cardList.add(new CardOperation_ShipmentFromKaguya());
	}
	
	public static void GetRunnerDeck(ArrayList<Card> cardList, int number)
	{
		if(number == 1)
		{
			Card tmpCard = new Card();
			tmpCard._drawableID = R.drawable.rneutral_resource1;
			cardList.add(tmpCard);
			
			tmpCard = new Card();
			tmpCard._drawableID = R.drawable.sha_resource1;
			cardList.add(tmpCard);
		}
			
		
		if(number == 2)
		{
			Card tmpCard = new Card();
			tmpCard._drawableID = R.drawable.sha_hardware1;
			cardList.add(tmpCard);
		}
		
		if(number == 3)
		{
			Card tmpCard = new Card();
			tmpCard._drawableID = R.drawable.rneutral_program1;
			cardList.add(tmpCard);
			
			tmpCard = new Card();
			tmpCard._drawableID = R.drawable.sha_program1;
			cardList.add(tmpCard);
			
			tmpCard = new Card();
			tmpCard._drawableID = R.drawable.sha_program2;
			cardList.add(tmpCard);	
		}
	}
}


