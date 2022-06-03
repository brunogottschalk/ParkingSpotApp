package com.parkingSpot.app.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "spot")
@Table(name = "spot")
public class SpotsModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "is_available")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ValueModel spotType;

}
