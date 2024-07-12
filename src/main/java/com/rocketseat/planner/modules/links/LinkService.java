package com.rocketseat.planner.modules.links;



import com.rocketseat.planner.modules.activities.ActivityDTO;
import com.rocketseat.planner.modules.trips.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;


    public LinkResponse createLink(LinkRequestPayload payload, Trip trip) {
        Link link = new Link(payload.title(), payload.url(), trip);

        this.linkRepository.save(link);

        return new LinkResponse(link.getId());
    }


    public List<LinkDTO> getAllLinksFromId(UUID tripId){
        return this.linkRepository.findByTripId(tripId).stream().map(link -> new LinkDTO(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
