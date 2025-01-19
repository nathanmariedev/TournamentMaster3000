package org.imt.tournamentmaster.service.match;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Match> getById(long id) {
        return matchRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Match> getAll() {
        return StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional
    public Optional<Match> updateMatch(long id, Match updatedMatch) {
        Optional<Match> match = matchRepository.findById(id);

        if (match.isPresent()) {
            Match existingMatch = match.get();

            // Détacher les entités liées si elles sont déjà attachées à la session
            if (entityManager.contains(existingMatch.getEquipeA())) {
                entityManager.detach(existingMatch.getEquipeA());
            }
            if (entityManager.contains(existingMatch.getEquipeB())) {
                entityManager.detach(existingMatch.getEquipeB());
            }

            // Fusionner les entités d'équipe
            existingMatch.setEquipeA(entityManager.merge(updatedMatch.getEquipeA()));
            existingMatch.setEquipeB(entityManager.merge(updatedMatch.getEquipeB()));

            // Mettre à jour les autres propriétés
            existingMatch.setStatus(updatedMatch.getStatus());
            existingMatch.setRounds(updatedMatch.getRounds());

            // Sauvegarder le match mis à jour
            matchRepository.save(existingMatch);

            return Optional.of(existingMatch);
        }

        return Optional.empty();
    }
}
