#Consultas SQL

/*Insertamos las regiones*/
INSERT INTO regiones (nombre) VALUES ('America');
INSERT INTO regiones (nombre) VALUES ('Europa');
INSERT INTO regiones (nombre) VALUES ('Asia');


/*Creamos algunos usuarios*/
INSERT INTO usuarios (username, password, enable) VALUES ('Saul','$2a$10$MtedsvvXfRC0Ie0AyjfK6.6Y3sELzv1S7IvYs/Xeb2dknM32HUdNm',true);
INSERT INTO usuarios (username, password, enable) VALUES ('Admin','$2a$10$7yAiZxZrjEottM9FfgeQ/uno4yiJFmVZfa14qQUtRyScIsE8EYNhS',true);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_USER');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,1);