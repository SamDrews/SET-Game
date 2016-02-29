/**
 * Card.java
 * Class for a SET card.
 *
 * Images used for this class have been borrowed from https://github.com/SWhelan/Set/tree/master/images
 *
 * @author Nico Suarez-Canton, Samson Drews, Alex Brown
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class Card extends JPanel {

    private String shape, color, shading;
    private int count;
    private boolean selected;
    private BufferedImage cardImage;


    /**
     * Create a SET card, setting its shape, color, count, and shading
     * @param shape the type of shape the card will contain
     * @param color the color of the shape
     * @param count the number of shapes on a card
     * @param shading the type of shading the shapes will have
     */
    public Card(String color, String shape, String shading, int count) {

        selected = false;

        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.shape = shape;
        this.color = color;
        this.count = count;
        this.shading = shading;

        String imgPath = "images/" + color + "_" + shape + "_" + shading + ".png";

        try {
            URL url = getClass().getResource(imgPath);
            cardImage = ImageIO.read(url);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        this.setBackground(Color.white);
    }


    /**
     * This method overrides the paintComponent method of JPanel.
     * Author: https://github.com/SWhelan/Set/blob/master/CardPanel.java
     *
     * @param g the current Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xCoordinate = (int) Math.floor(this.getWidth() / 2) - 50;
        int yCoordinate = (int) Math.floor(this.getHeight() / 2);
        if (count == 1) {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 25, null);
        } else if (count == 2) {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 50, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate, null);
        } else {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 75, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate - 25, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate + 25, null);
        }

    }


    /**
     * Gets the shape on the card
     *
     * @return the shape
     */
    public String getShape() {
        return shape;
    }

    /**
     * Gets the color of the shape on the card
     *
     * @return the color of the shape
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the number of shapes on the card
     *
     * @return the number of shapes
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the shading of the shape on the card
     *
     * @return the shading of the shape
     */
    public String getShading() {
        return shading;
    }


    /**
     * This method returns the current state of the Card
     *
     * @return true if Card has been selected by user.
     */
    public boolean isSelected() {

        return selected;
    }

    /**
     * This method returns a String representation of the Card
     *
     * @return a String representaion of a Card object.
     */
    public String toString() {
        return color + " - " + shape + " - " + shading + " - " + count + " - " + selected;

    }

    /**
     * This method set whether a Card has been selected by the user.
     *
     * @param selected true if the card has been selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public boolean equals(Card anotherCard) {
        return this.getCount() == anotherCard.getCount()
                && this.getShape().equals(anotherCard.getShape())
                && this.getShading().equals(anotherCard.getShading())
                && this.getColor().equals(anotherCard.getColor());
    }

}
