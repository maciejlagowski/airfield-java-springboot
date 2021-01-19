-- USERS - all the passwords are "abc", admin@admin.com password is "admin"

INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (1, 'John@mail.com', 'John', '$2a$10$2zYGObZ2rdpgtcPLAip1RuWABO.M1k2GxjXAqjfDPpdzOI.lzMD6O', '367531590',
        'ROLE_USER', null);
INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (2, 'Julie@mail.com', 'Julie', '$2a$10$VMOCaG.RCRcaT9hp.//7XO1dZQHMsEZmloFc.uJhtUKoTpPKG5a9i', '222494900',
        'ROLE_EMPLOYEE', null);
INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (3, 'Jennifer@mail.com', 'Jennifer', '$2a$10$pNW644qfWSZhdtYCWlP/KuXF3tCWwB.f727K66kVjCkMPIdAXfN6S', '155567910',
        'ROLE_USER', null);
INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (4, 'Helen@mail.com', 'Helen', '$2a$10$XPC5RTvn7ElbHUBccPKrq.s02lRP1AeMvw./kruaKTh2TnrPE7Qvm', '661157929',
        'ROLE_USER', null);
INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (5, 'Rachel@mail.com', 'Rachel', '$2a$10$7BEPJRMUS9tXjnNycGEcnuE28uscfYAEZmqnJiTNanqmyDWtwK1vS', '714992714',
        'ROLE_USER', null);
INSERT INTO airfield.user (id, email, name, password_hash, phone_number, role, token)
VALUES (6, 'admin@admin.com', 'Admin', '$2a$10$Al9pIvWU1UMRuSLrmRBnDe38nwS2nWDLlWAy9kEuCaBdaDJyUqouW', '666666666',
        'ROLE_ADMIN', null);

-- RESERVATIONS TODO zrobic to lepiej (daty itd)

INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (1, '2021-01-19', '08:00:00', 'OTHER', '07:00:00', 'CANCELLED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (2, '2021-01-19', '11:00:00', 'OTHER', '10:00:00', 'ACCEPTED', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (3, '2021-01-19', '13:00:00', 'CONCERT', '12:00:00', 'CANCELLED', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (4, '2021-01-19', '15:00:00', 'FLIGHT', '14:00:00', 'ACCEPTED', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (5, '2021-01-19', '16:00:00', 'FLIGHT', '15:00:00', 'REJECTED', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (6, '2021-01-18', '08:00:00', 'MOTORSPORTS', '07:00:00', 'REJECTED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (7, '2021-01-18', '10:00:00', 'FLIGHT', '09:00:00', 'DONE', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (8, '2021-01-18', '12:00:00', 'FLIGHT', '11:00:00', 'DONE', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (9, '2021-01-18', '13:00:00', 'FLIGHT', '12:00:00', 'CANCELLED', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (10, '2021-01-18', '16:00:00', 'CONCERT', '15:00:00', 'CANCELLED', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (11, '2021-01-17', '08:00:00', 'OTHER', '07:00:00', 'CANCELLED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (12, '2021-01-17', '09:00:00', 'FLIGHT', '08:00:00', 'DONE', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (13, '2021-01-17', '12:00:00', 'MOTORSPORTS', '11:00:00', 'CANCELLED', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (14, '2021-01-17', '13:00:00', 'OTHER', '12:00:00', 'NEW', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (15, '2021-01-17', '15:00:00', 'OTHER', '14:00:00', 'DONE', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (16, '2021-01-16', '08:00:00', 'CONCERT', '07:00:00', 'ACCEPTED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (17, '2021-01-16', '10:00:00', 'OTHER', '09:00:00', 'REJECTED', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (18, '2021-01-16', '13:00:00', 'CONCERT', '12:00:00', 'ACCEPTED', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (19, '2021-01-16', '15:00:00', 'FLIGHT', '14:00:00', 'CANCELLED', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (20, '2021-01-16', '18:00:00', 'CONCERT', '17:00:00', 'REJECTED', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (21, '2021-01-15', '08:00:00', 'OTHER', '07:00:00', 'REJECTED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (22, '2021-01-15', '11:00:00', 'OTHER', '10:00:00', 'CANCELLED', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (23, '2021-01-15', '12:00:00', 'MOTORSPORTS', '11:00:00', 'DONE', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (24, '2021-01-15', '14:00:00', 'MOTORSPORTS', '13:00:00', 'DONE', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (25, '2021-01-15', '17:00:00', 'OTHER', '16:00:00', 'REJECTED', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (26, '2021-01-14', '08:00:00', 'FLIGHT', '07:00:00', 'ACCEPTED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (27, '2021-01-14', '10:00:00', 'CONCERT', '09:00:00', 'NEW', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (28, '2021-01-14', '12:00:00', 'CONCERT', '11:00:00', 'DONE', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (29, '2021-01-14', '14:00:00', 'CONCERT', '13:00:00', 'NEW', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (30, '2021-01-14', '16:00:00', 'OTHER', '15:00:00', 'REJECTED', 5);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (31, '2021-01-13', '08:00:00', 'MOTORSPORTS', '07:00:00', 'REJECTED', 1);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (32, '2021-01-13', '11:00:00', 'CONCERT', '10:00:00', 'REJECTED', 2);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (33, '2021-01-13', '13:00:00', 'CONCERT', '12:00:00', 'NEW', 3);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (34, '2021-01-13', '16:00:00', 'OTHER', '15:00:00', 'CANCELLED', 4);
INSERT INTO airfield.reservation (id, date, end_time, reservation_type, start_time, status, user_id)
VALUES (35, '2021-01-13', '19:00:00', 'OTHER', '18:00:00', 'ACCEPTED', 5);
