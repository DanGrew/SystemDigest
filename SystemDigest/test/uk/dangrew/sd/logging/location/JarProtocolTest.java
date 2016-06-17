/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.location;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

/**
 * {@link JarLocation} test.
 */
public class JarProtocolTest {

   protected static final String FILENAME = "any file name";
   protected static final String SUB_FOLDER = "some sub folder";
   
   @Captor private ArgumentCaptor< File > fileCaptor;
   private JarProtocol systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JarProtocol( null, FILENAME, getClass() );
   }//End Method
   
   @Test public void shouldProvideSourceToGivenFilename() {
      assertThat( systemUnderTest.getSource().getAbsolutePath(), endsWith( FILENAME ) );
   }//End Method
   
   @Test public void fileShouldBeLocatedOutsideOfProjectForExternalClass() {
      systemUnderTest = new JarProtocol( SUB_FOLDER, FILENAME, Assert.class );
      
      assertThat( systemUnderTest.getLocation(), endsWith( FILENAME ) );
      assertThat( systemUnderTest.getLocation(), containsString( "junit" ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullFilename(){
      new JarProtocol( null, null, getClass() );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullClass(){
      new JarProtocol( null, FILENAME, null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptClassWithNoCodeSource() {
      new JarProtocol( null, FILENAME, String.class );
   }//End Method
   
   @Test public void shouldLookForFileInSubFolder(){
      final String subFolder = "SubFolder";
      systemUnderTest = new JarProtocol( subFolder, FILENAME, Assert.class );
      
      assertThat( systemUnderTest.getLocation(), endsWith( subFolder + "/" + FILENAME ) );
      assertThat( systemUnderTest.getLocation(), containsString( "junit" ) );
   }//End Method
   
   @Test public void shouldProvideAbsolutePath(){
      systemUnderTest = new JarProtocol( "anything", FILENAME, getClass() );
      
      File thisLocation = new File( getClass().getProtectionDomain().getCodeSource().getLocation().getPath() );
      
      String fullLocation = systemUnderTest.getLocation();
      assertThat( fullLocation, containsString( thisLocation.getParentFile().getAbsolutePath() + "/anything/" + FILENAME ) );
   }//End Method
   
}//End Class
