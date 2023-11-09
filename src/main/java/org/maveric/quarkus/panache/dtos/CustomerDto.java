package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
  private Long customerId;

  private String firstName;
  private String lastName;

  private String email;
  private Long phoneNumber;

}
