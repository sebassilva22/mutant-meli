package com.meli.mutant.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutant.commons.configuration.CommonsComponent;
import com.meli.mutant.commons.configuration.DaggerCommonsComponent;
import com.meli.mutant.dal.dao.IMutantDao;
import com.meli.mutant.evaluate.EvaluateMutant;
import com.meli.mutant.exception.DnaException;
import com.meli.mutant.model.GatewayResponse;

import software.amazon.awssdk.utils.IoUtils;

public class Mutant implements RequestStreamHandler {

	@Inject
	IMutantDao mutantDao;
	private final CommonsComponent commonsComponent;
	Map<String, String> APPLICATION_JSON = Collections.singletonMap("Content-Type", "application/json");
	int SC_OK = 200;

	public Mutant() {
		commonsComponent = DaggerCommonsComponent.builder().build();
		commonsComponent.inject(this);
	}
	
	public Mutant(IMutantDao mutantDao) {
		this.commonsComponent = null;
		this.mutantDao = mutantDao;
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		String text = IoUtils.toUtf8String(input);
		System.out.println(text);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<String>> map = mapper.readValue(text, Map.class);
		String[] dna = map.get("dna").toArray(new String[0]);
		EvaluateMutant evaluate = new EvaluateMutant();
		try {
			if (evaluate.isMutant(dna)) {
				mutantDao.registerMutant(mapper.writeValueAsString(evaluate.getMatrix()), true);
				output.write(mapper.writeValueAsBytes(new GatewayResponse<>("true", APPLICATION_JSON, SC_OK)));
			} else {
				mutantDao.registerMutant(mapper.writeValueAsString(evaluate.getMatrix()), false);
				throw new RuntimeException("403", new DnaException("false"));
			}

		} catch (DnaException e) {
			throw new RuntimeException("400", e);

		}
	}

}