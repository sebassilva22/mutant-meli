package com.meli.mutant.handler;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutant.dal.dao.IMutantDao;
import com.meli.mutant.dal.dao.MutantDaoImpl;
import com.meli.mutant.exception.DnaException;
import com.meli.mutant.model.GatewayResponse;
import com.meli.mutant.model.Statistics;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import static org.mockito.Matchers.any;

class StatsTest {

	Context context;

	IMutantDao mutantDao;

	private static Stats stats;
	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void init() {
		context = mock(Context.class);
		mutantDao = mock(MutantDaoImpl.class);
		
		stats = new Stats(mutantDao);

	}

	@Test
	public void handlerRequestStats() throws IOException, DnaException {
		InputStream inputStream = buildInputStream("src/test/resources/testMutant.json");
		OutputStream outputStream = buildOutputStream();
		Mockito.lenient().when(mutantDao.stats()).thenReturn(new Statistics(40,100,0.4));
		stats.handleRequest(inputStream, outputStream, context);
		GatewayResponseMetadataStats response = mapper.readValue(outputStream.toString(), GatewayResponseMetadataStats.class);
		System.out.println(response.getBody().toString());
		Assertions.assertEquals(100, response.getBody().getCountHumanDna());

	}

	@Test
	public void handlerRequest() throws IOException {
		stats = new Stats();
		Assertions.assertNotNull(stats);
	}

	@Test
	public void handlerRequestInvalidData() throws IOException, DnaException {
		InputStream inputStream = buildInputStream("src/test/resources/testMutantInvalidDna.json");
		OutputStream outputStream = buildOutputStream();
		Mockito.lenient().when(mutantDao.stats()).thenThrow(DnaException.class);
		Assertions.assertThrows(RuntimeException.class, () -> {
			stats.handleRequest(inputStream, outputStream, context);
		});
	}

	@Test
	public void calculateRatioTest() {
		Statistics s = new Statistics(40,100,0.4);
		s.calculateRatio();
		Assertions.assertEquals(0.4, s.getRatio());
	}
	

	@Test
	public void getStatsMutanDaoImplTest() throws DnaException {
		DynamoDbClient dynamoDb = mock(DynamoDbClient.class);
		Mockito.lenient().when(dynamoDb.scan(any(ScanRequest.class))).thenThrow(ResourceNotFoundException.class);
		MutantDaoImpl mutant = new MutantDaoImpl(dynamoDb, "Mutant-Meli");
		Assertions.assertThrows(DnaException.class, () -> {
			mutant.stats();
		});
	}
	
	private InputStream buildInputStream(String urlFile) throws FileNotFoundException {
		return new FileInputStream(new File(urlFile));
	}

	private OutputStream buildOutputStream() {
		return new OutputStreamT();
	}
}
