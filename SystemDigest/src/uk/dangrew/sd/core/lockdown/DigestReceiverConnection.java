/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.sd.core.lockdown;

/**
 * The {@link DigestReceiver} provides a base interface for any objects receiving
 * information from the system digest.
 */
public interface DigestReceiverConnection {
   
   /** 
    * Method to disconnect this {@link DigestReceiver} from the system digest.
    * Safe to call multiple times.
    */
   public void disconnect();
   
   /**
    * Method to connect this {@link DigestReceiver} to the system digest.
    * Safe to call multiple times.
    */
   public void connect();
   
   /**
    * Method to determine whether the receiver is connected.
    * @return true if connected.
    */
   public boolean isConnected();

}//End Interface
