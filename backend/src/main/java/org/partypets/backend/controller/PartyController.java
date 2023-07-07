package org.partypets.backend.controller;


import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.Quiz;
import org.partypets.backend.service.PartyService;
import org.partypets.backend.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PartyController {


    private final PartyService partyService;
    private final QuizService quizService;

    public PartyController(PartyService partyService, QuizService quizService) {
        this.partyService = partyService;
        this.quizService = quizService;
    }

    @GetMapping("/parties")
    public List<Party> listParties() {
        return this.partyService.list();
    }

    @GetMapping("/parties/{id}")
    public Party getDetails(@PathVariable String id) {
        return this.partyService.getDetails(id);
    }


    @PostMapping("/parties")
    public List<Party> addParty(@RequestBody DTOParty newParty) {
        this.partyService.add(newParty);
        return this.partyService.list();
    }

    @PutMapping("/parties/{id}")
    public Party update(@PathVariable String id, @RequestBody DTOParty newParty) {
        return partyService.edit(id, newParty);
    }

    @DeleteMapping("/parties/{id}")
    public void delete(@PathVariable String id) {
        partyService.delete(id);
    }

    @GetMapping("/quiz")
    public Quiz getRandomQuiz() {
        return this.quizService.getRandom();
    }
}
