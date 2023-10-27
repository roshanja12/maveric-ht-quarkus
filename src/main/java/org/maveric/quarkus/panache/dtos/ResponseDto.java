package org.maveric.quarkus.panache.dtos;

import lombok.Data;
import java.time.Instant;

@Data
public class ResponseDto {
    private  String status;
    private  String message;
    private  Long code;
    private  String error;
    private  String path;
    private Instant timeStamp;
    private  Object data;
}
