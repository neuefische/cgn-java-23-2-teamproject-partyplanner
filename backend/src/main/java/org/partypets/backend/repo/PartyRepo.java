package org.partypets.backend.repo;

import jakarta.servlet.http.Part;
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

    public void  add(Party party) {
        this.parties.add(party);
    }

}
