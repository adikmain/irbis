package kz.newmanne.irbis.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDTO {
    private String source;
    private String topic;
    private String news;
}
