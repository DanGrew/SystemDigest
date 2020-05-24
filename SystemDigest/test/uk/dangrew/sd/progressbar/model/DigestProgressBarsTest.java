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
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressImpl;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * {@link DigestProgressBars} test.
 */
public class DigestProgressBarsTest {
   
   private Source source;
   private Progress progress;
   private Message message;
   private ObjectDigest objectDigest;
   private DigestProgressBars systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() throws InterruptedException{
      source = new SourceImpl( this );
      objectDigest = new ObjectDigestImpl( source );
      MockitoAnnotations.initMocks( this );
      
      TestApplication.launch( () -> {
         systemUnderTest = new DigestProgressBars();
         return systemUnderTest;
      } );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      final Object object = new Object();
      for ( int i = 0; i < 101; i++ ) {
         final int j = i;
         PlatformImpl.runAndWait( () -> {
            systemUnderTest.handleProgress( new SourceImpl( this ), new ProgressImpl( j / 100.0 ), Messages.simpleMessage( "looping " + j ) );
            if ( j < 51 ) {
               systemUnderTest.handleProgress( new SourceImpl( object ), new ProgressImpl( j / 50.0 ), Messages.simpleMessage( "looping " + j ) );
            } else if ( j == 51 ){
               systemUnderTest.handleProgress( new SourceImpl( object ), Progresses.complete(), Messages.simpleMessage( "done" ) );
            }
            try {
               Thread.sleep( 100 );
            } catch ( Exception e ) {
               e.printStackTrace();
            }
         } );
      }
      PlatformImpl.runAndWait( () -> {
         systemUnderTest.handleProgress( new SourceImpl( this ), Progresses.complete(), Messages.simpleMessage( "done" ) );
      } );
      Thread.sleep( 1000000 );
   }//End Method
   
   @Test public void shouldBeEmptyInitially(){
      assertThat( systemUnderTest.progressLayout(), notNullValue() );
      assertThat( systemUnderTest.getChildren(), contains( systemUnderTest.progressLayout() ) );
      assertThat( systemUnderTest.progressLayout().getChildren(), empty() );
   }//End Method
   
   @Test public void shouldCreateProgressBarForDigest(){
      progress = new ProgressImpl( 45.5 );
      message = Messages.simpleMessage( "some special message" );
      
      objectDigest.progress( progress, message );
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 1 ) );
      
      DigestProgressBar progressBar = ( DigestProgressBar )systemUnderTest.progressLayout().getChildren().get( 0 );
      assertThat( progressBar.progressBar().getProgress(), is( Progress.percentageToProgress( progress.getPercentage() ) ) );
      assertThat( progressBar.messageLabel().getText(), is( DigestProgressBar.concatenateSourceAndMessage( source, message ) ) );
   }//End Method

   @Test public void shouldCreateMultipleProgressBarForDigest(){
      message = Messages.simpleMessage( "some special message" );
      progress = Progresses.simpleProgress( 22 );
      
      objectDigest.progress( progress, message );
      new ObjectDigestImpl( new SourceImpl( new Object() ) ).progress( progress, message );
      new ObjectDigestImpl( new SourceImpl( new Object() ) ).progress( progress, message );
      new ObjectDigestImpl( new SourceImpl( new Object() ) ).progress( progress, message );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 4 ) );
   }//End Method
   
   @Test public void shouldRemoveCompletedBars(){
      message = Messages.simpleMessage( "some special message" );
      progress = Progresses.simpleProgress( 22 );
      
      objectDigest.progress( progress, message );
      
      Source sourceA = new SourceImpl( new Object() );
      ObjectDigest objectDigestA = new ObjectDigestImpl( sourceA );
      objectDigestA.progress( progress, message );
      
      Source sourceB = new SourceImpl( new Object() );
      ObjectDigest objectDigestB = new ObjectDigestImpl( sourceB );
      objectDigestB.progress( progress, message );
      
      Source sourceC = new SourceImpl( new Object() );
      ObjectDigest objectDigestC = new ObjectDigestImpl( sourceC );
      objectDigestC.progress( progress, message );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 4 ) );
      
      DigestProgressBar objectDigestBar = systemUnderTest.digestProgressBar( source );
      assertThat( objectDigestBar, notNullValue() );
      DigestProgressBar objectDigestBarA = systemUnderTest.digestProgressBar( sourceA );
      assertThat( objectDigestBarA, notNullValue() );
      DigestProgressBar objectDigestBarB = systemUnderTest.digestProgressBar( sourceB );
      assertThat( objectDigestBarB, notNullValue() );
      DigestProgressBar objectDigestBarC = systemUnderTest.digestProgressBar( sourceC );
      assertThat( objectDigestBarC, notNullValue() );
      
      objectDigestA.progress( Progresses.complete(), message );
      objectDigestC.progress( Progresses.complete(), message );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 2 ) );
      
      assertThat( systemUnderTest.digestProgressBar( source ), is( objectDigestBar ) );
      assertThat( systemUnderTest.digestProgressBar( sourceA ), nullValue() );
      assertThat( systemUnderTest.digestProgressBar( sourceB ), is( objectDigestBarB ) );
      assertThat( systemUnderTest.digestProgressBar( sourceC ), nullValue() );
   }//End Method
   
   @Test public void shouldUpdateProgressOnEachBarIndividually(){
      message = Messages.simpleMessage( "some special message" );
      Progress progress = Progresses.simpleProgress( 22 );
      Progress progressA = Progresses.simpleProgress( 34.53 );
      Progress progressB = Progresses.simpleProgress( 0 );
      Progress progressC = Progresses.simpleProgress( 99 );
      
      objectDigest.progress( progress, message );
      
      Source sourceA = new SourceImpl( new Object() );
      ObjectDigest objectDigestA = new ObjectDigestImpl( sourceA );
      objectDigestA.progress( progressA, message );
      
      Source sourceB = new SourceImpl( new Object() );
      ObjectDigest objectDigestB = new ObjectDigestImpl( sourceB );
      objectDigestB.progress( progressB, message );
      
      Source sourceC = new SourceImpl( new Object() );
      ObjectDigest objectDigestC = new ObjectDigestImpl( sourceC );
      objectDigestC.progress( progressC, message );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 4 ) );
      
      assertThat( 
               systemUnderTest.digestProgressBar( source ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progress.getPercentage() ) ) 
      );
      assertThat( 
               systemUnderTest.digestProgressBar( sourceA ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressA.getPercentage() ) ) 
      );
      assertThat( 
               systemUnderTest.digestProgressBar( sourceB ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressB.getPercentage() ) ) 
      );
      assertThat(
               systemUnderTest.digestProgressBar( sourceC ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressC.getPercentage() ) ) 
      );
      
      progress = new ProgressImpl( 79.654 );
      objectDigest.progress( progress, message );
      
      PlatformImpl.runAndWait( () -> {} );
      assertThat( systemUnderTest.progressLayout().getChildren(), hasSize( 4 ) );
      
      assertThat( 
               systemUnderTest.digestProgressBar( source ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progress.getPercentage() ) ) 
      );
      assertThat( 
               systemUnderTest.digestProgressBar( sourceA ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressA.getPercentage() ) ) 
      );
      assertThat( 
               systemUnderTest.digestProgressBar( sourceB ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressB.getPercentage() ) ) 
      );
      assertThat( 
               systemUnderTest.digestProgressBar( sourceC ).progressBar().getProgress(), 
               is( Progress.percentageToProgress( progressC.getPercentage() ) ) 
      );
   }//End Method
   
}//End Class
