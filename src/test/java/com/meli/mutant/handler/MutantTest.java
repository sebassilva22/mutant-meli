package com.meli.mutant.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutant.dal.dao.IMutantDao;
import com.meli.mutant.dal.dao.MutantDaoImpl;
import com.meli.mutant.exception.DnaException;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;


class MutantTest {

	Context context;

	IMutantDao mutantDao;
	private Mutant mutant;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@BeforeEach
	void init() {
		context = mock(Context.class);
		mutantDao = mock(MutantDaoImpl.class);
		mutant = new Mutant(mutantDao);
	}

	@Test
	public void handlerRequestIsMutant() throws IOException {
		
		InputStream inputStream = buildInputStream("src/test/resources/testMutant.json");
		OutputStream outputStream = buildOutputStream();
		mutant.handleRequest(inputStream, outputStream, context);
		GatewayResponseMetadata response = mapper.readValue(outputStream.toString(), GatewayResponseMetadata.class);
		Assertions.assertEquals("true", response.getBody());
	}

	@Test
	public void handlerRequestIsMutantHuman() throws IOException {
		InputStream inputStream = buildInputStream("src/test/resources/testMutantHuman.json");
		OutputStream outputStream = buildOutputStream();
		Assertions.assertThrows(RuntimeException.class, () -> {
			mutant.handleRequest(inputStream, outputStream, context);
		});
	}

	@Test
	public void handlerRequestIsMutantInvalidDna() throws IOException {
		InputStream inputStream = buildInputStream("src/test/resources/testMutantInvalidDna.json");
		OutputStream outputStream = buildOutputStream();
		Assertions.assertThrows(RuntimeException.class, () -> {
			mutant.handleRequest(inputStream, outputStream, context);
		});
	}

//	 @Test
//	 public void handlerRequest() throws IOException {
//	 	mutant = new com.meli.mutant.handler.Mutant();
//	 	Assertions.assertNotNull(mutant);
//	 }
	
	@Test
	public void registerMutanDaoImplTest() throws DnaException {
		DynamoDbClient dynamoDb = mock(DynamoDbClient.class);
		Mockito.lenient().when(dynamoDb.putItem(any(PutItemRequest.class))).thenReturn(null);;
		MutantDaoImpl mutant = new MutantDaoImpl(dynamoDb, "Mutant-Meli");
		mutant.registerMutant("", true);
		Assertions.assertNotNull(mutant);
	}
	
	private InputStream buildInputStream(String urlFile) throws FileNotFoundException {
		return new FileInputStream(new File(urlFile));
	}

	private OutputStream buildOutputStream() {
		return new OutputStreamT();
	}
}
