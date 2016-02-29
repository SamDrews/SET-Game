/**
 * PlayingBoard.java
 * <p/>
 * A class for holding a visual representation of a board of SET cards.
 *
 * @author Nico Suarez-Canton, Samson Drews, Alex Brown
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// It is a custom subclass of JPanel and implements the interface MouseListener.
public class PlayingBoard extends JPanel implements MouseListener {

    // Reference to object that holds the game logic; i.e. Deck & Rules
    Board currentBoard;

    /**
     * Constructor for the PlayingBoard class. It takes a reference to the current Board object, which gets
     * initialized in Game.java
     *
     * @param currentPlayingBoard reference to the current Board object.
     */
    public PlayingBoard(Board currentPlayingBoard, boolean tutorialMode) {

        // Stores the currentPlayingBoard from when the user clicked on a Deal listener.
        currentBoard = currentPlayingBoard;

        // Necessary for JPanel to keep track of clicks.
        if (!tutorialMode) {
            addMouseListener(this);
        }
        // LayoutManager so the cards display properly.
        this.setLayout(new GridLayout(3, 5, 5, 5));

    }


    /**
     * This method adds a Card object to the visual representation of the current board.
     *
     * @param cardToAdd a Card object to display on the PlayingBoard.
     */
    public void addCard(Card cardToAdd) {

        this.add(cardToAdd); // JPanel.add() method.
        this.revalidate(); // Necessary to refresh the view.

    }


    public void clearPlayingBoard() {

        Component[] components = this.getComponents();
        for (Component component : components) {
            this.remove(component);
        }


    }


    /**
     * This method adds cards to the current PlayingBoard according to the state of the current Deck.
     */
    public void restoreBoard() {

        Deck currentDeck = currentBoard.getCurrentDeck();
        int cardsLeft = currentDeck.getCardsLeft();

        boolean deckHasCards = cardsLeft >= 3;

        int currentSize = currentBoard.getCurrentBoard().size();

        if (currentSize < 12) {

            if (deckHasCards) {

                for (int index = 0; index < 3; index++) {
                    this.add(currentBoard.addCardFromDeck());

                }

            } else {

                for (int index = 0; index < 3; index++) {
                    JPanel emptyCard = new JPanel();
                    emptyCard.setBackground(Color.green);
                    this.add(emptyCard);
                }
            }

        }

        currentBoard.findSets();

        int possibleSets = currentBoard.getSetsAvailable().size();

        if (currentDeck.isEmpty() && possibleSets == 0) {
            currentBoard.infoBox("GAME OVER\nClick 'Change Mode' to restart.", "Game Status");
        }


        this.revalidate();


    }


    /**
     * This method sets the card to selected if the users click on it. If the users clicks again on it again, this
     * method does the opposite.
     *
     * @param e A MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        Object userClickedOn = getComponentAt(e.getPoint());
        ArrayList<Card> selected = currentBoard.getSelected();

        if (userClickedOn instanceof Card) {

            Card selectedCard = (Card) userClickedOn; // Cast the Card the user clicked on.
            cardSelection(selectedCard);

            if (selected.size() == 3) {

                if (currentBoard.checkForSet(selected.get(0), selected.get(1), selected.get(2), true)) {


                    for (Card card : selected) {
                        this.remove(card);
                        currentBoard.removeFromCurrentBoard(card);
                    }


                    this.restoreBoard();

                } else {

                    for (Card card : selected) {
                        card.setBackground(Color.white);
                        card.setSelected(false);
                    }

                    this.revalidate();


                }

                currentBoard.clearSelected();
            }

        } else {
            System.out.println("User clicked on the Board.");
        }
    }


    /*
     * Private helper method for giving the user visual feedback and modifying the state of a Card.
     */
    private void cardSelection(Card selectedCard) {

        if (selectedCard.isSelected()) {
            selectedCard.setBackground(Color.white);
            selectedCard.setSelected(false);
            currentBoard.removeCardsFromSelected(selectedCard);
        } else {
            selectedCard.setBackground(Color.lightGray);
            selectedCard.setSelected(true);
            currentBoard.addCardsToSelected(selectedCard);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
