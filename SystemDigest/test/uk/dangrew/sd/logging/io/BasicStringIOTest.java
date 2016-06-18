/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.io;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;

/**
 * {@link BasicStringIO} test,
 */
@RunWith( JUnitParamsRunner.class )
public class BasicStringIOTest {

   private static final String EXISITNG_FILE = "existing-file.txt";
   private static final String POPULATING_FILE = "populating-file.txt";
   private static final String SUB_FOLDER_FILE = "testing/populating-file.txt";
   
   @Mock private BasicStringIODigest digest;
   private String object;
   private BasicStringIO systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      
      object = "i need to write this something to file";
      systemUnderTest = new BasicStringIO( digest );
      
      File file = constructFileFor( POPULATING_FILE );
      if ( file.exists() ) {
         file.delete();
      }
      
      File subfolderFile = constructFileFor( SUB_FOLDER_FILE );
      if ( subfolderFile.exists() ) {
         subfolderFile.delete();
      }
   }//End Method
   
   @Test public void shouldParseTestableFile() {
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      
      object = systemUnderTest.read( file );
      assertThat( object, is( not( nullValue() ) ) );
      assertThat( object, is( "this something was here to begin with" ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void readShouldNotAcceptNullFile(){
      systemUnderTest.read( null );
   }//End Method
   
   @Test public void readShouldIgnoreNonExistentFile(){
      assertThat( systemUnderTest.read( new File( "something that does not exist" ) ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void writeShouldNotAcceptNullFile(){
      systemUnderTest.write( null, object, false );
   }//End Method
   
   @Test public void writeShouldNotAcceptNullJsonObject(){
      final File file = mock( File.class );
      try {
         assertThat( systemUnderTest.write( file, null, false ), is( false ) );
         fail( "No null pointer exception thrown." );
      } catch ( NullPointerException npe ) {
         verifyZeroInteractions( file );
      }
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void writeShouldConstructNonExistentFile( String file ){
      assertThat( BasicStringIOTest.class.getResource( file ), is( nullValue() ) );
      shouldWriteTestableFile( file );
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void shouldWriteTestableFile( String filename ) {
      String output = "@Test public void shouldWriteTestableFile( String filename ) {";
      
      final File file = constructFileFor( filename );
      assertThat( systemUnderTest.write( file, output, false ), is( true ) );
      
      assertThat( file.exists(), is( true ) );
      object = systemUnderTest.read( file );
      assertThat( object.toString(), is( output.toString() ) );
   }//End Method
   
   @Test public void shouldCatchNullStringWhenReadAndAvoidExceptions(){
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      
      //this should not happen because file issues are handled before - spying to make sure
      systemUnderTest = spy( systemUnderTest );
      when( systemUnderTest.readFileIntoString( file ) ).thenReturn( null );
      
      assertThat( systemUnderTest.read( file ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void readScannerShouldClose(){
      Scanner scanner = new Scanner( new StringReader( "anything" ) );
      systemUnderTest.readScannerContentAndClose( scanner );
      scanner.next();
   }//End Method
   
   @Test public void readFileIntoStringShouldHandleIoExceptionsEvenThoughDefendedAgainst(){
      systemUnderTest = spy( systemUnderTest );
      doAnswer( invocation -> { throw new IOException(); } ).when( systemUnderTest ).readScannerContentAndClose( Mockito.any() );
      
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      assertThat( systemUnderTest.readFileIntoString( file ), is( nullValue() ) );
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void writeShouldUseFileThatAlreadyExists( String filename ) throws IOException{
      final File file = constructFileFor( filename );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( false ) );
      
      file.getParentFile().mkdirs();
      file.createNewFile();
      
      shouldWriteTestableFile( filename );
   }//End Method
   
   @Test public void writeShouldFailGracefullyIfFileCannotBeCreated() throws IOException{
      File file = mock( File.class );
      when( file.exists() ).thenReturn( false );
      when( file.createNewFile() ).thenThrow( new IOException() );
      
      assertThat( systemUnderTest.write( file, object, false ), is( false ) );
      verify( digest ).failedToSetupFiles( Mockito.eq( file ), Mockito.any() );
   }//End Method
   
   @Test public void writeShouldFailGracefullyIfFileWriterCannotBeCreated() throws IOException{
      File file = mock( File.class );
      when( file.exists() ).thenReturn( true );
      doAnswer( invocation -> { throw new IOException(); } ).when( file ).getPath();
      
      assertThat( systemUnderTest.write( file, object, false ), is( false ) );
      verify( digest ).failedToWriteToFile( Mockito.eq( file ), Mockito.any() );
   }//End Method
   
   /**
    * Method to construct the {@link File} for the given filename in the current package.
    * @param fileName the filename to construct for.
    * @return the {@link File}, not necessarily one that exists.
    */
   private File constructFileFor( String fileName ) {
      URL knownResource = BasicStringIOTest.class.getResource( EXISITNG_FILE );
      if ( knownResource == null ) {
         fail( EXISITNG_FILE + " shold be present but cannot be found." );
      }
      
      String knownResourcePath = knownResource.getFile();
      if ( !knownResourcePath.contains( fileName ) ) {
         knownResourcePath = knownResourcePath.replace( EXISITNG_FILE, fileName );
      }
      
      return new File( knownResourcePath );
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void shouldAppendWritesToTestableFile( String filename ) {
      String logA = "first thing logged";
      String logB = "another logging";
      String logC = "a catastrophe";
      
      final File file = constructFileFor( filename );
      assertThat( systemUnderTest.write( file, logA, true ), is( true ) );
      assertThat( systemUnderTest.write( file, logB, true ), is( true ) );
      assertThat( systemUnderTest.write( file, logC, true ), is( true ) );
      
      assertThat( file.exists(), is( true ) );
      object = systemUnderTest.read( file );
      assertThat( object.toString(), is( logA + logB + logC ) );
   }//End Method
   
   @Test public void publicConstructorShouldProvideDigest(){
      DigestMessageReceiver receiver = mock( DigestMessageReceiver.class );
      new DigestMessageReceiverImpl( receiver );
      
      systemUnderTest = new BasicStringIO();
      
      File file = mock( File.class );
      when( file.exists() ).thenReturn( true );
      doAnswer( invocation -> { throw new IOException(); } ).when( file ).getPath();
      
      assertThat( systemUnderTest.write( file, object, false ), is( false ) );
      verify( receiver ).log( Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any() );
   }//End Method
   
   @Test public void shouldAttachSourceToDigest(){
      verify( digest ).attachSource( systemUnderTest );
   }//End Method
   
}//End Class
