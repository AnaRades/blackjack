package com.mindit.blackjack;

public enum CardSuit {
	HEARTS("Hearts"),
	SPADES("Spades"),
	CLUBS("Clubs"),
	DIAMONDS("Diamonds");
	
	String suitName;
	CardSuit(String suitName) {
		this.suitName = suitName;
	}
}
