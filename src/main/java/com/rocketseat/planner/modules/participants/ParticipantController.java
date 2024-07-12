package com.rocketseat.planner.modules.participants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Participant> participant = this.participantRepository.findById(id);

        if(participant.isPresent()){
            Participant updatedParticipant = participant.get();

            updatedParticipant.setIsConfirmed(true);
            updatedParticipant.setName(payload.name());
            updatedParticipant.setEmail(payload.email());

            this.participantRepository.save(updatedParticipant);

            return ResponseEntity.ok(updatedParticipant);
        }

        return ResponseEntity.notFound().build();
    }
}
