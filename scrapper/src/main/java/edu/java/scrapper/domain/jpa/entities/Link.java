package edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_id_seq")
    @SequenceGenerator(name = "links_id_seq", sequenceName = "links_id_seq", allocationSize = 1)
    private Integer id;

    //    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime lastCheck;
    private OffsetDateTime lastUpdate;
    private String url;

    public Link(Integer id, OffsetDateTime lastCheck, OffsetDateTime lastUpdate, String url) {
        this.id = id;
        this.lastCheck = lastCheck;
        this.lastUpdate = lastUpdate;
        this.url = url;
    }

    public Link() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public OffsetDateTime getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(OffsetDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    public OffsetDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
