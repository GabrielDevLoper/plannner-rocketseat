package com.rocketseat.planner.modules.trip;

import com.rocketseat.planner.modules.activities.ActivityRequestPayload;
import com.rocketseat.planner.modules.activities.ActivityResponse;
import com.rocketseat.planner.modules.activities.ActivityService;
import com.rocketseat.planner.modules.participant.*;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService parcipantService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip trip = new Trip(payload);

        this.tripRepository.save(trip);

        this.parcipantService.registerParticipantsToEvent(payload.emails_to_invite(), trip);

        return ResponseEntity.ok(new TripCreateResponse(trip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip updatedTrip = trip.get();

            updatedTrip.setDestination(payload.destination());
            updatedTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTrip.setOwnerName(payload.owner_name());
            updatedTrip.setOwnerEmail(payload.owner_email());

            this.tripRepository.save(updatedTrip);

            return ResponseEntity.ok(updatedTrip);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip updatedTrip = trip.get();

            updatedTrip.setIsConfirmed(true);

            this.tripRepository.save(updatedTrip);

            this.parcipantService.triggerConfirmationEmailToParticipants(updatedTrip.getId());

            return ResponseEntity.ok(updatedTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantCreateResponse participant = this.parcipantService.registerParticipantToEvent(payload.email(), rawTrip);

            if (rawTrip.getIsConfirmed()) this.parcipantService.triggerConfirmationEmailToParticipant(payload.email());

            return ResponseEntity.ok(participant);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantDTO> participantList = this.parcipantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }


    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.saveActivity(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }

}
