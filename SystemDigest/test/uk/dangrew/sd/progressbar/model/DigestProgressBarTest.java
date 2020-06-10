/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.progressbar.model;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.ProgressImpl;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.utility.TestCommon;

/**
 * {@link DigestProgressBar} test.
 */
public class DigestProgressBarTest {

   private Source source;
   private DigestProgressBar systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() throws InterruptedException{
      TestApplication.launch( () -> {
         source = new SourceImpl( this );
         systemUnderTest = new DigestProgressBar( source );
         return systemUnderTest;
      } );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      for ( int i = 0; i < 101; i++ ) {
         final int j = i;
         JavaFxThreading.runAndWait( () -> {
            systemUnderTest.handleProgress( source, new ProgressImpl( j / 100.0 ), Messages.simpleMessage( "looping " + j ) );
            try {
               Thread.sleep( 100 );
            } catch ( Exception e ) {
               e.printStackTrace();
            }
         } );
      }
      JavaFxThreading.runAndWait( () -> {
         systemUnderTest.handleProgress( source, Progresses.complete(), Messages.simpleMessage( "done" ) );
      } );
      Thread.sleep( 1000000 );
   }//End Method
   
   @Test public void shouldHaveProgressBarAndLabel(){
      assertThat( 
               systemUnderTest.getChildren(), 
               contains( systemUnderTest.progressBar(), systemUnderTest.messageLabel() ) 
      );
   }//End Method
   
   @Test public void progressBarShouldExpandHeightAndWidth(){
      assertThat( systemUnderTest.progressBar().getMaxHeight(), is( Double.MAX_VALUE ) );
      assertThat( systemUnderTest.progressBar().getMaxWidth(), is( Double.MAX_VALUE ) );
   }//End Method
   
   @Test public void messageShouldExpandWidth(){
      assertThat( systemUnderTest.messageLabel().getMaxWidth(), is( Double.MAX_VALUE ) );
   }//End Method
   
   @Test public void messageShouldBeCentreAligned(){
      assertThat( systemUnderTest.messageLabel().getAlignment(), is( Pos.CENTER ) );
   }//End Method
      
   @Test public void messageShouldUseBoldFont(){
      TestCommon.assertFontBold( systemUnderTest.messageLabel().getFont() );
   }//End Method

   @Test public void shouldHaveColumnConstraintToStretchFully(){
      assertThat( systemUnderTest.getColumnConstraints(), hasSize( 1 ) );
      ColumnConstraints constraint = systemUnderTest.getColumnConstraints().get( 0 );
      assertThat( constraint.getPercentWidth(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldUpdateProgressBarAndLabel(){
      assertThat( systemUnderTest.messageLabel().getText(), isEmptyString() );
      assertThat( systemUnderTest.progressBar().getProgress(), is( -1.0 ) );
      
      final double progress = 45.34;
      final String message = "anything special to report";
      JavaFxThreading.runAndWait( () -> {
         systemUnderTest.handleProgress( source, Progresses.simpleProgress( progress ), Messages.simpleMessage( message ) );
      } );
      
      assertThat( 
               systemUnderTest.messageLabel().getText(), 
               is( DigestProgressBar.concatenateSourceAndMessage( new SourceImpl( this ), Messages.simpleMessage( message ) ) ) 
      );
      assertThat( systemUnderTest.progressBar().getProgress(), is( progress / 100.0 ) );
      
      assertThat( systemUnderTest.getText(), is( systemUnderTest.messageLabel().getText() ) );
      assertThat( systemUnderTest.getProgress(), is( systemUnderTest.progressBar().getProgress() ) );
   }//End Method
   
   @Test public void shouldConcatenateSourceAndMessage(){
      final String message = "anything";
      assertThat( 
               DigestProgressBar.concatenateSourceAndMessage( new SourceImpl( this ), Messages.simpleMessage( message ) ), 
               is( toString() + ": " + message ) 
      );
   }//End Method
   
   @Test public void shouldIgnoreMismatchingSource(){
      assertThat( systemUnderTest.messageLabel().getText(), isEmptyString() );
      assertThat( systemUnderTest.progressBar().getProgress(), is( -1.0 ) );
      
      final double progress = 45.34;
      final String message = "anything special to report";
      JavaFxThreading.runAndWait( () -> {
         systemUnderTest.handleProgress( new SourceImpl( new Object() ), Progresses.simpleProgress( progress ), Messages.simpleMessage( message ) );
      } );
      
      assertThat( systemUnderTest.messageLabel().getText(), isEmptyString() );
      assertThat( systemUnderTest.progressBar().getProgress(), is( -1.0 ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithSource(){
      assertThat( systemUnderTest.isAssociatedWith( source ), is( true ) );
      assertThat( systemUnderTest.isAssociatedWith( new SourceImpl( new Object() ) ), is( false ) );
   }//End Method

}//End Class
