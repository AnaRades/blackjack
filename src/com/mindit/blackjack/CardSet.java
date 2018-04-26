package com.mindit.blackjack;

import java.util.ArrayList;
import java.util.Random;

public class CardSet extends ArrayList<Card> {
	
	//added to suppress the warning, no actual use
	private static final long serialVersionUID = 1L;

	public CardSet() {
		super(52);
		for(int i=1; i<15; i++) {
			//add the ACE as card of number 1
			if(i == 11) {
				continue;
			}
			for(CardSuit suit : CardSuit.values()) {
				this.add(new Card(i, suit));
			}
		}
	}
	
	/**
	 * Takes and returns a random card from the deck
	 * @return
	 */
	public Card takeCard() {
		Random rand = new Random();
		int index = rand.nextInt(this.size());
	
		return this.remove(index);
	}
	
}
