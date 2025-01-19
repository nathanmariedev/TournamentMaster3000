package org.imt.tournamentmaster.controller.equipe;

import jakarta.validation.Valid;
import org.imt.tournamentmaster.model.equipe.Joueur;
import org.imt.tournamentmaster.service.equipe.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/joueur")
public class JoueurController {

    private final JoueurService joueurService;

    @Autowired
    public JoueurController(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joueur> getJoueurById(@PathVariable long id) {
        Optional<Joueur> joueur = joueurService.getById(id);

        return joueur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Joueur> getAllJoueurs() {
        return joueurService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> saveJoueur(@Valid @RequestBody Joueur joueur) {
        try {
            Optional<Joueur> savedJoueur = joueurService.save(joueur);
            return ResponseEntity.ok(savedJoueur.get());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
