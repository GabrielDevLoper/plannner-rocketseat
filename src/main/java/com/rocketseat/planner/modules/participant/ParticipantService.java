package com.rocketseat.planner.modules.participant;

import com.rocketseat.planner.modules.trip.Trip;
import com.rocketseat.planner.modules.trip.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip) {

        List<Participant> participantsList = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.participantRepository.saveAll(participantsList);

        System.out.println(participantsList.get(0).getId());
    }



    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip) {
        Participant participant = new Participant(email, trip);

        this.participantRepository.save(participant);

        return new ParticipantCreateResponse(participant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {
    }



    public void triggerConfirmationEmailToParticipant(String email) {
    }

    public List<ParticipantDTO> getAllParticipantsFromEvent(UUID tripId){
        return this.participantRepository.findByTripId(tripId).stream().map(participant -> new ParticipantDTO(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }

}
