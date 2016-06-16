/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.digest.object;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestConnector;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * {@link ObjectDigestImpl} test.
 */
public class ObjectDigestImplTest {

   private ObjectDigestImpl systemUnderTest;
   
   @Mock private DigestConnector connector;
   @Mock private Source source;
   @Mock private Category category;
   @Mock private Progress progress;
   @Mock private Message message;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ObjectDigestImpl( source, connector );
   }//End Method
   
   @Test public void shouldForwardLogToDigestManager(){
      systemUnderTest.log( category, message );
      verify( connector ).log( source, category, message );
   }//End Method
   
   @Test public void shouldForwardProgressToDigestManager(){
      systemUnderTest.progress( progress, message );
      verify( connector ).progress( source, progress, message );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullSource(){
      Source source = null;
      systemUnderTest = new ObjectDigestImpl( source );
   }//End Method
   
   @Test public void shouldAttachSource(){
      Source alternateSource = mock( Source.class );
      systemUnderTest.attachSource( alternateSource );
      systemUnderTest.log( category, message );
      verify( connector ).log( alternateSource, category, message ); 
      systemUnderTest.progress( progress, message );
      verify( connector ).progress( alternateSource, progress, message );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) 
   public void shouldAllowConstructionWithConnectorOnlyButEnforceSourceAttachedForLog(){
      systemUnderTest = new ObjectDigestImpl();
      systemUnderTest.log( category, message );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) 
   public void shouldAllowConstructionWithConnectorOnlyButEnforceSourceAttachedForProgress(){
      systemUnderTest = new ObjectDigestImpl();
      systemUnderTest.progress( progress, message );
   }//End Method

}//End Class
