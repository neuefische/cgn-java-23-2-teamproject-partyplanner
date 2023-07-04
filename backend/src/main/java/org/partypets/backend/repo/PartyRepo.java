package org.partypets.backend.repo;

import lombok.Data;
import org.partypets.backend.model.Party;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class PartyRepo {

    private List<Party> parties;

    public PartyRepo() {
        this.parties = new ArrayList<>();
    }

    public List<Party> getParties() {
        return parties;
    }

    public Party add(Party party) {
        this.parties.add(party);
        return this.parties.get(parties.size() - 1);
    }

    public Party getById(String id) {
        for (Party party : parties) {
            if (party.getId().equals(id)) {
                return party;
            }

        }
        return null;
    }
}
