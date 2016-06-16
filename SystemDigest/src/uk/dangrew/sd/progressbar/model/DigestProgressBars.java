/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.progressbar.model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link DigestProgressBars} provides an expandable view that constructs {@link DigestProgressBar}s as
 * more {@link Progress} is reported, and removes them as completed.
 */
public class DigestProgressBars extends BorderPane {
   
   private Map< Source, DigestProgressBar > sourceProgressBars;
   private VBox progressLayout;
   
   /**
    * Constructs a new {@link DigestProgressBars}.
    */
   public DigestProgressBars() {
      sourceProgressBars = new HashMap<>();
      new DigestProgressBarController( this );
      
      progressLayout = new VBox();
      setCenter( progressLayout );
   }//End Constructor
   
   /**
    * Method to handle {@link Progress} reported for any {@link Source}. A {@link DigestProgressBar} will
    * be created if not already or the progress updated. 
    * @param source the {@link Source} the {@link Progress} is for.
    * @param progress the {@link Progress}.
    * @param message the {@link Message} with the {@link Progress}.
    */
   void handleProgress( Source source, Progress progress, Message message ) {
      DigestProgressBar progressBar = sourceProgressBars.get( source );
      if ( progressBar == null ) {
         progressBar = new DigestProgressBar( source );
         sourceProgressBars.put( source, progressBar );
         progressLayout.getChildren().add( progressBar );
      }
      progressBar.handleProgress( progress, message );
      
      if ( progress.isComplete() ) {
         progressLayout.getChildren().remove( progressBar );
         sourceProgressBars.remove( source );
      }
   }//End Method

   VBox progressLayout() {
      return progressLayout;
   }//End Method

   /**
    * Getter for the {@link DigestProgressBar} associated with the given {@link Source}.
    * @param source the {@link Source} in question.
    * @return the {@link DigestProgressBar}.
    */
   DigestProgressBar digestProgressBar( Source source ) {
      return sourceProgressBars.get( source );
   }//End Method

}//End Class
