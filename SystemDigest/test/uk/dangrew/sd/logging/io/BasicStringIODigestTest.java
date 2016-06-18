/*
 * ----------------------------------------
 *             System Digest
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link BasicStringIODigest} test.
 */
public class BasicStringIODigestTest {
   
   private static final String SOME_SPECIFIC_FILE_VALUE = "a specific message for the file";
   private static final String SOME_SPECIFIC_EXCEPTION_VALUE = "something else for the exception";
   
   @Mock private File file;
   @Mock private IOException exception;
   
   @Captor private ArgumentCaptor< LocalDateTime > timestampCaptor;
   @Captor private ArgumentCaptor< Source > sourceCaptor;
   @Captor private ArgumentCaptor< Category > categoryCaptor;
   @Captor private ArgumentCaptor< Message > messageCaptor;
   
   @Mock private BasicStringIO stringIO;
   @Mock private DigestMessageReceiver receiver;
   private BasicStringIODigest systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      new DigestMessageReceiverImpl( receiver );
      
      systemUnderTest = new BasicStringIODigest();
      systemUnderTest.attachSource( stringIO );
      
      when( file.getAbsolutePath() ).thenReturn( SOME_SPECIFIC_FILE_VALUE );
      when( exception.getMessage() ).thenReturn( SOME_SPECIFIC_EXCEPTION_VALUE );
   }//End Method
   
   @Test public void shouldReportFailedFileSetup() {
      systemUnderTest.failedToSetupFiles( file, exception );
      
      verify( receiver ).log( 
               timestampCaptor.capture(), sourceCaptor.capture(), categoryCaptor.capture(), messageCaptor.capture() 
      );
      
      assertThat( timestampCaptor.getValue(), is( not( nullValue() ) ) );
      assertThat( sourceCaptor.getValue().getIdentifier(), is( BasicStringIODigest.BASIC_STRING_IO_NAME ) );
      assertThat( categoryCaptor.getValue(), is( Categories.error() ) );
      assertThat( 
               messageCaptor.getValue().getMessage(), 
               is( BasicStringIODigest.format( BasicStringIODigest.FAILED_TO_SETUP, file, exception ) ) 
      );
   }//End Method
   
   @Test public void shouldReportFailedFileWrite() {
      systemUnderTest.failedToWriteToFile( file, exception );
      
      verify( receiver ).log( 
               timestampCaptor.capture(), sourceCaptor.capture(), categoryCaptor.capture(), messageCaptor.capture() 
      );
      
      assertThat( timestampCaptor.getValue(), is( not( nullValue() ) ) );
      assertThat( sourceCaptor.getValue().getIdentifier(), is( BasicStringIODigest.BASIC_STRING_IO_NAME ) );
      assertThat( categoryCaptor.getValue(), is( Categories.error() ) );
      assertThat( 
               messageCaptor.getValue().getMessage(), 
               is( BasicStringIODigest.format( BasicStringIODigest.FAILED_TO_WRITE, file, exception ) ) 
      );
   }//End Method
   
   @Test public void shouldFormatFailureFileAndException(){
      assertThat( BasicStringIODigest.format( "some reason", file, exception ), is( 
               "some reason\nFile: a specific message for the file\nException: something else for the exception"
      ) );
   }//End Method
   
   @Test public void shouldFormatFileAndException(){
      assertThat( BasicStringIODigest.format( file, exception ), is( 
               "File: a specific message for the file\nException: something else for the exception"
      ) );
   }//End Method
   
   @Test public void failedSetupShouldIgnoreNullFile(){
      assertThat( BasicStringIODigest.format( null, exception ), is( 
               "File: File not available\nException: something else for the exception"
      ) );
   }//End Method
   
   @Test public void failedSetupShouldIgnoreFileWithNoPath(){
      when( file.getAbsolutePath() ).thenReturn( null );
      assertThat( BasicStringIODigest.format( file, exception ), is( 
               "File: File not available\nException: something else for the exception"
      ) );
   }//End Method
}//End Class
