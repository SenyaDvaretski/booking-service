package com.flightservice.model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "flights")
@Data
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID departureAirportId;
    private UUID arrivalAirportId;
    private UUID planeId;

    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    private String departureGateNumber;
    private String arrivalGateNumber;

    private Integer price;

    @ElementCollection
    private Set<String> tags;
}
