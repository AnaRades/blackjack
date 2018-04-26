package com.mindit.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game implements GameConstants {

	private CardSet cardSet;
	private Player player;
	private Player dealer;
	
	public static void main(String args[]) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean keepPlaying = true;
		Game game;
		while (keepPlaying) {
			try {
				game = new Game();
				game.play();
				System.out.println(PLAY_ANOTHER);
				if (!"Y".equalsIgnoreCase(in.readLine())) {
					keepPlaying = false;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(GOOD_DAY);
	}
	
	public Game() {
		this.cardSet = new CardSet();
		this.player = new Player();
		this.dealer = new Player().asDealer();
	}
	
	public void play() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
		//initial draw
		System.out.println(INITIAL_DRAW);
		//dealer gets 2 cards, display second one as hidden
		dealer.takeTurn(cardSet);
		//player receives 2 cards, display them both
		player.takeTurn(cardSet);

		// if player has blackjack he stands and wins if other player doesn't have the same
		if (player.isBlackJack()) {
			dealer.showHiddenCard();
			//check if dealer also has blackjack
			if (dealer.isBlackJack()) {
				//if yes, it's a draw
				System.out.println(BJ_DRAW);
			} else {
				//otherwise Player wins
				System.out.println(PLAYER_WINS_BJ);
			}
			return;
		}
		
		boolean isPlayerBust = false;
		int wrongAnswer = 0;
		String answer;
		//prompt user to take other cards or stop
		while(true) {
			System.out.println("Do you want to draw another card? (Y/N)");
			System.in.read();
			answer = in.readLine();
			if("Y".equalsIgnoreCase(answer.trim())) {
				player.takeTurn(cardSet);
				if(player.getHandSize() == 21) {
					System.out.println("BlackJack!");
					break;
				}
				if(player.getHandSize() > 21) {
					isPlayerBust = true;
					break;
				}
			} else if("N".equalsIgnoreCase(answer.trim())) {
				//end player, compute hand
				break;
			} 
			if(wrongAnswer < 3) {
				System.out.println("Do you want to draw another card? (Y/N)");
				answer = in.readLine();
				wrongAnswer++;
			} else {
				System.out.println("Go home player, you're drunk!");
				isPlayerBust = true;
				break;
			}
		}
		System.out.println("Player had a hand of " + player.getHandSize());
		if(isPlayerBust) {
			System.out.println("Player was bust, Dealer wins!");
			return;
		}
		//show hidden card
		dealer.showHiddenCard();
	
		if(dealer.getHandSize() >= 17) {
			System.out.println("Dealer stops");
		} else {
			//following rules of the game dealer takes other cards
			//while hand is below 17 take card
			while(true) {
				dealer.takeTurn(cardSet);
				if(dealer.getHandSize() < 17) {
					System.out.println("Dealer draws another");
				} else {
					break;
				}
			}
			System.out.println("dealer stops");
		}
		//compare hands and declare winner
		if(dealer.getHandSize() == player.getHandSize()) {
			System.out.println("It's a draw!");
		} else if(dealer.getHandSize() < player.getHandSize()) {
			System.out.println("Player had a better hand, wins!");
		} else {
			System.out.println("Dealer had a better hand, wins!");
		}
	}
}
