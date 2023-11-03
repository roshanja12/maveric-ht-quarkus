package org.maveric.quarkus.panache.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

import static org.maveric.quarkus.panache.common.SavingAccountConstant.FAILED_MSG;
import static org.maveric.quarkus.panache.common.SavingAccountConstant.SAVING_ACCOUNTS_URL_PATH;

@Getter
@EqualsAndHashCode
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorId;
    private List<ErrorMessage> errors;
    private String status = FAILED_MSG;
    private String message;
    private Integer code = HttpResponseStatus.INTERNAL_SERVER_ERROR.code();
    private String path = SAVING_ACCOUNTS_URL_PATH;
    private Instant timeStamp = Instant.now();
    private Object data;

    public ErrorResponse(String errorId, ErrorMessage errorMessage) {
        this.errorId = errorId;
        this.errors = List.of(errorMessage);
    }

    public ErrorResponse(ErrorMessage errorMessage) {
        this(null, errorMessage);
    }

    public ErrorResponse(List<ErrorMessage> errors) {
        this.errorId = null;
        this.errors = errors;
    }

    public ErrorResponse() {
    }

    @Getter
    @EqualsAndHashCode
    public static class ErrorMessage {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String path = SAVING_ACCOUNTS_URL_PATH;
        private String message;

        public ErrorMessage(String path, String message) {
            this.path = path;
            this.message = message;
        }

        public ErrorMessage(String message) {
            this.path = null;
            this.message = message;
        }

        public ErrorMessage() {
        }
    }

}