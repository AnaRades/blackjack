package com.mindit.blackjack;

import java.io.IOException;
import java.util.Scanner;

public class Game implements GameConstants {

	private CardSet cardSet;
	private Player player;
	private Player dealer;
	
	private static final Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		boolean keepPlaying = true;
		
		//initial {javaWhitespace}+ was not working correctly
		sc.useDelimiter("\\s*");
		
		Game game;
		while (keepPlaying) {
			try {
				game = new Game();
				game.play();
				print(PLAY_ANOTHER);
				
				if (!"Y".equalsIgnoreCase(sc.next().trim())) {
					keepPlaying = false;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		sc.close();
		print(GOOD_DAY);
	}
	
	public Game() {
		this.cardSet = new CardSet();
		this.player = new Player();
		this.dealer = new Player().asDealer();
	}
	
	public void play() throws IOException {
		//initial draw
		print(INITIAL_DRAW);
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
				print(BJ_DRAW);
			} else {
				//otherwise Player wins
				print(PLAYER_WINS_BJ);
			}
			return;
		}
		
		boolean isPlayerOut = false;
		int wrongAnswer = 0;
		String answer;
		//prompt user to take other cards or stop
		while(true) {
			print("Do you want to draw another card? (Y/N)");
			answer = sc.next();
			if("Y".equalsIgnoreCase(answer.trim())) {
				player.takeTurn(cardSet);
				if(player.getHandSize() == 21) {
					System.out.println("BlackJack!");
					break;
				}
				if(player.isBust()) {
					isPlayerOut = true;
					break;
				}
			} else if("N".equalsIgnoreCase(answer.trim())) {
				//end player, compute hand
				break;
			} else if(wrongAnswer < 3) {
				wrongAnswer++;
			} else if(wrongAnswer >= 3) {
				print("Go home player, you're drunk!");
				isPlayerOut = true;
				break;
			}
		}
		print("Player had a hand of " + player.getHandSize());
		if(isPlayerOut) {
			print("Player is out, Dealer wins!");
			return;
		}
		//show hidden card
		dealer.showHiddenCard();
	
		if(dealer.getHandSize() >= 17) {
			print("Dealer stops");
		} else {
			//following rules of the game dealer takes other cards
			//while hand is below 17 take card
			while(true) {
				dealer.takeTurn(cardSet);
				if(dealer.getHandSize() < 17) {
					print("Dealer draws another");
				} else {
					break;
				}
			}
			print("dealer stops");
		}
		if(dealer.isBust()) {
			print("Dealer is bust, Player wins!");
			return;
		}
		
		//compare hands and declare winner
		int result = dealer.compareTo(player);
		
		if(result == 0) {
			print("It's a draw!");
		} else if(result < 0) {
			print("Player had a better hand, wins!");
		} else {
			print("Dealer had a better hand, wins!");
		}
	}
	
	private static void print(String txt) {
		System.out.println(txt);
	}
}
