/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

/**
 *
 * @author Daniel
 */
public class NoTypeRequiredException extends Exception{
    // Parameterless Constructor
      public NoTypeRequiredException() {}

      // Constructor that accepts a message
      public NoTypeRequiredException(String message)
      {
         super(message);
      }
}
