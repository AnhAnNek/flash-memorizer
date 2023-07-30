package com.universityteam.flashmemorizer.controller;

import com.universityteam.flashmemorizer.dto.CardDTO;
import com.universityteam.flashmemorizer.dto.DeckDTO;
import com.universityteam.flashmemorizer.exception.DeckNotFoundException;
import com.universityteam.flashmemorizer.service.CardService;
import com.universityteam.flashmemorizer.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {

    private final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private DeckService deckService;

    @Autowired
    private CardService cardService;

    @GetMapping("/get")
    public String getDecksByUserId(@RequestParam Long userId, Model m) {
        List<DeckDTO> decks;
        try {
            decks = deckService.getByUser(userId);
        } catch (DeckNotFoundException e) {
            log.error(e.getMessage());
            decks = new ArrayList<>();
        }
        m.addAttribute("decks", decks);
        return "deck";
    }

    @GetMapping("/input")
    public String inputForm() {
        return "input-deck";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute DeckDTO deck, RedirectAttributes ra) {
        deck.setCreation(new Date());
        deck.setModified(new Date());
        try {
            deckService.add(deck);
            log.info("Deck added successfully!");
            ra.addFlashAttribute("msg", "Deck added successfully!");
            return "redirect:/decks/edit/" + deck.getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            ra.addFlashAttribute("msg", "Deck added unsuccessfully!");
            return "redirect:/decks/new";
        }
    }

    @GetMapping("/edit/{deckId}")
    public String getDeckDetails(@PathVariable("deckId") Long deckId, Model m) {
        DeckDTO deck;
        try {
            deck = deckService.getById(deckId);
            ArrayList<CardDTO> cards = (ArrayList<CardDTO>) cardService.getByDeckId(deckId);
            deck.setCards(cards);
            log.info("get deck successfully!");
        } catch (DeckNotFoundException e) {
            log.error(e.getMessage());
            deck = new DeckDTO();
        }
        m.addAttribute("deck", deck);
        m.addAttribute("cards", deck.getCards());
        return "edit-deck";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute DeckDTO deck, RedirectAttributes ra) {
        try {
            deckService.update(deck);
            cardService.saveInOnlyOneDeck(deck.getId(), deck.getCards());
            log.info("Deck updated successfully!");
            ra.addFlashAttribute("msg", "Deck updated successfully!");
        } catch (DeckNotFoundException e) {
            log.error(e.getMessage());
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cards/review/" + deck.getId();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long userId, @RequestParam Long deckId, RedirectAttributes ra) {
        try {
            deckService.delete(deckId);
            ra.addFlashAttribute("message", "The Deck Id=" + deckId + " has been deleted.");
        } catch (DeckNotFoundException e) {
            log.error(e.getMessage());
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/decks/get?userId=" + userId;
    }
}