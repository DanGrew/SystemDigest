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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.logging.io.BasicStringIO;

/**
 * {@link JarLoggingProtocol} test.
 */
public class JarLoggingProtocolTest {
   
   @Mock private BasicStringIO stringIO;
   @Mock private JarProtocol protocol;
   private JarLoggingProtocol systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JarLoggingProtocol( stringIO, protocol );
   }//End Method

   @Test public void shouldForwardLogCallOntoIoAppendToSource() {
      File file = mock( File.class );
      when( protocol.getSource() ).thenReturn( file );
      
      final String valueToLog = "something speicific to log";
      systemUnderTest.logToLocation( valueToLog );
      
      verify( stringIO ).write( file, valueToLog, true );
   }//End Method
   
   @Test public void logLocationShouldReturnResultOfRead(){
      when( stringIO.write( Mockito.any(), Mockito.any(), Mockito.anyBoolean() ) ).thenReturn( true );
      assertThat( systemUnderTest.logToLocation( null ), is( true ) );
      
      when( stringIO.write( Mockito.any(), Mockito.any(), Mockito.anyBoolean() ) ).thenReturn( false );
      assertThat( systemUnderTest.logToLocation( null ), is( false ) );
      
      when( stringIO.write( Mockito.any(), Mockito.any(), Mockito.anyBoolean() ) ).thenReturn( true );
      assertThat( systemUnderTest.logToLocation( null ), is( true ) );
   }//End Method
   
   @Test public void shouldProvideLocationFromAssociatedProtocol() {
      final String location = "anything to prove the point";
      when( protocol.getLocation() ).thenReturn( location );
      
      assertThat( systemUnderTest.getLocation(), is( location ) );
   }//End Method
   
   @Test public void shouldConstructWithoutSubFolder(){
      systemUnderTest = new JarLoggingProtocol( "anything", getClass() );
      assertThat( systemUnderTest.getLocation(), containsString( "anything" ) );
   }//End Method
   
   @Test public void shouldConstructWithSubFolder(){
      systemUnderTest = new JarLoggingProtocol( "sub", "anything", getClass() );
      assertThat( systemUnderTest.getLocation(), containsString( "sub/anything" ) );
   }//End Method
   
   @Test public void shouldNotAppendIfFileSizeIsGreaterThanLimit(){
      systemUnderTest.setFileSizeLimit( 1000L );
      
      File file = mock( File.class );
      when( protocol.getSource() ).thenReturn( file );
      when( file.length() ).thenReturn( 1001L );
      
      systemUnderTest.logToLocation( "anything" );
      verify( stringIO ).write( file, "anything", false );
   }//End Method
   
   @Test public void shouldAppendIfFileSizeIsLimit(){
      systemUnderTest.setFileSizeLimit( 1000L );
      
      File file = mock( File.class );
      when( protocol.getSource() ).thenReturn( file );
      when( file.length() ).thenReturn( 1000L );
      
      systemUnderTest.logToLocation( "anything" );
      verify( stringIO ).write( file, "anything", true );
   }//End Method
   
   @Test public void shouldAppendIndefintelyByDefault(){
      File file = mock( File.class );
      when( protocol.getSource() ).thenReturn( file );
      when( file.length() ).thenReturn( Long.MAX_VALUE );
      
      systemUnderTest.logToLocation( "anything" );
      verify( stringIO ).write( file, "anything", true );
   }//End Method
   
}//End Class
