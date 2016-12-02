USE eppione;

-- ========== DB SETUP script ==========

INSERT INTO `role` VALUES 
('ROLE_SYSADMIN'), 
('ROLE_ADMIN'), 
('ROLE_CONTRIBUTOR'), 
('ROLE_MANAGER'), 
('ROLE_CITITOR');

INSERT INTO `department`
(`id`, `name`)
VALUES
    (1, 'iTeam'), (2, 'Dep2'), (3, 'Dep3');

INSERT INTO `user`
(`id`,
 `first_name`,
 `last_name`,
 `username`,
 `email`,
 `password`,
 `function`,
 `id_superior`,
 `department_id`)
VALUES
    (1, 'Diana', 'Dobra', 'dianadobra', 'dianadobra95@yahoo.ro', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'teamlead', 2, 1),
    (2, 'Sandi', 'Petrut', 'sandipetrut', 'sandi@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'frontend', 4, 1),
    (3, 'Helga', 'Fekete', 'helgafekete', 'helga@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'backend', 1, 1),
    (4, 'Mircea', 'Ciuciu', 'mirceaciuciu', 'mircea@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'fullstack', 3, 1),
    (5, 'Leo', 'Corocea', 'leocorocea', 'leo@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'frontend', 6, 1),
    (6, 'Vsa', 'Draghita', 'vsadraghita', 'vsa@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'fullstack', 8, 1),
    (7, 'Felix', 'Danut', 'felixdanut', 'felix@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'tester', 1, 1),
    (8, 'Daniel', 'Bogdan', 'danielbogdan', 'daniel@mail.com', '$2a$10$Q2AOEO4LHdoX6cbNtQw00ux8pOXeVWuvaJwvyRu/vb55wYnilkj1O', 'frontend', 7, 1);

INSERT INTO `user_role`
(`user_id`, 
 `role_name`) 
VALUES 
(1, 'ROLE_SYSADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_CONTRIBUTOR'),
(4, 'ROLE_CITITOR'),
(5, 'ROLE_MANAGER'),
(6, 'ROLE_ADMIN'),
(7, 'ROLE_CONTRIBUTOR'),
(8, 'ROLE_MANAGER');



