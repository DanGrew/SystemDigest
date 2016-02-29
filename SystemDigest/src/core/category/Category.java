/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.category;

import javafx.scene.paint.Color;

/**
 * The {@link Category} provides an interface to the categories of message that
 * can be provided to the {@link digest.object.ObjectDigest}.
 */
public interface Category {

   /**
    * Method to get the displayed name of the {@link Category}.
    * @return the {@link String} name.
    */
   public String getName();

   /**
    * Method to get the {@link Color} associated with the {@link Category}.
    * @return the {@link Color}.
    */
   public Color getColour();

}//End Interface
