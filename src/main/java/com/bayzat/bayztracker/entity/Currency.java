package com.bayzat.bayztracker.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Entity
@Table(name = "CURRENCY")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotEmpty
    public String name;

    @Column(nullable = false)
    @NotEmpty
    public String symbol;

    @Column(nullable = false)
    @NotEmpty
    public Double currentPrice;

    @Column
    public Instant createdTime;

    @Column
    public Boolean enabled;
}
