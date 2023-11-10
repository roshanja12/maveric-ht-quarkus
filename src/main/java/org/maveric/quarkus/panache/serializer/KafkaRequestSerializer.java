package org.maveric.quarkus.panache.serializer;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import org.maveric.quarkus.panache.model.KafkaResponseDto;

public class KafkaRequestSerializer extends ObjectMapperSerializer<KafkaResponseDto> {
  public KafkaRequestSerializer() {
    super();
  }
}
