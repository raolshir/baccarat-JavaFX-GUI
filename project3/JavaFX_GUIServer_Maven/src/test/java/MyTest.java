import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	
	//Testing the Card class
	@Test
	void test_Card_Constructor() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for(int i = 1; i < 14; i++) {
			Card card = new Card("Heart",i);
			deck.add(card);
			assertEquals(deck.get(i-1).suite, "Heart", "Wrong the suite of the card should be Hearts");
			assertEquals(deck.get(i-1).value, i, "Wrong the value of the card should be 1");
		}
		
	}
	
	// Testing the BaccaratDealer class
	@Test
	void test_BaccaratDealer_generateDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(dealer.deck.size(), 52, "The deck size should be 52 cards");
	}
	
	@Test
	void test2_BaccaratDealer_generateDeck() {
		ArrayList<Card> card = new ArrayList<>();
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		int count = 0;
		for(int i = 0; i < 52; i++) {
			if(dealer.deck.get(i).suite == "hearts") {
				count++;
			}
		}
		assertEquals(count, 13, "There should only be 13 cards of hearts");
	}

	@Test
	void test_BaccaratDealer_dealHand() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		ArrayList<Card> hand = dealer.dealHand();
		assertEquals(hand.size(), 2, "The size of the hand should be 2");
	}
	
	@Test
	void test2_BaccaratDealer_dealHand() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		ArrayList<Card> hand = dealer.dealHand();
		assertEquals(hand.get(0).suite, "hearts", "The first card should be (hearts,1)");
		assertEquals(hand.get(0).value, 1, "The first card should be (hearts,1)");
		assertEquals(hand.get(1).suite, "spades", "The last card should be (spades,1)");
		assertEquals(hand.get(1).value, 1, "The last card should be (spades,1)");
	}
	
	@Test
	void test_BaccaratDealer_drawOne() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		ArrayList<Card> hand = dealer.dealHand();
		Card oneCard = dealer.drawOne();
		assertEquals(dealer.curPostion, 3, "The current posion of the deck should be in the 3 place");
	}
	
	@Test
	void test2_BaccaratDealer_drawOne() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		ArrayList<Card> hand = dealer.dealHand();
		Card oneCard = dealer.drawOne();
		hand.add(oneCard);
		assertEquals(dealer.deck.get(2).suite, "clubs", "The current posion of the deck suite should be clubs");
		assertEquals(dealer.deck.get(2).value, 1, "The current posion of the deck value should be 1");
	}
	
	@Test
	void test_BaccaratDealer_shuffleDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();
		Card firstCard = new Card("hearts",1);
		ArrayList<Card> hand2 =  dealer.deck;
		assertNotEquals(hand2.get(0), firstCard, "The first card should not be (hearts,1)");
		
	}
	@Test
	void test2_BaccaratDealer_shuffleDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();
		assertEquals(dealer.deck.size(), 52, "The deck size should be 52 cards");
		
	}
	
	@Test
	void test_BaccaratDealer_deckSize() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(dealer.deckSize(), 52,"The deck should be full");
	}
	@Test
	void test2_BaccaratDealer_deckSize() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		Card one = dealer.drawOne();
		assertEquals(dealer.deckSize(), 51,"The deck size should be less by 1");
	}
	
	//testing BaccaratGameLogic class
	@Test
	void test_BaccaratGameLogic_WhoWon() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		ArrayList<Card> hand2 =  new ArrayList<>();
		hand1.add(new Card("hearts",1));
		hand1.add(new Card("hearts",4));
		hand2.add(new Card("hearts",1));
		hand2.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.whoWon(hand1, hand2), "Draw","The game should  be a draw");
	}
	
	@Test
	void test2_BaccaratGameLogic_WhoWon() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		ArrayList<Card> hand2 =  new ArrayList<>();
		hand1.add(new Card("hearts",2));
		hand1.add(new Card("hearts",4));
		hand2.add(new Card("hearts",1));
		hand2.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.whoWon(hand1, hand2), "Player","The Player should win the game");
	}
	
	@Test
	void test_BaccaratGameLogic_handTotal() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		hand1.add(new Card("hearts",1));
		hand1.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.handTotal(hand1), 5,"The hand points should be 5");
		
	}
	
	@Test
	void test2_BaccaratGameLogic_handTotal() {
		ArrayList<Card> hand2 =  new ArrayList<>();
		hand2.add(new Card("hearts",10));
		hand2.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.handTotal(hand2), 4,"The hand points should be 4");
	}
	
	@Test
	void test_BaccaratGameLogic_evaluateBankerDraw() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		hand1.add(new Card("hearts",1));
		hand1.add(new Card("hearts",4));
		
		Card playerDraw = new Card("hearts",4);
		
		assertEquals(BaccaratGameLogic.evaluateBankerDraw(hand1, playerDraw), true,"The Banker should draw a card");
		
	}
	
	@Test
	void test2_BaccaratGameLogic_evaluateBankerDraw() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		hand1.add(new Card("hearts",2));
		hand1.add(new Card("hearts",4));
		
		Card playerDraw = new Card("hearts",4);
		
		assertEquals(BaccaratGameLogic.evaluateBankerDraw(hand1, playerDraw), false,"The Banker should not draw a card");
		
	}
	
	@Test
	void test_BaccaratGameLogic_evaluatePlayerDraw() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		hand1.add(new Card("hearts",1));
		hand1.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.evaluatePlayerDraw(hand1), true,"The Player should draw a card");
		
	}
	
	@Test
	void test2_BaccaratGameLogic_evaluatePlayerDraw() {
		ArrayList<Card> hand1 =  new ArrayList<>();
		hand1.add(new Card("hearts",2));
		hand1.add(new Card("hearts",4));
		
		assertEquals(BaccaratGameLogic.evaluatePlayerDraw(hand1), false,"The Player should not draw a card");
		
	}
	
	//testing BaccaratGame class
	@Test
	void test_BaccaratGame_getBet() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		
		assertEquals(game.currentBet,input,"The bet should be the same");
		
	}
	
	@Test
	void test2_BaccaratGame_getBet() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(34.0);
		
		assertNotEquals(game.currentBet,input,"The bet should not be the same");
		
	}
	
	@Test
	void test_BaccaratGame_startGame() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertNotEquals(game.playerHand.size(),4,"The player hand should be 4");
		
	}
	
	@Test
	void test2_BaccaratGame_startGame() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertNotEquals(game.BankerHand.size(),4,"The player hand should be 4");
		
	}
	
	@Test
	void test_BaccaratGame_evaluateWinnings() {
		BaccaratGame game = new BaccaratGame();
		double input = 20.0;
		game.getBet(input);
		game.startGame();
		if(BaccaratGameLogic.whoWon(game.playerHand, game.BankerHand) == "Draw") {
			assertEquals(game.evaluateWinnings(),160.0,"The winnings are 160.0");
		} else {
			assertEquals(game.evaluateWinnings(),20.0,"The winnings are 20.0");
		}
		
		
	}
	
	@Test
	void test2_BaccaratGame_evaluateWinnings() {
		BaccaratGame game = new BaccaratGame();
		double input = 10.0;
		game.getBet(input);
		game.startGame();
		if(BaccaratGameLogic.whoWon(game.playerHand, game.BankerHand) == "Draw") {
			assertEquals(game.evaluateWinnings(),80.0,"The winnings are 160.0");
		} else {
			assertEquals(game.evaluateWinnings(),10.0,"The winnings are 10.0");
		}
		
	}
	
	@Test
	void test_BaccaratGame_PlayerPoints() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertTrue(game.PlayerPoints() < 10,"The player points should not be greater than 10");
		
	}
	
	@Test
	void test2_BaccaratGame_PlayerPoints() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertTrue(game.PlayerPoints() >= 0,"The player points should be greater than or equal to 0 ");
		
	}
	
	@Test
	void test_BaccaratGame_BankerPoints() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertTrue(game.BankerPoints() < 10,"The player points should not be greater than 10");
		
	}
	
	@Test
	void test2_BaccaratGame_BankerPoints() {
		BaccaratGame game = new BaccaratGame();
		double input = 33.0;
		game.getBet(input);
		game.startGame();
		
		assertTrue(game.BankerPoints() >= 0,"The player points should be greater than or equal to 0 ");
		
	}
}
