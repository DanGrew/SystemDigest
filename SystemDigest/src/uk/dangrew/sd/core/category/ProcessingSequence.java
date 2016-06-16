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
 * The {@link ProcessingSequence} provides a default implementation of {@link Category} that
 * can be used to show object a number of steps in a processing sequence, like when iterating
 * over a list of items.
 */
public class ProcessingSequence implements Category {

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return "Processing Sequence";
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Color getColour() {
      return Color.GREEN;
   }//End Method

}//End Class
