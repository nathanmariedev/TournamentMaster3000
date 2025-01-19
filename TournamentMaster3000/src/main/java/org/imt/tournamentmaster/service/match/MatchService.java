package org.imt.tournamentmaster.service.match;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.match.Match.Status;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.imt.tournamentmaster.service.equipe.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final EquipeService equipeService;
    private final EntityManager entityManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MatchService(MatchRepository matchRepository, EquipeService equipeService, EntityManager entityManager) {
        this.matchRepository = matchRepository;
        this.equipeService = equipeService;
        this.entityManager = entityManager;
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
    public Match creerMatch(Match match) {
        /*
        Equipe equipeA = renvoyerOuCreerEquipe(match.getEquipeA());
        equipeA = entityManager.merge(equipeA);
        match.setEquipeA(equipeA);
        Equipe equipeB = renvoyerOuCreerEquipe(match.getEquipeB());
        equipeB = entityManager.merge(equipeB);
        match.setEquipeB(equipeB);
         */
        return matchRepository.save(match);
    }

    private Equipe renvoyerOuCreerEquipe(Equipe equipe) {
        if (equipe != null && equipe.getId() != 0) {
            Optional<Equipe> equipeExistante = equipeService.getById(equipe.getId());
            if (equipeExistante.isPresent()) {
                return equipeExistante.get();
            }
        }
        return equipeService.creerEquipe(equipe);
    }

    public List<Match> searchMatch(
            Long equipeAId,
            Long equipeBId,
            String equipeAName,
            String equipeBName,
            String playerName,
            Status status
    ) {
        Specification<Match> spec = Specification.where(null);

        if (!ObjectUtils.isEmpty(equipeAId)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("equipeA").get("id"), equipeAId));
        }

        if (!ObjectUtils.isEmpty(equipeBId)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("equipeB").get("id"), equipeBId));
        }

        if (!ObjectUtils.isEmpty(equipeAName)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("equipeA").get("name"), equipeAName));
        }

        if (!ObjectUtils.isEmpty(equipeBName)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("equipeB").get("name"), equipeBName));
        }

        if (!ObjectUtils.isEmpty(playerName)) {
            spec = spec.and((root, query, cb) -> {
                var equipeAJoin = root.join("equipeA");
                var playerAJoin = equipeAJoin.join("joueurs");

                var equipeBJoin = root.join("equipeB");
                var playerBJoin = equipeBJoin.join("joueurs");

                String pattern = "%" + playerName.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(playerAJoin.get("nom")), pattern),
                        cb.like(cb.lower(playerBJoin.get("nom")), pattern)
                );
            });
        }

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        return matchRepository.findAll(spec);
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
