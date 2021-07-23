package com.meli.mutant.dal.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.meli.mutant.exception.DnaException;
import com.meli.mutant.model.Statistics;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest.Builder;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.Select;

public class MutantDaoImpl implements IMutantDao {
	private DynamoDbClient dynamoDb;
	private String tableName;
	private static final String ID = "pk";
	private static final String DNA = "dna";
	private static final String IS_MUTANT = "isMutant";
	
	public MutantDaoImpl(DynamoDbClient dynamoDb, String tableName) {
		this.tableName =  tableName;
		this.dynamoDb = dynamoDb;
	}

	@Override
	public void registerMutant(String dna, boolean isMutant) throws DnaException {
		try {
            Map<String, AttributeValue> insert = transformDtoToMap(dna, isMutant);
            dynamoDb.putItem(PutItemRequest.builder()
                    .tableName(tableName)
                    .item(insert)
                    .conditionExpression("attribute_not_exists(" + ID + ")")
                    .build());

        } catch (ConditionalCheckFailedException | ResourceNotFoundException e) {
            throw new DnaException(e.getMessage());
        }

	}

	@Override
	public Statistics stats() throws DnaException {
		ScanResponse result;
		Statistics stats;
		Map<String, String> mutants = new HashMap<>();
		Map<String, String> humans = new HashMap<>();
		
		Map<String, AttributeValue> expresionHum = new HashMap<String, AttributeValue>(); 
		expresionHum.put(":mutant", AttributeValue.builder().bool(false).build());
		
		Map<String, AttributeValue> expresionMut = new HashMap<String, AttributeValue>(); 
		expresionMut.put(":mutant", AttributeValue.builder().bool(true).build());
		String filterExpresion =  "#".concat(IS_MUTANT.concat(" = :mutant"));
        try {
			
			mutants.put("#".concat(IS_MUTANT), IS_MUTANT);
			humans.put("#".concat(IS_MUTANT), IS_MUTANT);
			Builder scanRequest = ScanRequest.builder().tableName(tableName).filterExpression(filterExpresion)
					.consistentRead(false).expressionAttributeNames(humans).expressionAttributeValues(expresionHum)
					.select(Select.COUNT);
			
			
			
			result = dynamoDb.scan(scanRequest.build());
			stats = new Statistics();
			stats.setCountHumanDna(result.count());
			
			scanRequest = ScanRequest.builder().tableName(tableName).filterExpression(filterExpresion)
					.consistentRead(false).expressionAttributeNames(mutants).expressionAttributeValues(expresionMut)
					.select(Select.COUNT);
			result = dynamoDb.scan(scanRequest.build());
			stats.setCountMutantDna(result.count());
			
			stats.calculateRatio();
		} catch (ResourceNotFoundException e) {
			throw new DnaException("Not exist registers");
		}
		return stats;
	}
	
	protected Map<String, AttributeValue> transformDtoToMap(String dna, boolean isMutant) {
        Map<String, AttributeValue> item = new HashMap<>();
        
        item.put(ID, AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        item.put(DNA, AttributeValue.builder().s(dna).build());
        item.put(IS_MUTANT, AttributeValue.builder().bool(isMutant).build());

        return item;

    }

}
