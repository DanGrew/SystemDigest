/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.utility;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * {@link TestCommon} privides some common, global test utilities.
 */
public class TestCommon {
   
   /**
    * Method to assert that the given {@link Font} is bold.
    * @param font the {@link Font} to assert.
    */
   public static void assertFontBold( Font font ){
      assertThat( FontWeight.findByName( font.getStyle() ), is( FontWeight.BOLD ) );
   }//End Method
   
   /**
    * Method to assert that two instructions do not concurrently interfere with each other.
    * @param threadOneInstruction the {@link Consumer} to accept the loop count and execute the first instruction.
    * @param threadTwoInstruction the {@link Consumer} to accept the loop count and execute the second instruction.
    * @param loopCounts the number of loops of each instruction to run.
    */
   public static final void assertConcurrencyIsNotAnIssue( 
            Consumer< Integer > threadOneInstruction, 
            Consumer< Integer > threadTwoInstruction,
            final int loopCounts
   ){
      CountDownLatch latch = new CountDownLatch( 2 );
      
      IntegerProperty firstCount = new SimpleIntegerProperty( 0 );
      IntegerProperty secondCount = new SimpleIntegerProperty( 0 );
      
      new Thread( () -> {
         for ( int i = 0; i < loopCounts; i++ ) {
            threadOneInstruction.accept( i );
            firstCount.set( firstCount.get() + 1 );
         }
         latch.countDown();
      } ).start();
      
      new Thread( () -> {
         for ( int i = 0; i < loopCounts; i++ ) {
            threadTwoInstruction.accept( i );
            secondCount.set( secondCount.get() + 1 );
         }
         latch.countDown();
      } ).start();
      
      try {
         latch.await( 10000, TimeUnit.MILLISECONDS );
      } catch ( InterruptedException e ) {
         fail( "Waiting on latch has failed." );
      }
      assertThat( firstCount.get(), is( loopCounts ) );
      assertThat( secondCount.get(), is( loopCounts ) );
   }//End Method

}//End Class
