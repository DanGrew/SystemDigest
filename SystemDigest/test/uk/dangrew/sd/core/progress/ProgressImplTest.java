/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.sd.core.progress;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressImpl;

/**
 * {@link ProgressImpl} test.
 */
public class ProgressImplTest {

   @Test public void shouldProvideBasicProgressPercentage() {
      final double percentage = 56.8;
      Progress progress = new ProgressImpl( percentage );
      assertThat( progress.getPercentage(), is( percentage ) );
      assertThat( progress.isComplete(), is( false ) );
   }//End Method
   
   @Test public void shouldBeEqualTo(){
      assertThat( new ProgressImpl( 0 ), is( new ProgressImpl( 0 ) ) );
      assertThat( new ProgressImpl( 5.359276395 ), is( new ProgressImpl( 5.359276395 ) ) );
      assertThat( new ProgressImpl( 1000 ), is( new ProgressImpl( 1000 ) ) );
      Progress progress = new ProgressImpl( 1000 );
      assertThat( progress, is( progress ) );
   }//End Method
   
   @Test public void shouldHashTo(){
      assertThat( new ProgressImpl( 0 ).hashCode(), is( new ProgressImpl( 0 ).hashCode() ) );
      assertThat( new ProgressImpl( 5.359276395 ).hashCode(), is( new ProgressImpl( 5.359276395 ).hashCode() ) );
      assertThat( new ProgressImpl( 1000 ).hashCode(), is( new ProgressImpl( 1000 ).hashCode() ) );
   }//End Method
   
   @Test public void shouldNotBeEqualTo(){
      assertThat( new ProgressImpl( 0 ), is( not( new ProgressImpl( 10 ) ) ) );
      assertThat( new ProgressImpl( 5.359276396 ), is( not( new ProgressImpl( 5.359276395 ) ) ) );
      assertThat( new ProgressImpl( 1000 ), is( not( new ProgressImpl( 1001 ) ) ) );
      assertThat( new ProgressImpl( 1000 ), is( not( new Object() ) ) );
      assertThat( new ProgressImpl( 1000 ).equals( null ), is( not( true ) ) );
   }//End Method
   
   @Test public void shouldNotHashTo(){
      assertThat( new ProgressImpl( 0 ).hashCode(), is( not( new ProgressImpl( 10 ).hashCode() ) ) );
      assertThat( new ProgressImpl( 5.359276396 ).hashCode(), is( not( new ProgressImpl( 5.359276395 ).hashCode() ) ) );
      assertThat( new ProgressImpl( 1000 ).hashCode(), is( not( new ProgressImpl( 1001 ).hashCode() ) ) );
   }//End Method

}//End Class
