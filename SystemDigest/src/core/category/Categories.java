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
   
   private static final Category ERROR = new Error();
   private static final Category INFORMATION = new Information();
   private static final Category STATUS = new Status();
   private static final Category OBJECT_ALLOCATION = new ObjectAllocation();
   private static final Category PROCESSING_SEQUENCE = new ProcessingSequence();
   
   /**
    * {@link Category} of {@link core.message.Message} used when an error is detected.
    * @return the {@link Category}.
    */
   public static Category error() {
      return ERROR;
   }//End Method
   
   /**
    * {@link Category} of {@link core.message.Message} used for showing general information.
    * @return the {@link Category}.
    */
   public static Category information() {
      return INFORMATION;
   }//End Method
   
   /**
    * {@link Category} of {@link core.message.Message} used for showing state changes and the status of something.
    * @return the {@link Category}.
    */
   public static Category status() {
      return STATUS;
   }//End Method
   
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
