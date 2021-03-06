/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.progressbar.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link DigestProgressBar} provides a simple {@link ProgressBar} and {@link Label}
 * that show the current state of progress that has been sent to the system digest.
 */
public class DigestProgressBar extends GridPane {
   
   private final Source source;
   private final ProgressBar progressBar;
   private final Label messageLabel;
   
   /**
    * Constructs a new {@link DigestProgressBar}.
    * @param source the {@link Source} associated with the progress.
    */
   public DigestProgressBar( Source source ) {
      this.source = source;
      
      progressBar = new ProgressBar();
      progressBar.setMaxWidth( Double.MAX_VALUE );
      progressBar.setMaxHeight( Double.MAX_VALUE );
      add( progressBar, 0, 0 );
      
      messageLabel = new Label();
      messageLabel.setAlignment( Pos.CENTER );
      messageLabel.setMaxWidth( Double.MAX_VALUE );
      Font defaultFont = Font.getDefault();
      messageLabel.setFont( Font.font( defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize() ) );
      add( messageLabel, 0, 1 );
      
      ColumnConstraints columnConstraint = new ColumnConstraints();
      columnConstraint.setPercentWidth( 100 );
      getColumnConstraints().add( columnConstraint );
   }//End Constructor
   
   /**
    * Method to handle some {@link Progress} with a {@link Message} for the associated {@link Source}.
    * @param source the {@link Source} provided with the progress, to be filtered against.
    * @param progress the {@link Progress} provided.
    * @param message the {@link Message} associated.
    */
   public void handleProgress( Source source, Progress progress, Message message ) {
      if ( !source.equals( this.source ) ) {
         return;
      }
      JavaFxThreading.runAndWait( () -> {
         progressBar.setProgress( Progress.percentageToProgress( progress.getPercentage() ) );
         messageLabel.setText( concatenateSourceAndMessage( source, message ) );
      } );
   }//End Method
   
   /**
    * Getter for the current progress percentage directly from the {@link ProgressBar}.
    * @return the progress.
    */
   public double getProgress(){
      return progressBar.getProgress();
   }//End Method
   
   /**
    * Getter for the text associated with progress.
    * @return the text associated.
    */
   public String getText(){
      return messageLabel.getText();
   }//End Method
   
   /**
    * Method to determine whether this is associated with the given.
    * @param source the {@link Source} in question.
    * @return true if identical.
    */
   public boolean isAssociatedWith( Source source ) {
      return this.source.equals( source );
   }//End Method
   
   /**
    * Method to concatenate the {@link Source} and {@link Message} into a readable update.
    * @param source the {@link Source}.
    * @param message the {@link Message}.
    * @return a readable {@link String}.
    */
   static String concatenateSourceAndMessage( Source source, Message message ) {
      return source.getIdentifier() + ": " + message.getMessage();
   }//End Method

   ProgressBar progressBar() {
      return progressBar;
   }//End Method

   Label messageLabel() {
      return messageLabel;
   }//End Method

}//End Class
