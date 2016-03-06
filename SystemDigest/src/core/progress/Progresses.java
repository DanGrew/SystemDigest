/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package core.progress;

/**
 * {@link Progresses} provides some default methods to handling {@link Progress} where
 * more default configurations are useful.
 */
public class Progresses {

   private static final Progress COMPLETE = new ProgressCompleteImpl();
   
   /**
    * Method to get an instance of a complete {@link Progress}.
    * @return the {@link Progress} representing completion.
    */
   public static Progress complete(){
      return COMPLETE;
   }//End Method

   /**
    * Method to construct a simple {@link ProgressImpl} with the given progress.
    * @param progress the progress as a percentage.
    * @return the {@link Progress}.
    */
   public static Progress simpleProgress( double progress ) {
      return new ProgressImpl( progress );
   }//End Method
   
}//End Class
