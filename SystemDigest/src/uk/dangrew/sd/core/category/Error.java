/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.category;

import javafx.scene.paint.Color;

/**
 * The {@link Error} provides a {@link Category} for errors that are found.
 */
public class Error implements Category {

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return "Error";
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Color getColour() {
      return Color.RED;
   }//End Method

}//End Class
