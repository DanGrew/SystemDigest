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
 * Simple class to wrap a {@link Runnable} in a {@link Thread}. This helps to 
 * decouple threading from other objects.
 */
public class ThreadedWrapper {
   
   /**
    * Constructs a new {@link ThreadedWrapper}. This will launch a new {@link Thread}
    * with the given {@link Runnable} and start it immediately.
    * @param runnable the {@link Runnable} to run. This is responsible for stopping the
    * {@link Thread} safely with an exit condition.
    */
   public ThreadedWrapper( Runnable runnable ) {
      new Thread( runnable ).start();
   }//End Constructor

}//End Class
