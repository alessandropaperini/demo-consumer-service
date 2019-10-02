package it.laterale.cloud.consumers;

import it.laterale.cloud.dtos.Ball;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BallDataConsumer {

    public void receiveMessage(Ball data) {
        // Do something
        log.info("Consuming ball data {}", data.toString());
    }

}
