package org.maveric.quarkus.panache.common;

import org.maveric.quarkus.panache.dtos.ResponseDto;

public class UtilsMethods {

    public static ResponseDto getResponseStructure(String status, Integer code,
                                            String message, Object data, String path
    ) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(status);
        responseDto.setCode(code);
        responseDto.setMessage(message);
        responseDto.setData(data);
        responseDto.setError(null);
        responseDto.setPath(path);
        return responseDto;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
