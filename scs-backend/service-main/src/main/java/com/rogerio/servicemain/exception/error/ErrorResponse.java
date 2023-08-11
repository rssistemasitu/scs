package com.rogerio.servicemain.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;
    private String details;

    public ErrorResponse(int status, LocalDateTime timestamp, String message, String details) {
        super();
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


}
