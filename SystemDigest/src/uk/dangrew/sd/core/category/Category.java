/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.category;

import javafx.scene.paint.Color;

/**
 * The {@link Category} provides an base class for the categories of message that
 * can be provided to the {@link uk.dangrew.sd.digest.object.ObjectDigest}.
 */
public abstract class Category {
   
   private final String name;
   private final Color colour;
   
   protected Category( String name, Color colour ) {
      this( new SystemWideCategoryEnablement().get(), name, colour, false );
   }//End Constructor

   protected Category( CategoryEnablement enablement, String name, Color colour, boolean enabled ) {
      this.name = name;
      this.colour = colour;
      enablement.register( this );
      enablement.setEnabled( this, enabled );
   }//End Constructor
   
   /**
    * Method to get the displayed name of the {@link Category}.
    * @return the {@link String} name.
    */
   public String getName() {
      return name;
   }//End Method

   /**
    * Method to get the {@link Color} associated with the {@link Category}.
    * @return the {@link Color}.
    */
   public Color getColour(){
      return colour;
   }//End Method

}//End Interface
