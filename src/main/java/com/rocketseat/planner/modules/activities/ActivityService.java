package com.rocketseat.planner.modules.activities;


import com.rocketseat.planner.modules.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponse saveActivity(ActivityRequestPayload payload, Trip trip){
    Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);

    this.activityRepository.save(newActivity);

    return new ActivityResponse(newActivity.getId());
    }
}
