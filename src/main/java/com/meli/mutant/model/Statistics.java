package com.meli.mutant.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Statistics {
	
	@JsonProperty("count_mutant_dna")
	private Integer countMutantDna;
	@JsonProperty("count_human_dna")
	private Integer countHumanDna;
	private double ratio;
	
	public Statistics() {
		
	}
	
	/**
	 * @param countMutantDna
	 * @param countHumanDna
	 * @param ratio
	 */
	public Statistics(int countMutantDna, int countHumanDna, double ratio) {
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ratio = ratio;
	}

	public void calculateRatio() {
		ratio =  countMutantDna.doubleValue() / countHumanDna.doubleValue();
	}
	
	public int getCountMutantDna() {
		return countMutantDna;
	}

	public void setCountMutantDna(int countMutantDna) {
		this.countMutantDna = countMutantDna;
	}

	public int getCountHumanDna() {
		return countHumanDna;
	}

	public void setCountHumanDna(int countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return (countMutantDna != null ? "countMutantDna=" + countMutantDna + ", " : "") + (countHumanDna != null ? "countHumanDna=" + countHumanDna + ", " : "") + "ratio=" + ratio;
	}


	
	
}
