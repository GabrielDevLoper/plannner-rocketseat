package com.rocketseat.planner.modules.participant;

import java.util.UUID;

public record ParticipantDTO(UUID id, String nome, String email, boolean isConfirmed) {
}
