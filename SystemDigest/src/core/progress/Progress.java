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

}//End Interface
