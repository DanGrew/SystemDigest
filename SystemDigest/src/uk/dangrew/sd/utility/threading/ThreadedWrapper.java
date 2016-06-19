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
   
   private volatile int shutdownCounter = -1;
   
   /**
    * This will launch a new {@link Thread} with the given {@link Runnable} and start it immediately.
    * @param runnable the {@link Runnable} to run. This is responsible for stopping the
    * {@link Thread} safely with an exit condition.
    * @param iterations the number of iterations to perform, -1 to continue indefinitely.
    */
   public void wrap( ProtectedRunnable runnable, int iterations ) {
      shutdownCounter = iterations;
      new Thread( () -> {
         
         while ( shutdownCounter == -1 || shutdownCounter > 0 ){
            if ( shutdownCounter != -1 ) {
               shutdownCounter--;
            }
            
            runnable.iterate();
         }
      } ).start();
   }//End Constructor
   
   /**
    * Method to shutdown the {@link Thread}.
    */
   public void shutdown(){
      shutdownCounter = 0;
   }//End Method

}//End Class
