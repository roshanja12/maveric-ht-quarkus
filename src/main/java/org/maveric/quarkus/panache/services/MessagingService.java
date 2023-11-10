package org.maveric.quarkus.panache.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.maveric.quarkus.panache.dtos.SavingsAccountResponseDto;
import org.maveric.quarkus.panache.enums.Type;
import org.maveric.quarkus.panache.model.CreateSavingsMessageDto;
import org.maveric.quarkus.panache.model.KafkaResponseDto;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.Instant;
import org.eclipse.microprofile.reactive.messaging.Channel;
public class MessagingService {

  @Inject
  @Channel("insights_events")
  Emitter<KafkaResponseDto> messageEmitter;
  public void savingsAccountProducer(SavingsAccountResponseDto responseDetails){
    try {
      CreateSavingsMessageDto messageDto =convertResponseDtoToEventMessage(responseDetails);
      KafkaResponseDto sendDto = new KafkaResponseDto();
      sendDto.setType(Type.SAVING_ACCOUNT_CREATED);
      sendDto.setMessage(messageDto);
      messageEmitter.send(sendDto);
    }catch (Exception e){
      e.getStackTrace();
    }

  }
  private CreateSavingsMessageDto convertResponseDtoToEventMessage(SavingsAccountResponseDto responseDetails) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    CreateSavingsMessageDto messageDto = new CreateSavingsMessageDto();
     messageDto.setSavingsAccountId(responseDetails.getSavingsAccountId());
    messageDto.setMinOpeningBalance(responseDetails.getMinOpeningBalance());
    messageDto.setType(Type.CREATE);
    messageDto.setCustomerId(responseDetails.getCustomerId());
    messageDto.setStatus(String.valueOf(responseDetails.getStatus()));
    messageDto.setCreatedAt(String.valueOf(Instant.now()));
    messageDto.setAllowOverDraft(responseDetails.getAllowOverDraft());
    messageDto.setInterestCompoundingPeriod(responseDetails.getInterestCompoundPeriod());
    messageDto.setCity(responseDetails.getCity());
    return messageDto;

  }
}
