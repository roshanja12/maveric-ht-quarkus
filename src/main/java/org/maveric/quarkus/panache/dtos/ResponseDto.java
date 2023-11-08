package org.maveric.quarkus.panache.dtos;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String status;
    private String message;
    private Integer code;
    private String error;
    private String path;
    private Instant timeStamp = Instant.now();
    private Object data;
}

