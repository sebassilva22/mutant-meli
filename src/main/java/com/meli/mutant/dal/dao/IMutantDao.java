package com.meli.mutant.dal.dao;

import com.meli.mutant.exception.DnaException;
import com.meli.mutant.model.Statistics;

public interface IMutantDao {
	
	void registerMutant(String dna, boolean isMutant) throws DnaException;
	Statistics stats() throws DnaException;
}
