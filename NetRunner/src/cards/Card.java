package cards;

public class Card implements Comparable<Card>
{
	int drawableID; // graphic assigned to this card
	
	public enum CardType
	{
		ID, 									// Corp or Runner ID
		Operation, Agenda, Ice, Upgrade, Asset, // Corp cards 
		Hardware, Resource, Program, Event; 	// Runner cards
	}
	CardType _cardType;
	
	Boolean _isTapped;
	Boolean _isRevealed;
	
	int _advancement; // initialise to negative if cannot be advanced

	
	// For sorting based on drawable ID
	@Override
	public int compareTo(Card anotherCard)
	{
	     return(drawableID - anotherCard.drawableID);
	}
}


