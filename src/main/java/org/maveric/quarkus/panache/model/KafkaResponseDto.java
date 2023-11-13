package org.maveric.quarkus.panache.model;

import lombok.Data;
import org.maveric.quarkus.panache.enums.Type;

import java.io.Serializable;

@Data
public class KafkaResponseDto implements Serializable {

  public Type type;
  public Object  message;

}
