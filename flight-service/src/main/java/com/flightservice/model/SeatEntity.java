package com.flightservice.model;

import com.flightservice.constant.SeatType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "seats")
@Data
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "plane_id")
    private UUID planeId;
    private SeatType seatType;
    private Boolean isOccupied;
}
