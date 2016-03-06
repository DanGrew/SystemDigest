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
 * The {@link ProgressCompleteImpl} provides a basic implementation of {@link Progress} that
 * completes the {@link Progress} is monitored in some way.
 */
public class ProgressCompleteImpl implements Progress {

   /**
    * {@inheritDoc}
    */
   @Override public double getPercentage() {
      return 100;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean isComplete() {
      return true;
   }//End Method

}//End Class
