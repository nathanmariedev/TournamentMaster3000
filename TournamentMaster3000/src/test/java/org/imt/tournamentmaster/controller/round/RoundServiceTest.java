package org.imt.tournamentmaster.controller.round;

import org.imt.tournamentmaster.model.match.Round;
import org.imt.tournamentmaster.service.match.RoundService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoundServiceTest {

    @Autowired
    private RoundService roundService;

    @Test
    public void testGetRoundById() {
        Round round1 = roundService.getById(1L).orElseThrow();

        // assert
        Assertions.assertNotNull(round1);
        Assertions.assertEquals(1L, round1.getId());
        Assertions.assertEquals(1L, round1.getEquipeA().getId());
        Assertions.assertEquals(2L, round1.getEquipeB().getId());
        Assertions.assertEquals(21, round1.getScoreA());
        Assertions.assertEquals(14, round1.getScoreB());
        Assertions.assertEquals(1, round1.getRoundNumber());

        Round round2 = roundService.getById(2L).orElseThrow();

        // assert
        Assertions.assertNotNull(round2);
        Assertions.assertEquals(2L, round2.getId());
        Assertions.assertEquals(1L, round2.getEquipeA().getId());
        Assertions.assertEquals(2L, round2.getEquipeB().getId());
        Assertions.assertEquals(19, round2.getScoreA());
        Assertions.assertEquals(21, round2.getScoreB());
        Assertions.assertEquals(2, round2.getRoundNumber());

        Round round3 = roundService.getById(3L).orElseThrow();

        // assert
        Assertions.assertNotNull(round3);
        Assertions.assertEquals(3L, round3.getId());
        Assertions.assertEquals(1L, round3.getEquipeA().getId());
        Assertions.assertEquals(2L, round3.getEquipeB().getId());
        Assertions.assertEquals(21, round3.getScoreA());
        Assertions.assertEquals(17, round3.getScoreB());
        Assertions.assertEquals(3, round3.getRoundNumber());
    }

    @Test
    public void testGetNonExistingRoundById_shouldBeNull() {
        // assert
        Assertions.assertTrue(roundService.getById(42L).isEmpty());
    }

    @Test
    public void testGetAll() {
        List<Round> rounds = roundService.getAll();

        // assert
        Assertions.assertNotNull(rounds);
        Assertions.assertEquals(6, rounds.size());
    }
}
