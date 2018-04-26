package com.mindit.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating player properties and game actions
 * 
 */
public class Player {

	private int handSize;
	private List<Card> hand;
	private int turnCount = 1;
	private boolean containsAce = false;
	private boolean isDealer;
	
	public Player() {
		this.handSize = 0;
		this.hand = new ArrayList<>();
		this.turnCount = 1;
		this.containsAce = false;
		this.isDealer = false;
	}
	/**
	 * 
	 * @return special player, the Dealer
	 */
	public Player asDealer() {
		this.isDealer = true;
		return this;
	}
	
	public void takeTurn(CardSet cardSet) {
		//initial draw has 2 cards
		if(turnCount == 1) {
			drawCard(cardSet.takeCard());
			drawCard(cardSet.takeCard());
			showfirstHand();
		} else {
			Card card= cardSet.takeCard(); 
			drawCard(card);
			System.out.println(getPrintPrefix()+ card + " current size = " + handSize);
		}
		turnCount++;
	}
	
	private void drawCard(Card card) {
		hand.add(card);		
		if(card.isAceCard()) {
			containsAce = true;
		}
		handSize += card.getValue();
	}
	
	/**
	 * Hand changes depending on the presence of ACE card 
	 * @return the sum of cards in the hand
	 */
	public int getHandSize() {
		//use ACE with value as 11 if it doesn't mean the player busts
		if (containsAce && handSize <= 11) {
			return handSize+10;
		}
		return handSize;
	}
	
	/**
	 * Blackjack is when we have 2 cards, an ace and one of ten value
	 * @return true if player has blackjack
	 */
	public boolean isBlackJack() {
		if(handSize != 2) {
			return false;
		}
		//if neither card is an ACE, we don't have blackjack
		if(!hand.get(0).isAceCard() && !hand.get(0).isTenValueCard()) {
			return false;
		}
		//if neither card is a 10 point card, we don't have blackjack
		if(!hand.get(1).isAceCard() && !hand.get(1).isTenValueCard()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Show dealer hidden card
	 */
	public void showHiddenCard() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Dealer Hidden Card Was: ");
		sb.append(hand.get(1).toString());
		sb.append(". Dealer hand is " + handSize);
		
		System.out.println(sb.toString());
	}
	/**
	 * Print initial draw 
	 */
	private void showfirstHand() {
		System.out.print(getPrintPrefix());
		if(isDealer) {
			System.out.println(hand.get(0).toString() + ", Hidden");
		} else {
			StringBuilder handStr = new StringBuilder();
			for(Card card : hand) {
				handStr.append(card).append(", ");
			}
			//2 is length of ", " separator
			System.out.println(handStr.substring(0, handStr.length()-2));
		}
	}
	
	/**
	 * Displays the player's name and turn count
	 * @return
	 */
	private String getPrintPrefix() {
		StringBuilder sb = new StringBuilder();
		if(isDealer) {
			sb.append("Dealer");
		} else {
			sb.append("Player");
		}
		sb.append(" Draw(");
		sb.append(turnCount);
		sb.append(") : ");
		
		return sb.toString();
	}
}
