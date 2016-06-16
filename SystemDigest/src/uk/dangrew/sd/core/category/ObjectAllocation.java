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
 * The {@link ObjectAllocation} provides a default implementation of {@link Category} that
 * can be used to show object allocations, like when an object is created.
 */
public class ObjectAllocation implements Category {

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return "Object Allocation";
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Color getColour() {
      return Color.BLUE;
   }//End Method

}//End Class
