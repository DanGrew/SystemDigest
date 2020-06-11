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
public class ObjectAllocation extends Category {

   public ObjectAllocation() {
      super( "Object Allocation", Color.BLUE );
   }//End Constructor
}//End Class
