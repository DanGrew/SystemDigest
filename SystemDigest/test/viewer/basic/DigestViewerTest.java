/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package viewer.basic;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import core.category.Categories;
import core.message.Messages;
import core.progress.ProgressImpl;
import core.progress.Progresses;
import core.source.SourceImpl;
import digest.object.ObjectDigest;
import digest.object.ObjectDigestImpl;
import graphics.launch.TestApplication;
import progressbar.model.DigestProgressBars;
import table.model.DigestTable;

/**
 * {@link DigestViewer} test.
 */
public class DigestViewerTest {

   private static final double WIDTH = 432;
   private static final double HEIGHT = 987;
   private ObjectDigest thisObjectDigest;
   private DigestViewer systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() throws InterruptedException{
      thisObjectDigest = new ObjectDigestImpl( new SourceImpl( this ) );
      
      TestApplication.launch( () -> { 
         systemUnderTest = new DigestViewer( WIDTH, HEIGHT );
         return systemUnderTest;
      } );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      thisObjectDigest.log( Categories.processingSequence(), Messages.simpleMessage( "Starting processing" ) );
      for ( int i = 0; i < 101; i++ ) {
         final int j = i;
            thisObjectDigest.progress( new ProgressImpl( j / 100.0 ), Messages.simpleMessage( "looping " + j ) );
            
            if ( j == 34 || j == 65 || j == 89 ) {
               ObjectDigest anotherObjectDigest = new ObjectDigestImpl( new SourceImpl( new Object() ) );
               for ( int k = 0; k < 51; k++ ) {
                  final int l = k;
                  anotherObjectDigest.progress( new ProgressImpl( l / 50.0 ), Messages.simpleMessage( "sub processing " + l ) );
                  try {
                     Thread.sleep( 100 );
                  } catch ( Exception e ) {
                     e.printStackTrace();
                  }
               }
               anotherObjectDigest.progress( Progresses.complete(), Messages.simpleMessage( "completed sub process" ) );
            }
            try {
               Thread.sleep( 100 );
            } catch ( Exception e ) {
               e.printStackTrace();
            }
      }
      PlatformImpl.runAndWait( () -> {
         thisObjectDigest.progress( Progresses.complete(), Messages.simpleMessage( "done" ) );
      } );
      thisObjectDigest.log( Categories.processingSequence(), Messages.simpleMessage( "Completed processing" ) );
      
      while ( true ) {
         thisObjectDigest.log( Categories.objectAllocation(), Messages.simpleMessage( "anything" ) );
         Thread.sleep( 100 );
      }
   }//End Method

   @Test public void shouldHaveCenterProgressBars(){
      assertThat( systemUnderTest.getCenter(), instanceOf( DigestProgressBars.class ) );
   }//End Method
   
   @Test public void shouldHaveTableAtBottom() {
      assertThat( systemUnderTest.getBottom(), instanceOf( DigestTable.class ) );
   }//End Method
   
   @Test public void tableShouldHaveGivenPreferredWidthAndHeight(){
      DigestTable table = ( DigestTable )systemUnderTest.getBottom();
      assertThat( table.getPrefWidth(), is( WIDTH ) );
      assertThat( table.getPrefHeight(), is( HEIGHT ) );
   }//End Method
}//End Class
