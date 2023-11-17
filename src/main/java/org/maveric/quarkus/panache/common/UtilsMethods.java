package org.maveric.quarkus.panache.common;

import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.SavingsAccountRequestDto;
import org.maveric.quarkus.panache.enums.SavingsAccountStatus;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.modelmapper.ModelMapper;

import java.sql.Blob;
import java.time.Instant;

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
    public static Integer getPageIndexValue(Integer page, Integer size) {
        Integer index=null;
        if (page != 0) {
            index = page - 1;
            if (index != 0) {
                index *= size;
            }
        }
        return index;
    }

  public static SavingsAccount toSavingAccount(SavingsAccountRequestDto dto, String fileName, Blob blob){
    ModelMapper mapper=new ModelMapper();
    SavingsAccount savingsAccount = mapper.map(dto, SavingsAccount.class);
    savingsAccount.setStatus(SavingsAccountStatus.APPLIED);
    savingsAccount.setCreatedDate(Instant.now());
    savingsAccount.setUpdatedDate(Instant.now());
    savingsAccount.setDocument(blob);
    savingsAccount.setDocumentName(fileName);
    savingsAccount.setSavingsAccountId(null);
    return savingsAccount;
  }
}
