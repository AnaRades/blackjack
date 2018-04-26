package com.mindit.blackjack;

public class Card {

	private static final int SPECIAL_VALUE = 10;
	
	private CardSuit suit;
	private int number;
	
	public Card(int number, CardSuit suit) {
		this.number = number;
		this.suit = suit;
	}
	/**
	 * Cards 1-10 have their face value
	 * Face cards value of 10
	 * ACE card has value of 1 or 11, depending on the hand
	 * @return
	 */
	public int getValue() {
		if(number > 1 && number < 11) {
			return number;
		} else if(number > 11) {
			return SPECIAL_VALUE;
		}
		return 1;
	}
	
	public int getCardNumber() {
		return this.number;
	}
	
	/**
	 * 
	 * @return true if the card value is 10
	 */
	public boolean isTenValueCard() {
		return (number >= 10);
	}
	/**
	 * 
	 * @return true if the card is an ACE
	 */
	public boolean isAceCard() {
		return (number == 1);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		String rank;
		if (number > 1 && number <= 10) {
			sb.append(number);
		} else {
			switch (number) {
			case 1: {
				rank = "Ace";
				break;
			}
			case 12: {
				rank = "J";
				break;
			}
			case 13: {
				rank = "Q";
				break;
			}
			case 14: {
				rank = "K";
				break;
			}
			default: {
				rank = "";
			}
			}
			sb.append(rank);
		}
		sb.append(" ").append(suit.suitName);
		return sb.toString();
	}
}
