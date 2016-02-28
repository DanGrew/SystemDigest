/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.progress;

/**
 * {@link ProgressImpl} provides a basic implementation of the {@link Progress} interface.
 */
public class ProgressImpl implements Progress {

   private double percentage;
   
   /**
    * Constructs a new {@link ProgressImpl}.
    * @param percentage the percentage complete.
    */
   public ProgressImpl( double percentage ) {
      this.percentage = percentage;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public double getPercentage(){
      return percentage;
   }//End Method

}//End Class
