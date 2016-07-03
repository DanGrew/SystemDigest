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
 * The {@link LoggingLocationProtocol} is responsible for defining the interface that
 * should be implemented when providing a method of logging digest output to a {@link java.io.File}.
 */
public interface LoggingLocationProtocol extends FileLocationProtocol {
   
   /**
    * Method to instruct the mechanism to log the given line to the {@link java.io.File}
    * associated by appending.
    * @param logLine the {@link String} line to log.
    * @return true if successful.
    */
   public boolean logToLocation( String logLine );
   
   /**
    * Method to set the {@link java.io.File} size limit. Default is unlimited, logging will happen
    * no matter what the size is. 
    * @param bytes the number of bytes in size the file can be, if greater than this, it should be
    * written over.
    */
   public void setFileSizeLimit( Long bytes );
   
   /**
    * Getter for the current {@link java.io.File} size limit.
    * @return the limit in bytes.
    */
   public Long getFileSizeLimit();
   
}//End Interface
