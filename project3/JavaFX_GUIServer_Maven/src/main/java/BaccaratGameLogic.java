import java.util.ArrayList;

public class BaccaratGameLogic {

	int Thand1, Thand2;
	String[] winner = {("Player"),("Banker"),("Draw")};
	
	static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {

		if(handTotal(hand1) > handTotal(hand2)) {
			return "Player";
		} else if(handTotal(hand1) < handTotal(hand2)) {
			return "Banker";
		} else {
			return "Draw";
		}
		
	}
	static int handTotal(ArrayList<Card> hand) {
		int sizeofHand = hand.size();
		int count = 0;
		int i;
		for(i = 0; i < sizeofHand; i++) {
			int cardPoints=hand.get(i).value;
			if(cardPoints > 9) {
				cardPoints = 0;
			}
			count = count + cardPoints;
		}
		
		if(count > 9) {
			count = count - 10;
		}
		return count;
	}
	static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {

		int count = handTotal(hand);
		if(count <= 2) {
			return true;
		} else if(playerCard.value != 8 && count == 3 ) {
			return true;
		} else if(playerCard.value > 1 && playerCard.value <=7 && count == 4) {
			return true;
		} else if(playerCard.value > 3 && playerCard.value <=7 && count == 5) {
			return true;
		} else if(playerCard.value > 5 && playerCard.value <=7 && count == 6) {
			return true;
		} else if(count > 6 ) {
			return false;
		}
		
		return false;
		
		
	}
	static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		if(handTotal(hand) <= 5) {
			return true;
		}
		return false;
	}
}
