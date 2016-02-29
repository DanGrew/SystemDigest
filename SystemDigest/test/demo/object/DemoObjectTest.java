/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package demo.object;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.category.Categories;
import core.category.Category;
import core.lockdown.DigestMessageReceiver;
import core.lockdown.DigestMessageReceiverImpl;
import core.lockdown.DigestProgressReceiver;
import core.lockdown.DigestProgressReceiverImpl;
import core.message.Message;
import core.progress.Progress;
import core.source.SimpleSourceImpl;
import core.source.Source;

/**
 * {@link DemoObject} test.
 */
public class DemoObjectTest {
   
   @Mock private DigestMessageReceiver messageReceiver;
   @Mock private DigestProgressReceiver progressReceiver;
   @Captor private ArgumentCaptor< Source > sourceCaptor;
   @Captor private ArgumentCaptor< Category > categoryCaptor;
   @Captor private ArgumentCaptor< Progress > progressCaptor;
   @Captor private ArgumentCaptor< Message > messageCaptor;
   private DemoObject systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      new DigestMessageReceiverImpl( messageReceiver );
      new DigestProgressReceiverImpl( progressReceiver );
      systemUnderTest = new DemoObject();
   }//End Method

   @Test public void shouldRecordObjectAllocationOnConstruction() {
      verify( messageReceiver ).log( sourceCaptor.capture(), categoryCaptor.capture(), messageCaptor.capture() );
      assertThat( sourceCaptor.getValue(), is( new SimpleSourceImpl( systemUnderTest ) ) );
      assertThat( categoryCaptor.getValue(), is( Categories.objectAllocation() ) );
      assertThat( messageCaptor, notNullValue() );
   }//End Method
   
   @Test public void shouldRecordProgress(){
      systemUnderTest.processSomeInformation( 10 );
      verify( messageReceiver, times( 11 ) ).log( sourceCaptor.capture(), categoryCaptor.capture(), messageCaptor.capture() );
      verify( progressReceiver, times( 10 ) ).progress( sourceCaptor.capture(), progressCaptor.capture(), messageCaptor.capture() );
      //check construction
      assertThat( sourceCaptor.getAllValues().get( 0 ), is( new SimpleSourceImpl( systemUnderTest ) ) );
      assertThat( categoryCaptor.getAllValues().get( 0 ), is( Categories.objectAllocation() ) );
      assertThat( messageCaptor.getAllValues().get( 0 ), notNullValue() );
      
      //check category messages
      for ( int i = 1; i < 11; i++ ) {
         assertThat( sourceCaptor.getAllValues().get( i ), is( new SimpleSourceImpl( systemUnderTest ) ) );
         assertThat( categoryCaptor.getAllValues().get( i ), is( Categories.processingSequence() ) );
         assertThat( messageCaptor.getAllValues().get( i ), notNullValue() );
      }
      
      //check progress
      for ( int i = 0; i < 10; i++ ) {
         assertThat( sourceCaptor.getAllValues().get( i ), is( new SimpleSourceImpl( systemUnderTest ) ) );
         assertThat( progressCaptor.getAllValues().get( i ).getPercentage(), is( ( i + 1 ) * 10.0 ) );
         assertThat( messageCaptor.getAllValues().get( i ), notNullValue() );
      }
   }//End Method

}//End Class
