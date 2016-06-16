/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.progress;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.progress.ProgressCompleteImpl;
import uk.dangrew.sd.core.progress.Progresses;

/**
 * {@link Progresses} test.
 */
public class ProgressesTest {

   @Test public void shouldProvideCompleteProgress() {
      Progress progress = Progresses.complete();
      assertThat( progress, instanceOf( ProgressCompleteImpl.class ) );
      assertThat( Progresses.complete(), is( progress ) );
   }//End Method
   
   @Test public void shouldProvideSimpleProgress(){
      final double value = 4.234;
      Progress progress = Progresses.simpleProgress( value );
      assertThat( progress.getPercentage(), is( value ) );
      assertThat( progress.isComplete(), is( false ) );
   }//End Method

}//End Class
