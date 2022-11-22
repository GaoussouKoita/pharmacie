INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'ROOT'),
(2, 'ADMINISTRATEUR'),
(3, 'EMPLOYE');

INSERT INTO `pharmacie` (`id`, `adresse`, `nom`, `telephone`) VALUES
(1, 'Quartier du Fleuve', 'Pharmacie Populaire', 66666666);

INSERT INTO `utilisateur` (`id`, `adresse`, `email`, `nom`, `password`, `prenom`, `telephone`, `pharmacie_id`) VALUES
(1, 'Baguineda', 'admin@g', 'KOITA', '$2a$10$lnqBpZV77RRJvKyf/ixatOQFgcg0rruCuboXnZdgHMNTzB4rXJNCC', 'Gaoussou', 76684788, 1),
(2, 'Dakar', 'user@g', 'BRIBAUD', '$2a$10$G6fKkNS6ZErL.7Y83Y1lk.POtmVQ6rNe/MmdHDefDn70JZQncfoU.', 'Yannick', 773332211, 1);

INSERT INTO `utilisateur_roles` (`utilisateur_id`, `roles_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3);
