package com.rocketseat.planner.modules.participants;

import java.util.UUID;

public record ParticipantDTO(UUID id, String nome, String email, boolean isConfirmed) {
}
