import java.util.ArrayList;
import java.util.Random;

public class BaccaratDealer {
	
	ArrayList<Card> deck;
	int curPostion;
	
	void generateDeck() {
		deck = new ArrayList<Card>();
		curPostion = 0;
		int i;
		for(i = 1; i < 14; i++) {
			deck.add(new Card("hearts", i)); 
			deck.add(new Card("spades", i)); 
			deck.add(new Card("clubs", i)); 
			deck.add(new Card("diamonds", i));
		}
		 
	}
	
	ArrayList<Card> dealHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(deck.get(curPostion));
		curPostion++;
		hand.add(deck.get(curPostion));
		curPostion++;
		
		return hand;
	}
	Card drawOne() {
		Card curCard = deck.get(curPostion); 
		curPostion++;
		return curCard;
	}
	void shuffleDeck() {
		int totalNumberDeck = deck.size();
		Random rand = new Random();
		int i;
		for(i = 0; i < totalNumberDeck; i++) {
			Card curCard = deck.get(i);
			int randIndex = i + rand.nextInt(totalNumberDeck - i);
			Card randCard = deck.get(randIndex);
			
			deck.set(i, randCard);
			deck.set(randIndex, curCard);
		}
	}
	int deckSize() {
		return deck.size() - curPostion;
	}
}
