package org.partypets.backend.service;

import org.partypets.backend.exception.NoSuchPartyException;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.PartyWithoutId;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.partypets.backend.security.MongoUser;
import org.partypets.backend.security.MongoUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    private final PartyRepo partyRepo;

    private final UuIdService uuIdService;

    private final MongoUserService userService;

    public PartyService(PartyRepo partyRepo, UuIdService uuIdService, MongoUserService userService) {
        this.partyRepo = partyRepo;
        this.uuIdService = uuIdService;
        this.userService = userService;
    }

    public List<Party> list() {
        return this.partyRepo.findAll();
    }

    public Party add(PartyWithoutId newParty) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = this.userService.getUserByUsername(username);
        String id = uuIdService.getRandomId();
        Party party = new Party(id, newParty.getDate(), newParty.getLocation(), newParty.getTheme(), user.id());
        return this.partyRepo.insert(party);
    }

    public Party getDetails(String id) {
        return this.partyRepo.findById(id).orElseThrow(() -> new NoSuchPartyException(id));
    }

    public Party edit(String id, PartyWithoutId newParty) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = this.userService.getUserByUsername(username);

        Party currentParty = this.partyRepo.findById(id).orElseThrow(() -> new NoSuchPartyException(id));
        if (currentParty.getUserId().equals(user.id())) {
            Party editedParty = new Party(id, newParty.getDate(), newParty.getLocation(), newParty.getTheme(), user.id());
            return this.partyRepo.save(editedParty);
        }
        return currentParty;
    }

    public void delete(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = this.userService.getUserByUsername(username);

        Party currentParty = this.partyRepo.findById(id).orElseThrow(() -> new NoSuchPartyException(id));
        if (currentParty.getUserId().equals(user.id())) {
            this.partyRepo.deleteById(id);
        }
    }
}
