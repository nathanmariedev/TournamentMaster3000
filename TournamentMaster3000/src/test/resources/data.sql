-----------------------------------------------------------
-- Données initiales pour les équipes existantes et leurs joueurs
-----------------------------------------------------------
-- Insertion des joueurs existants
INSERT INTO joueur (id, nom, prenom, numero) VALUES (1, 'Doe', 'John', 1);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (2, 'Doe', 'Jane', 2);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (3, 'Doe', 'Jack', 3);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (4, 'Doe', 'Jill', 4);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (5, 'Doe', 'Jim', 5);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (6, 'Doe', 'Jenny', 6);

-- Insertion des équipes existantes
INSERT INTO equipe (id, nom) VALUES (1, 'Equipe 1');
INSERT INTO equipe (id, nom) VALUES (2, 'Equipe 2');

-- Association des joueurs aux équipes existantes
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (1, 1);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (1, 2);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (1, 3);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (2, 4);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (2, 5);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (2, 6);

-----------------------------------------------------------
-- Données initiales pour les rounds et matchs existants
-----------------------------------------------------------
-- Insertion des rounds existants
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (1, 1, 2, 21, 14, 1);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (2, 1, 2, 19, 21, 2);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (3, 1, 2, 21, 17, 3);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (4, 1, 2, 2, 1, 4);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (5, 1, 2, 1, 2, 5);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (6, 1, 2, 2, 1, 6);

-- Insertion des matchs existants
INSERT INTO `match` (id, equipea_id, equipeb_id, status) VALUES (1, 1, 2, 2);
INSERT INTO `match` (id, equipea_id, equipeb_id, status) VALUES (2, 1, 2, 2);

-- Association des rounds aux matchs existants
INSERT INTO match_rounds (match_id, rounds_id) VALUES (1, 1);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (1, 2);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (1, 3);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (2, 4);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (2, 5);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (2, 6);

-- Insertion des résultats pour les matchs existants
INSERT INTO resultat (id, match_id) VALUES (1, 1);
INSERT INTO resultat (id, match_id) VALUES (2, 2);

-----------------------------------------------------------
-- Ajout d'une nouvelle équipe et création d'un match correspondant
-----------------------------------------------------------
-- 1. Insertion de trois nouveaux joueurs pour la nouvelle équipe (Equipe 3)
INSERT INTO joueur (id, nom, prenom, numero) VALUES (7, 'Smith', 'Alice', 7);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (8, 'Smith', 'Bob', 8);
INSERT INTO joueur (id, nom, prenom, numero) VALUES (9, 'Smith', 'Charlie', 9);

-- 2. Insertion de la nouvelle équipe "Equipe 3"
INSERT INTO equipe (id, nom) VALUES (3, 'Equipe 3');

-- 3. Association des joueurs à l'équipe 3
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (3, 7);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (3, 8);
INSERT INTO equipe_joueurs (equipe_id, joueurs_id) VALUES (3, 9);

-- 4. Création de nouveaux rounds pour le match opposant Equipe 3 et Equipe 1
-- Ici on crée trois rounds (id 7, 8 et 9) avec des scores d'exemple
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (7, 3, 1, 21, 18, 1);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (8, 3, 1, 19, 21, 2);
INSERT INTO round (id, equipea_id, equipeb_id, scorea, scoreb, round_number) VALUES (9, 3, 1, 21, 16, 3);

-- 5. Insertion d'un nouveau match (Match 3) entre Equipe 3 et Equipe 1
-- Le status est défini à 2 (par exemple, 2 = TERMINE)
INSERT INTO `match` (id, equipea_id, equipeb_id, status) VALUES (3, 3, 1, 1);

-- 6. Association des rounds du nouveau match dans la table de jointure match_rounds
INSERT INTO match_rounds (match_id, rounds_id) VALUES (3, 7);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (3, 8);
INSERT INTO match_rounds (match_id, rounds_id) VALUES (3, 9);

-- 7. Insertion d'un enregistrement de résultat pour le nouveau match dans la table resultat
INSERT INTO resultat (id, match_id) VALUES (3, 3);
