package pl.javastart.main.pojo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class User {

  private Integer id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;
  private Integer userStatus;
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
