package com.meli.mutant.evaluate;

import java.util.Arrays;
import java.util.regex.Pattern;


import com.meli.mutant.exception.DnaException;

public class EvaluateMutant {

    private String[][] matrix;
    private static final Pattern PATTERN = Pattern.compile("[^ATCG,\\[\\]]");
    private boolean horizontal;
    private boolean vertical;
    private boolean oblique;


    public boolean isMutant(String[] dna) throws DnaException{
        try {
    	if(!evaluateChainDna(dna)){
            this.matrix = arrayToMatrix(dna);
            horizontal();
            vertical();
            oblique();
        }else{
            throw new DnaException("Dna invalid");
        }
        }catch (Exception e) {
        	throw new DnaException("Dna invalid");
		}
        return horizontal || vertical || oblique;
    }

    private void horizontal() {
        for (int i = 0; i < matrix.length; i++) {
            // vamos hasta -3, porque ir mas alla no sirve porque no hay 4 elementos
            for (int j = 0; j < matrix[0].length - 3; j++) {
                if (matrix[i][j].equals(matrix[i][j + 1]) && matrix[i][j].equals(matrix[i][j + 2])
                        && matrix[i][j].equals(matrix[i][j + 3])) {
                    horizontal = true;
                }
            }
        }
    }

    private void vertical() {
        for (int i = 0; i < matrix.length - 3; i++) {
            
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].equals(matrix[i + 1][j]) && matrix[i][j].equals(matrix[i + 2][j])
                        && matrix[i][j].equals(matrix[i + 3][j])) {
                    vertical = true;

                }
            }
        }
    }

    private void oblique() {
        for (int i = 0; i < matrix.length - 3; i++) {
            
            for (int j = 0; j < matrix[0].length - 3; j++) {
                if (matrix[i][j].equals(matrix[i + 1][j + 1]) && matrix[i][j].equals(matrix[i + 2][j + 2])
                        && matrix[i][j].equals(matrix[i + 3][j + 3])) {
                    oblique = true;
                }
            }
        }
    }

    public String[][] arrayToMatrix(String[] array) {
        this.matrix = new String[array.length][array[0].length()];
        for (int i = 0; i < array.length; i++) {
            String[] split = array[i].split("");
            for (int j = 0; j < split.length; j++) {
                matrix[i][j] = split[j];
            }
        }
        return matrix;
    }

    private boolean evaluateChainDna(String[] array) {
        return PATTERN.matcher(Arrays.toString(array).trim().replaceAll(", ", ",")).find();
    }
    
    public String[][] getMatrix() {
		return this.matrix;
	}
}