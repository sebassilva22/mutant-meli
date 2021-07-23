package com.meli.mutant;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.meli.mutant.evaluate.EvaluateMutant;
import com.meli.mutant.exception.DnaException;


public class App 
{
    private static Logger logger =  Logger.getLogger(App.class.getName());
    
    public static void main( String[] args ) throws DnaException{
        logger.setLevel(Level.ALL);
        EvaluateMutant evaluateMutant =  new EvaluateMutant();
        String[] dna = {"ACCCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        
        if(args != null && args.length > 0) {
        	dna = args;
        }
        try {
            logger.log(Level.INFO , "Is mutant: {0}",evaluateMutant.isMutant(dna));
        } catch (DnaException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw e;
        }
        
    }
}
