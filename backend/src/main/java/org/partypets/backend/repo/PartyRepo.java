package org.partypets.backend.repo;

import org.partypets.backend.model.Diet;
import org.partypets.backend.model.Guest;
import org.partypets.backend.model.Party;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository

public class PartyRepo {

    private List<Party> parties;

    public PartyRepo() {
        this.parties = new ArrayList<>();
        Party newParty = new Party("FakeDate", "Home", "Dog-Bday", List.of(new Guest("Gökhan", true, Diet.VEGETARIAN)));
        this.parties.add(newParty);

    }

    public List<Party> getParties() {
        return parties;
    }

    public void add(Party newParty) {
        this.parties.add(newParty);
    }
}
