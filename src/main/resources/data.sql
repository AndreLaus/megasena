INSERT INTO USUARIO(nome, email, senha) VALUES('Admin', 'admin@email.com', '$2a$10$EoFr18KsM.MvdvAXFsFaYugup7APP5RBAmJqezvFDPd8n2hpVy71G');--admin
INSERT INTO PERFIL(nome) VALUES('ROLE_ADMIN');
INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES (1,1);

INSERT INTO USUARIO(nome, email, senha) VALUES('teste', 'teste@email.com', '$2a$10$TG53gqxSBJ0srH/Con242edGJJAGdtIT03mF.LfSIYxbTQHGOXg4e');--teste123
INSERT INTO USUARIO(nome, email, senha) VALUES('user', 'user@email.com', '$2a$10$TG53gqxSBJ0srH/Con242edGJJAGdtIT03mF.LfSIYxbTQHGOXg4e');--teste123