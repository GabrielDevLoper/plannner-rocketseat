package com.rocketseat.planner.modules.activities;


import com.rocketseat.planner.modules.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "occurs_at")
    private LocalDateTime occursAt;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;


    public Activity(String title, String occursAt, Trip trip) {
        this.title = title;
        this.trip = trip;
        this.occursAt = LocalDateTime.parse(occursAt, DateTimeFormatter.ISO_DATE_TIME);
    }

}
