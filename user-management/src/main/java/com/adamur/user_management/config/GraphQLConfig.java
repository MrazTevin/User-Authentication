package com.adamur.user_management.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import java.util.Collections;

@Configuration
public class GraphQLConfig {
    @Bean
    public GraphQlSourceBuilderCustomizer exceptionHandler() {
        return (builder) ->
                builder.exceptionResolvers(Collections.singletonList(new DataFetcherExceptionResolverAdapter() {
                    @Override
                    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
                        System.out.println("GraphQL Error: " + ex.getMessage());
                        ex.printStackTrace();
                        return GraphQLError.newError()
                                .message(ex.getMessage())
                                .path(env.getExecutionStepInfo().getPath())
                                .location(env.getField().getSourceLocation())
                                .build();
                    }
                }));
    }
}