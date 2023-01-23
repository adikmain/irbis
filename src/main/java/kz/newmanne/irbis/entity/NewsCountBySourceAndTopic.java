package kz.newmanne.irbis.entity;

public interface NewsCountBySourceAndTopic {
    String getSource();

    String getTopic();

    Long getNewsCount();
}