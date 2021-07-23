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
import com.meli.mutant.model.Statistics;

import software.amazon.awssdk.utils.IoUtils;

public class Stats implements RequestStreamHandler {

	@Inject
	IMutantDao mutantDao;
	private final CommonsComponent commonsComponent;
	Map<String, String> APPLICATION_JSON = Collections.singletonMap("Content-Type", "application/json");
	int SC_OK = 200;

	public Stats() {
		commonsComponent = DaggerCommonsComponent.builder().build();
		commonsComponent.inject(this);
	}
	
	public Stats(IMutantDao mutantDao) {
		this.commonsComponent = null;
		this.mutantDao = mutantDao;
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
				Statistics statistics = mutantDao.stats();
				System.out.println(statistics.toString());
				output.write(mapper.writeValueAsBytes(new GatewayResponse<>(statistics, APPLICATION_JSON, SC_OK)));
		} catch (DnaException e) {
			throw new RuntimeException("400", e);

		}
	}

}