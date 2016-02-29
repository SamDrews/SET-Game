/**
 * Deck.java
 *
 * @authors Nico Suarez-Canton, Samson Drews, Alex Brown
 */

import java.util.ArrayList;
import java.util.Collections;


public class Deck {

    private static String[] cardColors = {"green", "purple", "red"};
    private static String[] cardShapes = {"diamond", "oval", "squiggly"};
    private static String[] cardShades = {"empty", "filled", "lined"};
    int[] cardCounts = {1, 2, 3};

    private ArrayList<Card> cards;
    private int cardsLeft;


    /**
     * Constructor for a Deck object. This method adds Card objects to the Deck, and sets the numbers of cards left to
     * full deck.
     */
    public Deck() {
        cards = new ArrayList<>();
        for (String color : cardColors) {
            for (String shape : cardShapes) {
                for (String shade : cardShades) {
                    for (int count : cardCounts) {
                        Card newCard = new Card(color, shape, shade, count);
                        cards.add(newCard);
                        cardsLeft += 1;
                    }
                }
            }
        }

    }


    /**
     * This method returns the card at the beginning of the deck. It also removes such card from the current deck, and it
     * updates the instance variable cardsLeft.
     *
     * @return the Card object at the top of the Deck
     */
    public Card deal() {
        cardsLeft -= 1;
        Card dealtCard = cards.get(0);
        remove(dealtCard);
        return dealtCard;

    }


    /**
     * This method randomizes the order of the Cards in the Deck.
     */
    public void shuffe() {
        Collections.shuffle(cards);
    }


    /**
     * This method removes a given card from the current Deck.
     *
     * @param cardToRemove the Card to remove from the Deck.
     */
    public void remove(Card cardToRemove) {
        cards.remove(cardToRemove);
    }


    /**
     * This method returns a String representation of the Card objects in the current Deck.
     *
     * @return A String representation of a Deck object.
     */
    @Override
    public String toString() {

        int index = 1;
        String cardsInDeck = "";

        for (Card card : cards) {

            if (index < cards.size()) {
                cardsInDeck += index + ": " + card.toString() + "\n";
            } else {
                cardsInDeck += index + ": " + card.toString();
            }

            index += 1;

        }

        return cardsInDeck;
    }

    /**
     * This method returns the number of Card objects left in the current Deck.
     *
     * @return an int with the number of cards left in the current Deck.
     */
    public int getCardsLeft() {
        return cardsLeft;
    }

    /**
     * This method returns true if the Deck is empty.
     */
    public boolean isEmpty() {
        return cards.size() == 0;
    }
}