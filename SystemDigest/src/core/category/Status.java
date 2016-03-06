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
 * The {@link Status} provides a {@link Category} that can be used for showing the
 * state of objects.
 */
public class Status implements Category {

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return "Status";
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Color getColour() {
      return Color.ORANGE;
   }//End Method

}//End Class
