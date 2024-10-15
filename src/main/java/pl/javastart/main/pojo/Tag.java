package pl.javastart.main.pojo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class Tag {

  private Integer id;
  private String name;
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
