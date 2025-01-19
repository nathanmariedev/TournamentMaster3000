package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.match.Match.Status;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

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

}
