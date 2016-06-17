/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.logging.location;

/**
 * The {@link FileLocationProtocol} is responsible for defining the location 
 * of a {@link java.io.File} that can be read from and written to.
 */
public interface FileLocationProtocol {
   
   /**
    * Method to get the absolute path of the {@link java.io.File} location.
    * @return the {@link String} location.
    */
   public String getLocation();

}//End Interface
