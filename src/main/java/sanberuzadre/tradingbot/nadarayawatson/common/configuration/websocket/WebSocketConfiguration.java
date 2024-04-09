package sanberuzadre.tradingbot.nadarayawatson.common.configuration.websocket;

import com.binance.connector.client.WebSocketApiClient;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.impl.WebSocketApiClientImpl;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.signaturegenerator.HmacSignatureGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfiguration {

    // ToDo: check with the client if we want to change apiKey and secretKey dynamically
    @Value("${binance.api.test.key}")
    private String apiKey;
    @Value("${binance.api.test.secret}")
    private String secretKey;

    @Bean
    public WebSocketApiClient createWebSocketApiClient() {
        HmacSignatureGenerator signatureGenerator = new HmacSignatureGenerator(secretKey);

        // ToDo: need to add a possibility of changing TESTNET_WS_API_URL, and changing test credentials to prod WS_API_URL and prod credentials.
        // ToDo: use factory that returns new WebSocketApiClientImpl with appropriate apiKey and WS API URL.
        return new WebSocketApiClientImpl(apiKey, signatureGenerator, DefaultUrls.TESTNET_WS_API_URL);
    }

    @Bean
    public WebSocketStreamClient createWebSocketStreamClient() {
        return new WebSocketStreamClientImpl();
    }

}
