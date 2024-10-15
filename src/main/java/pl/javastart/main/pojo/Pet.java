package pl.javastart.main.pojo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class Pet {
  private Integer id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<Tag> tags;
  private String status;
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
