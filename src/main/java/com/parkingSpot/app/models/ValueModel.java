package com.parkingSpot.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name = "spot_value")
@Table(name = "spot_value", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueModel {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private BigDecimal value;
    private String type;
}
