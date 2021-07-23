package com.meli.mutant.evaluate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.meli.mutant.exception.DnaException;


public class EvaluateMutantTest {
	
	EvaluateMutant evaluateMutant;
	
	@BeforeEach
	public void init() {
		evaluateMutant = new EvaluateMutant();
	}
	
	@Test
	public void isMutantTestOblique() throws DnaException {
		String[] dna = {"ACCCGA","CAGTGC","TTATGT","AGAATG","CCGCTA","TCACTG"};
		Assertions.assertTrue(evaluateMutant.isMutant(dna));
	}
	
	@Test
	public void isMutantTestHorizontal() throws DnaException {
		String[] dna = {"GCCCGA","CAGTGC","TTATGT","AGAATG","CCCCTA","TCACTG"};
		Assertions.assertTrue(evaluateMutant.isMutant(dna));
	}
	
	@Test
	public void isMutantTestVertical() throws DnaException {
		String[] dna = {"GCCCGA","CAGTGC","TTATGT","AGAAGG","CCGCTA","TCACTG"};
		Assertions.assertTrue(evaluateMutant.isMutant(dna));
	}
	
	@Test
	public void isMutantTestFail() throws DnaException {
		String[] dna = {"GCCCGA","CAGTGC","TTATCT","AGAAGG","CACCTA","TCACTG"};
		Assertions.assertFalse(evaluateMutant.isMutant(dna));
	}
	
	@Test
	public void isMutantTestDnaException() throws DnaException {
		String[] dna = {"ACCCGA","CAGTGC","THATGT","AGAAGG","CCCCTA","TCACTG"};
		Assertions.assertThrows(DnaException.class , () -> {
			evaluateMutant.isMutant(dna);
		});
	}
	
	@Test
	public void isMutantTestDnaExceptionNull() throws DnaException {
		String[] dna = null;
		Assertions.assertThrows(DnaException.class , () -> {
			evaluateMutant.isMutant(dna);
		});
	}

	
}
