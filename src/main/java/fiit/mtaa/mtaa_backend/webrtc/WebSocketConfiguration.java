package fiit.mtaa.mtaa_backend.webrtc;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @SneakyThrows
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        SocketHandler socketHandler = ;
        registry.addHandler(new SocketHandler(), "/socket")
                .setAllowedOrigins("*");
    }
}
