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
public class NoConectionDataBaseException extends Exception{
    // Parameterless Constructor
      public NoConectionDataBaseException() {}

      // Constructor that accepts a message
      public NoConectionDataBaseException(String message)
      {
         super(message);
      }
}
