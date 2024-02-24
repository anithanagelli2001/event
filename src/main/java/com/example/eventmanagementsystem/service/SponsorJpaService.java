/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.repository.SponsorRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SponsorJpaService implements SponsorRepository {

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Override
    public ArrayList<Sponsor> getSponsors() {
        List<Sponsor> sponsorList = sponsorJpaRepository.findAll();
        ArrayList<Sponsor> sponsors = new ArrayList<>(sponsorList);
        return sponsors;
    }

    @Override
    public Sponsor getSponsorById(int sponsorId) {
        try {
            return sponsorJpaRepository.findById(sponsorId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        try {
            int eventId = sponsor.getEvents().getEventId();
            Event event = eventJpaRepository.findById(eventId).get();
            sponsor.setEvents(event);
            return sponsorJpaRepository.save(sponsor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor newSponsor = sponsorJpaRepository.findById(sponsorId).get();
            if (sponsor.getSponsorName() != null) {
                newSponsor.setSponsorName(sponsor.getSponsorName());
            }
            if (sponsor.getIndustry() != null) {
                newSponsor.setIndustry(sponsor.getIndustry());
            }
            if (sponsor.getEvents() != null) {
                int eventId = sponsor.getEvents().getEventId();
                Event event = eventJpaRepository.findById(eventId).get();
                sponsor.setEvents(event);
            }
            return sponsorJpaRepository.save(newSponsor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            sponsorJpaRepository.deleteById(sponsorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Event> getSponsorEvents(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            return sponsor.getEvents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}