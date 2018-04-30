package com.mindit.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating player properties and game actions
 * 
 */
public class Player implements Comparable<Player>{

	private int handSize;
	private List<Card> hand;
	private int turnCount = 1;
	private boolean containsAce = false;
	private boolean isDealer;
	private boolean isBust;
	
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
			System.out.println(getPrintPrefix()+ card + " current size = " + getHandSize());
		}
		turnCount++;
	}
	
	private void drawCard(Card card) {
		hand.add(card);		
		if(card.isAceCard()) {
			containsAce = true;
		}
		handSize += card.getValue();
		if(getHandSize() > 21) {
			isBust = true;
		}
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
		if(hand.size() != 2) {
			return false;
		}
		return (getHandSize() == 21);
	}
	
	/**
	 * 
	 * @return true if play hand is over 21
	 */
	public boolean isBust() {
		return this.isBust;
	}
	
	/**
	 * Show dealer hidden card
	 */
	public void showHiddenCard() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Dealer Hidden Card Was: ");
		sb.append(hand.get(1).toString());
		sb.append(". Dealer hand is " + getHandSize());
		
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
	
	@Override
	public int compareTo(Player otherPlayer) {
		if(otherPlayer.isBust) {
			if(this.isBust) {
				return 0;
			}
			return 1;
		}
		if(this.isBust) {
			return -1;
		}
		return (this.getHandSize() - otherPlayer.getHandSize());
	}
}
