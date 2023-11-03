package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@ToString
public class ResponseDto {
    private String status;
    private String message;
    private Integer code;
    private List<String> error;
    private String path;
    private Instant timeStamp;
    private Object data;
}
