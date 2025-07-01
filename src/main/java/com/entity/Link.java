package com.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"sourcePage", "targetPage"})
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    @Setter(AccessLevel.NONE)
    private Long linkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_page_id", nullable = false)
    private Page sourcePage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_page_id")
    private Page targetPage;

    @Column(name = "target_url", nullable = false, length = 1000)
    private String targetUrl;

    @Column(name = "anchor_text", length = 500)
    private String anchorText;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type", nullable = false)
    private LinkType linkType;

    @Column(name = "rel_attribute", length = 100)
    private String relAttribute;

    @Column(name = "title_attribute", length = 200)
    private String titleAttribute;

    @CreationTimestamp
    @Column(name = "discovered_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime discoveredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link link)) return false;
        return linkId != null && linkId.equals(link.linkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum LinkType {
        INTERNAL, EXTERNAL, MAILTO, JAVASCRIPT, ANCHOR
    }
}
