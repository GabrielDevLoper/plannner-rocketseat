package com.rocketseat.planner.modules.links;

import com.rocketseat.planner.modules.activities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {

    List<Link> findByTripId(UUID tripId);
}
