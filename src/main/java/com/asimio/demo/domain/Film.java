package com.asimio.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film", schema = "public")
@Getter
@Setter
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "film_id", unique = true, nullable = false)
    private int filmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "rental_duration", nullable = false)
    private short rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false, precision = 5)
    private BigDecimal replacementCost;

    @Column(name = "rating")
    private String rating;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false, length = 29)
    private Date lastUpdate;

    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "fulltext", nullable = false)
    private String fulltext;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "film")
    private Set<Inventory> inventories = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmCategory> filmCategories = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmActor> filmActors = new HashSet<>(0);

    public Integer getId() {
        return this.filmId;
    }
}
