/**
 * Game.java
 *
 * A class to hold a JApple-based GUI for playing the game SET.
 *
 * @authors Nico Suarez-Canton, Samson Drews, Alex Brown
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Game extends JApplet {

    private static final long serialVersionUID = 1L;

    private Board brd;


    JLabel game_label = new JLabel("SET");

    // Sidebar container that holds JButtons
    JPanel control_panel = new JPanel();
    // Container where cards are displayed
    PlayingBoard board_panel;


    // JButtons for selecting/changing game mode
    JButton change_mode = new JButton("Change Mode");
    JButton tutorial_mode = new JButton("Tutorial");
    JButton solitaire_mode = new JButton("Solitaire");

    // JButtons for player functionality
    JButton add_three = new JButton("Add Three");
    JButton show_set = new JButton("Show SET");
    JButton show_sets = new JButton("Show SETs");
    JButton sol_deal = new JButton("Deal");
    JButton tut_deal = new JButton("Deal");
    JButton next_set = new JButton("Next SET");


    // Necessary method for JApplet.
    public void init() {
        this.setSize(800, 600);

        setup_layout();
        setup_button_listeners();
    }


    // Sets up button listeners.
    private void setup_button_listeners() {

        // Set up button listeners for game modes and for changing game modes.
        change_mode.addActionListener(new ChangeModeButtonListener());
        tutorial_mode.addActionListener(new TutorialButtonListener());
        solitaire_mode.addActionListener(new SolitaireButtonListener());

        // Set up button listeners for the rest of actions.
        add_three.addActionListener(new AddThreeButtonListener());
        show_set.addActionListener(new ShowSetButtonListener());
        show_sets.addActionListener(new ShowSetsButtonListener());
        sol_deal.addActionListener(new SolDealButtonListener());
        tut_deal.addActionListener(new TutDealButtonListener());
        next_set.addActionListener(new NextSetButtonListener());

    }


    // Sets up the layout of the interface.
    private void setup_layout() {

        // Sets the layout manager for this applet to BorderLayout().
        setLayout(new BorderLayout());

        /*
        Changes the background color of the control panel (right side) to ORANGE.
        Sets is layout to BoxLayout().
        */
        control_panel.setBackground(Color.ORANGE);
        control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.Y_AXIS));

        // Adds buttons to control_panel.
        control_panel.add(tutorial_mode);
        control_panel.add(solitaire_mode);

        // Adds the rest of buttons to control_panel, but they are hidden.
        control_panel.add(tut_deal);
        tut_deal.setVisible(false);
        control_panel.add(sol_deal);
        sol_deal.setVisible(false);
        control_panel.add(show_sets);
        show_sets.setVisible(false);
        control_panel.add(show_set);
        show_set.setVisible(false);
        control_panel.add(add_three);
        add_three.setVisible(false);
        control_panel.add(next_set);
        next_set.setVisible(false);


        // Add each item to the layout.
        add("North", game_label);
        add("South", change_mode);
        add("West", control_panel);


    }


    /**
     * This button is located at the bottom of the user interface and it allows the user to change the game mode between
     * tutorial and solitaire.
     */
    private class ChangeModeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            tutorial_mode.setVisible(true);
            solitaire_mode.setVisible(true);

            tut_deal.setVisible(false);
            sol_deal.setVisible(false);
            show_sets.setVisible(false);
            show_set.setVisible(false);
            add_three.setVisible(false);
            next_set.setVisible(false);

            remove(board_panel);

        }
    }


    /**
     * Allows the user to start a game in Tutorial mode.
     */
    private class TutorialButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorial_mode.setVisible(false);
            solitaire_mode.setVisible(false);
            tut_deal.setVisible(true);
        }
    }


    /**
     * Allows the users to start a game in Solitaire mode.
     */
    private class SolitaireButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            solitaire_mode.setVisible(false);
            tutorial_mode.setVisible(false);
            sol_deal.setVisible(true);

        }
    }


    /**
     * Shows a SET that is already on the current board.
     */
    private class ShowSetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<ArrayList<Card>> setsAvailable = brd.getSetsAvailable();

            if (!setsAvailable.isEmpty()) {

                ArrayList<Card> possibleSet = brd.getSetsAvailable().get(0);


                for (Card card : possibleSet) {
                    card.setBackground(Color.pink);
                    card.revalidate();
                }

            } else {
                brd.infoBox("No SET in the current board.", "SET");
            }

            board_panel.revalidate();

        }
    }


    /**
     * Handles showing the first set available in the current board, and allows the user to request more SETs (if any)
     * in Tutorial mode.
     */
    private class ShowSetsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<ArrayList<Card>> setsAvailable = brd.getSetsAvailable();

            show_sets.setVisible(false);

            if (setsAvailable.size() > 1) {

                ArrayList<Card> nextSet = brd.getNextSetAvaialble();

                for (Card card : nextSet) {
                    card.setBackground(Color.lightGray);
                }

                next_set.setVisible(true);
                tut_deal.setVisible(false);

            }

            if (setsAvailable.size() == 1) {

                ArrayList<Card> nextSet = brd.getNextSetAvaialble();

                for (Card card : nextSet) {
                    card.setBackground(Color.lightGray);
                }
                tut_deal.setVisible(true);

            }

        }
    }


    /**
     * This class allows the user to deal a new board for a SET game in Tutorial mode.
     */
    private class TutDealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            brd = new Board();
            if(board_panel != null){
                remove(board_panel);
            }

            board_panel = new PlayingBoard(brd, true);
            board_panel.setBackground(Color.GREEN);
            add("Center", board_panel);

            ArrayList<Card> cardsOnBoard = brd.getCurrentBoard();

            tut_deal.setVisible(false);
            show_sets.setVisible(true);

            for (Card card : cardsOnBoard) {
                board_panel.addCard(card);
            }
        }
    }


    /**
     * Call to deal cards to board and forwards those cards to display. If board already exists, creates a new board
     * to display.
     */
    private class SolDealButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            brd = new Board();
            board_panel = new PlayingBoard(brd, false);

            board_panel.setBackground(Color.GREEN);
            add("Center", board_panel);


            ArrayList<Card> cardsOnBoard = brd.getCurrentBoard();

            ArrayList<ArrayList<Card>> setsAvailable = brd.getSetsAvailable();

            sol_deal.setVisible(false);

            add_three.setVisible(true);

            if (!setsAvailable.isEmpty()) {
                show_set.setVisible(true);
            }

            for (ArrayList<Card> set : setsAvailable) {
                System.out.println(set.toString());
            }

            for (Card card : cardsOnBoard) {
                board_panel.addCard(card);
            }

            board_panel.revalidate();


        }
    }


    /**
     * Calls brd to add three cards to the board and then takes the array of cards that were added and adds it
     * to the display.
     */
    private class AddThreeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Card> cardsToAdd = brd.addThreeMoreCards();
            if (cardsToAdd != null) {
                for (Card card : cardsToAdd) {
                    if (card != null) {
                        board_panel.addCard(card);
                    }
                }
            }
        }
    }


    /**
     * If there is more than one possible SET on the current board, this class handles showing the user possible SETs
     * whenever the user clicks Next Set.
     */
    private class NextSetButtonListener implements ActionListener {

        private void backToWhiteBakcground() {

            Component[] cards = board_panel.getComponents();
            for (Component card : cards) {
                card.setBackground(Color.white);
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            backToWhiteBakcground();

            ArrayList<ArrayList<Card>> setsAvailable = brd.getSetsAvailable();

            if (setsAvailable.size() > brd.getSetsAvailableIndex()) {
                ArrayList<Card> nextSet = brd.getNextSetAvaialble();

                for (Card card : nextSet) {
                    card.setBackground(Color.lightGray);
                }

            }

            if (setsAvailable.size() == brd.getSetsAvailableIndex()) {

                tut_deal.setVisible(true);
                next_set.setVisible(false);

            }

        }
    }
}