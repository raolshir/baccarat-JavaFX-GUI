import java.util.ArrayList;

public class BaccaratGame {
	ArrayList<Card> playerHand;
	ArrayList<Card> BankerHand;
	BaccaratDealer theDealer;
	double currentBet;
	double totalWinnings;
	
	public void getBet(double input) {
		currentBet = input;
	}
	public void startGame() {
		theDealer = new BaccaratDealer();
		theDealer.generateDeck();
		theDealer.shuffleDeck();
		
		BankerHand = theDealer.dealHand();
		playerHand = theDealer.dealHand();
		
		int countTotalBanker = BaccaratGameLogic.handTotal(BankerHand);
		
		if(BaccaratGameLogic.evaluatePlayerDraw(playerHand)) {
			playerHand.add(theDealer.drawOne());
			if(BaccaratGameLogic.evaluateBankerDraw(BankerHand,playerHand.get(2))) {
				BankerHand.add(theDealer.drawOne());
			}
		} else {
			if(countTotalBanker <= 5) {
				BankerHand.add(theDealer.drawOne());
			}
		}

	}
	
	double evaluateWinnings() {
		
		String TheWinner = evaluteOption();
		if(TheWinner == "Player" || TheWinner == "Banker") {
			currentBet = currentBet*1;
		}
		else if (TheWinner == "Draw" ) {
			currentBet = currentBet*8;
		}
		
		return currentBet;
	}
	
	String evaluteOption() {
		String option = BaccaratGameLogic.whoWon(playerHand, BankerHand);
		return option;
	}
	
	public int PlayerPoints() {
		return BaccaratGameLogic.handTotal(playerHand);
	}
	public int BankerPoints() {
		return BaccaratGameLogic.handTotal(BankerHand);
	}
}
