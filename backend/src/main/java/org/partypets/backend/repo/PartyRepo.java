package org.partypets.backend.repo;

import org.partypets.backend.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartyRepo extends MongoRepository<Party, String> {


}
