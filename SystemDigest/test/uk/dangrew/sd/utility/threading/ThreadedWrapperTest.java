/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.utility.threading;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link ThreadedWrapper} test.
 */
public class ThreadedWrapperTest {

   private CountDownLatch latch;
   @Mock private ProtectedRunnable runnable;
   private ThreadedWrapper systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ThreadedWrapper();
   }//End Method

   @Test public void shouldExecuteRunnableGivenNumberOfTimes() throws InterruptedException {
      final int numberOfCalls = 7;
      latch = new CountDownLatch( numberOfCalls );
      doAnswer( invocation -> { latch.countDown(); return null; } ).when( runnable ).iterate();
      
      systemUnderTest.wrap( runnable, numberOfCalls );
      
      latch.await( 5000, TimeUnit.MILLISECONDS );
      verify( runnable, times( numberOfCalls ) ).iterate();
   }//End Method
   
   @Test public void shouldShutdownWhenInstructed() throws InterruptedException{
      final int target = 8;
      latch = new CountDownLatch( target );
      
      doAnswer( invocation -> { 
         latch.countDown(); 
         if ( latch.getCount() == 0 ) {
            systemUnderTest.shutdown();
         }
         return null; 
      } ).when( runnable ).iterate();
      
      systemUnderTest.wrap( runnable, -1 );
      latch.await();
      
      verify( runnable, times( target ) ).iterate();
   }//End Method

}//End Class
