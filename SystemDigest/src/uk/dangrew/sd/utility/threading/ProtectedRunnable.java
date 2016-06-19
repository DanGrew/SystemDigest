/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.utility.threading;

/**
 * The {@link ProtectedRunnable} is like {@link Runnable} but can be protected
 * within a {@link ThreadedWrapper}.
 */
@FunctionalInterface public interface ProtectedRunnable {
   
   /**
    * Method to perform one iteration of the processing.
    */
   public void iterate();

}//End Class
