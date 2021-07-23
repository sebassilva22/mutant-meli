package com.meli.mutant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.meli.mutant.exception.DnaException;

class AppTest {

	@Test
	public void mainNullArgs() throws DnaException {
		App.main(null);
		Assertions.assertTrue(true);
	}
	
	@Test
	public void mainTestException() {
		String[] dna = {"AHCCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		Assertions.assertThrows(DnaException.class, () -> {
			App.main(dna);
		});
		
	}
}
