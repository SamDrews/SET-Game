/**
 * Board.java
 *
 * A class for playing the card game SET. It implements methods that obey the rules of this board game.
 *
 * @author Nico Suarez-Canton, Samson Drews, Alex Brown
 */

import javax.swing.*;
import java.util.ArrayList;


public class Board {

    private ArrayList<Card> currentBoard;   // Holds the cards currently on the board.
    private ArrayList<Card> selectedCards;  // Holds references to cards the user clicks and updates the array
    private Deck currentDeck;   // Hold a reference to the current Deck in play.

    private String notASet;
    private boolean boardPlayable;  // True if there is at least a valid SET in currentBoard.
    private boolean gameOver;   // True when game has ended

    private ArrayList<ArrayList<Card>> setsAvailable;   // Holds the SETs currently on the Board
    private int setsAvailableIndex; // Instance variable to track the last SET that was sent to View

    /**
     * Constructor for Board.java objects. It creates a new Deck, shuffles it and places 12 cars in the current board.
     */
    public Board() {

        // Empty ArrayLists for the current board and the cards the user selects.
        currentBoard = new ArrayList<>();
        selectedCards = new ArrayList<>();

        currentDeck = new Deck();
        currentDeck.shuffe();

        for (int i = 0; i < 12; i++) {
            // Note: Deck.deal() updates the cards left in the Deck.
            currentBoard.add(currentDeck.deal());
        }

        notASet = "";

        setsAvailable = findSets();
        setsAvailableIndex = 0;


    }


    /**
     * This method return the index corresponding to the next item in setsAvailable.
     *
     * @return an integer corresponding to the next item in setsAvailable.
     */
    public int getSetsAvailableIndex() {
        return setsAvailableIndex;
    }


    /**
     * This method gets the current Board displayed to the user
     *
     * @return the current Board
     */
    public ArrayList<Card> getCurrentBoard() {
        return currentBoard;
    }


    /**
     * This method get the current Deck being used
     *
     * @return the Deck being used
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }


    /**
     * This method returns the next SET in setsAvailable.
     *
     * @return the next valid SET in setsAvailable.
     */
    public ArrayList<Card> getNextSetAvaialble() {

        ArrayList<Card> nextPossibleSet = setsAvailable.get(setsAvailableIndex);
        setsAvailableIndex++;

        return nextPossibleSet;
    }


    /**
     * This method returns a reference to an ArrayList of ArrayLists of Card objects that are sets.
     *
     * @return a reference to an ArrayList of ArrayLists of Card objects that are sets.
     */
    public ArrayList<ArrayList<Card>> getSetsAvailable() {
        return setsAvailable;
    }


    /**
     * This method returns the reason why the last three cards selected were not a set, if they were not.
     *
     * @return a String containing why last three cards selected were not a set.
     */
    public String getNotASet() {
        return notASet;
    }


    /**
     * This method adds a Card to the current Board
     *
     * @return the card added
     */
    public Card addCardFromDeck() {

        Card cardFromDeck = null;

        if (!currentDeck.isEmpty()) {
            cardFromDeck = currentDeck.deal();
            currentBoard.add(cardFromDeck);
            setsAvailable = findSets();
        }

        return cardFromDeck;
    }


    /**
     * This method adds a user selected Card
     *
     * @param selectedCard the Card the user selects
     */
    public void addCardsToSelected(Card selectedCard) {

        selectedCards.add(selectedCard);
    }


    /**
     * This method removes a user selected Card
     *
     * @param selectedCard the Card the user selects
     */
    public void removeCardsFromSelected(Card selectedCard) {

        selectedCards.remove(selectedCard);
    }


    /**
     * This method clears the Cards that have been selected by the user.
     */
    public void clearSelected() {
        selectedCards = new ArrayList<>();

    }


