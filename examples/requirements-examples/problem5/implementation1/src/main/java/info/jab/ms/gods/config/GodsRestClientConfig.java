package info.jab.ms.gods.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class GodsRestClientConfig {

	/**
	 * REST client for the upstream JSON server. Contract:
	 * {@code src/main/resources/openapi/my-json-server-api.yaml} (GET {@code /greek}).
	 */
	@Bean
	@Qualifier("greekGodsSync")
	public RestClient greekGodsSyncRestClient(GreekGodsProperties properties) {
		ClientHttpRequestFactory factory = requestFactory(properties);
		return RestClient.builder()
				.requestFactory(factory)
				.baseUrl(properties.getSync().getBaseUrl())
				.build();
	}

	private static ClientHttpRequestFactory requestFactory(GreekGodsProperties properties) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		var timeouts = properties.getRestClient();
		factory.setConnectTimeout((int) timeouts.getConnectTimeout().toMillis());
		factory.setReadTimeout((int) timeouts.getReadTimeout().toMillis());
		return factory;
	}
}
