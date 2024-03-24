package edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "links_github")
public class GithubLink {
    @Id
    private Integer id;

    private int latestIssueNumber;
    private int latestPrNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Link link;

    public GithubLink(Integer id, int latestIssueNumber, int latestPrNumber, Link link) {
        this.id = id;
        this.latestIssueNumber = latestIssueNumber;
        this.latestPrNumber = latestPrNumber;
        this.link = link;
    }

    public GithubLink() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLatestIssueNumber() {
        return latestIssueNumber;
    }

    public void setLatestIssueNumber(int latestIssueNumber) {
        this.latestIssueNumber = latestIssueNumber;
    }

    public int getLatestPrNumber() {
        return latestPrNumber;
    }

    public void setLatestPrNumber(int latestPrNumber) {
        this.latestPrNumber = latestPrNumber;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
