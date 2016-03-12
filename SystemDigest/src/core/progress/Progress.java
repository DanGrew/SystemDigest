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
 * The {@link Progress} provides an interface for an object describing progress for the
 * system digest.
 */
public interface Progress {

   /**
    * Method to get the percentage complete that has been logged.
    * @return the percentage.
    */
   public double getPercentage();
   
   /**
    * Method to determine whether the {@link Progress} provides a completion indication.
    * @return true if completion.
    */
   public boolean isComplete();
   
   /**
    * Method to convert the given percentage to the equivalent progress associated with {@link javafx.scene.control.ProgressBar}s.
    * @param percentage the percentage to convert.
    * @return the converted progress.
    */
   public static double percentageToProgress( double percentage ) {
      if ( percentage < 0 ) return 0;
      if ( percentage > 100 ) return 1;
      return percentage / 100.0;
   }//End Method

}//End Interface
