package com.parkingSpot.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "history", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class HistoryModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_finished")
    private boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private SpotsModel spot;

}
