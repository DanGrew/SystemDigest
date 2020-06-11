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
public class ProcessingSequence extends Category {

   public ProcessingSequence() {
      super( "Processing Sequence", Color.GREEN );
   }//End Constructor

}//End Class
