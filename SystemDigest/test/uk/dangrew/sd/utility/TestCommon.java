/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.utility;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * {@link TestCommon} privides some common, global test utilities.
 */
public class TestCommon {
   
   /**
    * Method to assert that the given {@link Font} is bold.
    * @param font the {@link Font} to assert.
    */
   public static void assertFontBold( Font font ){
      assertThat( FontWeight.findByName( font.getStyle() ), is( FontWeight.BOLD ) );
   }//End Method

}//End Class
