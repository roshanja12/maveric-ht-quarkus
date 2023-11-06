package org.maveric.quarkus.panache.dtos;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResponseDto {
    private String status;
    private String message;
    private Integer code;
    private List<String> error;
    private String path;
    private Instant timeStamp = Instant.now();
    private Object data;
}
