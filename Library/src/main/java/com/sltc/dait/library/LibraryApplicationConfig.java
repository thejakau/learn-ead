package com.sltc.dait.library;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.concurrent.Executor;


@Configuration
public class LibraryApplicationConfig {

	@Bean
	public Executor mailSendTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("SendingEmail-");
		executor.initialize();
		return executor;
	}

	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration
				.builder()
				.withJdbcTemplate(new JdbcTemplate(dataSource))
				.usingDbTime() // Use database time for locks
				.build());
	}

//	@Bean
//	public RestTemplate createRestTemplateWithConnectionPool() {
//		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//		connectionManager.setMaxTotal(100); // Maximum total connections
//		connectionManager.setDefaultMaxPerRoute(20); // Maximum connections per route
//		RequestConfig requestConfig = RequestConfig.custom()
////				.setConnectTimeout(3000) // wait time to establish a connection to the external component.
////				.setSocketTimeout(3000) // Socket timeout in milliseconds
//				.setConnectionRequestTimeout(3000) // wait time to get a connection from a connection pool
//				.build();
//		CloseableHttpClient httpClient = HttpClients.custom()
//				.setConnectionManager(connectionManager)
//				.setDefaultRequestConfig(requestConfig)
////				.evictIdleConnections(30, TimeUnit.SECONDS) // Evict idle connections after 30 seconds
//				.evictExpiredConnections() // Evict expired connections
//				.build();
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		return new RestTemplate(requestFactory);
//
//		PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
//				.setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
//						.setSslContext(SSLContexts.createSystemDefault())
//						.setTlsVersions(TLS.V_1_3)
//						.build())
//				.setDefaultSocketConfig(SocketConfig.custom()
//						.setSoTimeout(Timeout.ofSeconds(3))  //how long the socket will wait in receive mode without receiving anything before timing out
//						.build())
//				.setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
//				.setConnPoolPolicy(PoolReusePolicy.LIFO)
//				.setDefaultConnectionConfig(ConnectionConfig.custom()
//						.setSocketTimeout(Timeout.ofMinutes(1))  //wait for a response from an HTTP web server - between two data packets
//						.setConnectTimeout(Timeout.ofMinutes(1))
//						.setTimeToLive(TimeValue.ofMinutes(10))
//						.build())
//				.build();
//	}

}
