package kz.newmanne.irbis.web.dto;


import lombok.Data;

@Data
public class NewsDTO {
    private String source;
    private String topic;
    private String news;
}
