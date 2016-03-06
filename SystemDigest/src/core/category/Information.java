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
 * The {@link Information} provides a {@link Category} for general information messages.
 */
public class Information implements Category {

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return "Information";
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Color getColour() {
      return Color.BLACK;
   }//End Method

}//End Class
