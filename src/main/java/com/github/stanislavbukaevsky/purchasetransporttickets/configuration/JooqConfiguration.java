//package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;
//
//import io.r2dbc.spi.Connection;
//import io.r2dbc.spi.ConnectionFactories;
//import io.r2dbc.spi.ConnectionFactory;
//import io.r2dbc.spi.ConnectionFactoryOptions;
//import org.jooq.DSLContext;
//import org.jooq.Publisher;
//import org.jooq.impl.DSL;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import reactor.core.publisher.Mono;
//
//import java.util.Collection;
//
//@Configuration
//public class JooqConfiguration {
////    private ConnectionFactory connectionFactory;
//
//    @Bean
//    public DSLContext createContext() {
////        return DSL.using(connectionFactory);
//        ConnectionFactory connectionFactory = ConnectionFactories.get(
//                ConnectionFactoryOptions
//                        .parse("jdbc:postgresql://localhost:5434/purchase-transport-tickets")
//                        .mutate()
//                        .option(ConnectionFactoryOptions.USER, "bukaevsky")
//                        .option(ConnectionFactoryOptions.PASSWORD, "stanislav")
//                        .build()
//        );
//
//        return DSL.using(connectionFactory);
//    }
//}
