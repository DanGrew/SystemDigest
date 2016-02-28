/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.category;

/**
 * {@link Categories} provides default common categories predefined for external use.
 */
public class Categories {
   
   private static final Category OBJECT_ALLOCATION = new ObjectAllocation();
   private static final Category PROCESSING_SEQUENCE = new ProcessingSequence();
   
   /**
    * {@link Category} of {@link core.message.Message} used for when objects are created.
    * @return the {@link Category}.
    */
   public static Category objectAllocation() {
      return OBJECT_ALLOCATION;
   }//End Method
   
   /**
    * {@link Category} of {@link core.message.Message} used for when an iterative process is
    * being performed.
    * @return the {@link Category}.
    */
   public static Category processingSequence() {
      return PROCESSING_SEQUENCE;
   }//End Method

}//End Class
