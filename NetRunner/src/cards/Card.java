package cards;

import java.util.ArrayList;

public class Card implements Comparable<Card>
{
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
	
	public static void GetDeck(ArrayList<Card> cardList, int number)
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
}


