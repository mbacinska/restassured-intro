package pl.javastart.main.pojo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserResponse {

  private Integer code;
  private String type;
  private String message;
}
