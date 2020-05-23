/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.viewer.basic;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.ProgressImpl;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigest;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;
import uk.dangrew.sd.progressbar.model.DigestProgressBars;
import uk.dangrew.sd.table.model.DigestTable;
import uk.dangrew.sd.table.presentation.DigestTableRowLimit;

/**
 * {@link DigestViewer} test.
 */
public class DigestViewerTest {

   private static final double WIDTH = 432;
   private static final double HEIGHT = 987;
   @Mock private DigestTable table; 
   private ObjectDigest thisObjectDigest;
   private DigestViewer systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() throws InterruptedException{
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
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
   
   @Test public void shouldApplyTableRowLimit(){
      systemUnderTest = new DigestViewer( table, WIDTH, HEIGHT );
      systemUnderTest.setTableRowLimit( DigestTableRowLimit.TenThousand );
      verify( table ).setRowLimit( DigestTableRowLimit.TenThousand );
   }//End Method
}//End Class
