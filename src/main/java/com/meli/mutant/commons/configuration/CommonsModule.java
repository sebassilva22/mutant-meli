package com.meli.mutant.commons.configuration;

import java.net.URI;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import com.meli.mutant.dal.dao.IMutantDao;
import com.meli.mutant.dal.dao.MutantDaoImpl;

import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

@Module
public class CommonsModule {

	
	@Singleton
    @Provides
    @Named("tableName")
    String tableName() {
        return Optional.ofNullable(System.getenv("TABLE_NAME")).orElse("Mutant-Meli");
    }

    @Singleton
    @Provides
    DynamoDbClient dynamoDb() {
        final String endpoint = System.getenv("ENDPOINT_OVERRIDE");
        DynamoDbClientBuilder builder = DynamoDbClient.builder();
        builder.httpClient(ApacheHttpClient.builder().build());
        //Run to local dynamodb set variables on environment
        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(endpoint));
        }
        return builder.build();
    }

    @Singleton
    @Provides
    IMutantDao mutantImplement(DynamoDbClient dynamoDb, @Named("tableName") String tableName) {
        return new MutantDaoImpl(dynamoDb, tableName);
    }
}
