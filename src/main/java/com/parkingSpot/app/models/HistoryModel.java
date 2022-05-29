package com.parkingSpot.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "history")
public class HistoryModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "departure_date")
    private Date departureDate;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private SpotsModel spot;

}
