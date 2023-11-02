package org.maveric.quarkus.panache.enums;

import java.util.stream.Stream;

public enum CustomHttpStatus {

    C_200(200);
    private int code;

    private CustomHttpStatus(int code) {
        this.code = code;
    }

    public int getCustomHttpStatus() {
        return code;
    }

    public static CustomHttpStatus of(int code) {
        return Stream.of(CustomHttpStatus.values())
                .filter(p -> p.getCustomHttpStatus() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
