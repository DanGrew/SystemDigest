/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.utility.threading;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link ThreadedWrapper} test.
 */
public class ThreadedWrapperTest {

   private CountDownLatch latch;
   private Runnable runnable;
   private boolean executed;
   private ThreadedWrapper systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      latch = new CountDownLatch( 1 );
      runnable = () -> {
         executed = true;
         latch.countDown();
      };
      systemUnderTest = new ThreadedWrapper( runnable );
   }//End Method

   @Test public void shouldExecuteRunnable() throws InterruptedException {
      latch.await( 5000, TimeUnit.MILLISECONDS );
      assertThat( executed, is( true ) );
   }//End Method

}//End Class