    /**
     * This method returns True if the selected Cards are a SET.
     *
     * @return true if the selected Cards are a SET.
     */
    public boolean checkForSet(Card card1, Card card2, Card card3, boolean verbose) {

        String reasoningString;

        boolean sameCount = card1.getCount() == card2.getCount() && card1.getCount() == card3.getCount();
        boolean sameColor = card1.getColor().equals(card2.getColor()) && card1.getColor().equals(card3.getColor());
        boolean sameShading = card1.getShading().equals(card2.getShading()) && card1.getShading().equals(card3.getShading());
        boolean sameShape = card1.getShape().equals(card2.getShape()) && card1.getShape().equals(card3.getShape());

        boolean diffCount = card1.getCount() != card2.getCount() && card1.getCount() != card3.getCount() &&
                card2.getCount() != card3.getCount();
        boolean diffColor = !card1.getColor().equals(card2.getColor()) && !card1.getColor().equals(card3.getColor()) &&
                !card2.getColor().equals(card3.getColor());
        boolean diffShading = !card1.getShading().equals(card2.getShading()) && !card1.getShading().equals(card3.getShading()) &&
                !card2.getShading().equals(card3.getShading());
        boolean diffShape = !card1.getShape().equals(card2.getShape()) && !card1.getShape().equals(card3.getShape()) &&
                !card2.getShape().equals(card3.getShape());

        reasoningString = "Not the same count";
        if (sameCount || diffCount) {
            reasoningString = "Not the same color";
            if (sameColor || diffColor) {
                reasoningString = "Not the same shading";
                if (sameShading || diffShading) {
                    reasoningString = "Not the same shape";
                    if (sameShape || diffShape) {
                        return true;
                    }
                }
            }

        }
        if (verbose) {
            infoBox(reasoningString, "This is not a SET.");
        }
        return false;
    }

    /**
     * @param infoMessage
     * @param titleBar
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * This method gets the current user selected Cards
     *
     * @return the Cards the user selects
     */
    public ArrayList<Card> getSelected() {
        return selectedCards;
    }


    /**
     * This method removes a Card from the current Board
     *
     * @param cardToRemove the Card to be removed
     */
    public void removeFromCurrentBoard(Card cardToRemove) {

        ArrayList<Card> newBoard = new ArrayList<>();

        for (Card c : currentBoard) {
            if (!c.equals(cardToRemove)) {
                newBoard.add(c);
            }
        }

        currentBoard = newBoard;

    }


    /**
     * This method adds three Cards to the current Board.
     */
    public ArrayList<Card> addThreeMoreCards() {
        if (currentBoard.size() <= 12) {
            ArrayList<Card> addThree = new ArrayList<>(3);

            for (int i = 0; i < 3; i++) {
                Card newCard = this.addCardFromDeck();

                if (newCard != null) {
                    addThree.add(newCard);

                }
            }

            return addThree;
        } else {
            return null;
        }
    }


    /**
     * This method finds all the SETs available in the current board.
     *
     * @return an ArrayList of SETs (ArrayList<Card>)
     */
    public ArrayList<ArrayList<Card>> findSets() {

        ArrayList<ArrayList<Card>> sets = new ArrayList<>();

        int boardSize = currentBoard.size();

        for (int index = 0; index < boardSize; index++) {

            Card card1 = currentBoard.get(index);

            for (int index1 = 0; index1 < boardSize; index1++) {

                Card card2 = currentBoard.get(index1);

                for (int index2 = 0; index2 < boardSize; index2++) {

                    Card card3 = currentBoard.get(index2);

                    boolean differentCards = !card1.equals(card2) && !card2.equals(card3) && !card1.equals(card3);

                    if (differentCards && checkForSet(card1, card2, card3, false)) {

                        ArrayList<Card> aSet = new ArrayList<>();
                        aSet.add(card1);
                        aSet.add(card2);
                        aSet.add(card3);

                        if (notAlreadyAdded(sets, aSet)) {
                            sets.add(aSet);
                        }

                    }
                }
            }
        }

        setsAvailable = sets;
        return sets;
    }


    public String toString() {
        String currentBoard = "";
        int index = 1;
        for (Card card : this.currentBoard) {
            currentBoard += index + " " + card.toString() + "\n";
            index++;
        }
        return currentBoard;
    }


    /*
     * Private helper method that prevents findSets() from adding the same SET to setsAvaialble but with the Cards in
     * a different order.
     *
     */
    private boolean notAlreadyAdded(ArrayList<ArrayList<Card>> sets, ArrayList<Card> possibleSet) {
        for (ArrayList<Card> set : sets) {
            if (set.contains(possibleSet.get(0)) && set.contains(possibleSet.get(1)) && set.contains(possibleSet.get(2))) {
                return false;
            }
        }

        return true;
    }

}
