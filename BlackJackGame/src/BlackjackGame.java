import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        if ("J".equals(rank) || "Q".equals(rank) || "K".equals(rank)) {
            return 10;
        } else if ("A".equals(rank)) {
            return 11;
        } else {
            return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}


class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}


class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int calculatePoints() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            total += card.getValue();
            if ("A".equals(card.getRank())) {
                aces++;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public void displayHand() {
        for (Card card : cards) {
            System.out.println(card);
        }
        System.out.println("Total points: " + calculatePoints());
    }
}


public class BlackjackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void dealInitialCards() {
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    public void playerTurn() {
        boolean playerBusted = false;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Player's hand:");
            playerHand.displayHand();

            if (playerHand.calculatePoints() > 21) {
                System.out.println("Player busts!");
                playerBusted = true;
                break;
            }

            System.out.println("Do you want to hit or stand? (H/S)");
            String decision = scanner.next();

            if ("H".equalsIgnoreCase(decision)) {
                playerHand.addCard(deck.drawCard());
            } else if ("S".equalsIgnoreCase(decision)) {
                break;
            }
        }

        if (!playerBusted) {
            dealerTurn();
        }
    }

    public void dealerTurn() {
        System.out.println("Dealer's hand:");
        dealerHand.displayHand();

        while (dealerHand.calculatePoints() < 17) {
            dealerHand.addCard(deck.drawCard());
            System.out.println("Dealer hits.");
            dealerHand.displayHand();
        }

        if (dealerHand.calculatePoints() > 21) {
            System.out.println("Dealer busts!");
        }

        determineWinner();
    }

    public void determineWinner() {
        int playerPoints = playerHand.calculatePoints();
        int dealerPoints = dealerHand.calculatePoints();

        if (playerPoints > 21) {
            System.out.println("Dealer wins!");
        } else if (dealerPoints > 21) {
            System.out.println("Player wins!");
        } else if (playerPoints > dealerPoints) {
            System.out.println("Player wins!");
        } else if (dealerPoints > playerPoints) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            BlackjackGame game = new BlackjackGame();
            game.dealInitialCards();
            game.playerTurn();

            System.out.println("Do you want to play a new game or quit? (N/Q)");
            String choice = scanner.next();

            if ("Q".equalsIgnoreCase(choice)) {
                System.out.println("Thanks for playing! Goodbye!");
                break;
            } else if (!"N".equalsIgnoreCase(choice)) {
                System.out.println("Invalid choice, quitting the game.");
                break;
            }
        }
    }
}