-- =========================================================================
-- CARGA INICIAL DE DADOS (import.sql)
-- 2º Trabalho Parcial - Sistema de Gerenciamento de Eventos
-- Total: 30 registros (5 para cada entidade)
-- =========================================================================

-- 1. CATEGORIAS (5 registros)
INSERT INTO categoria (id, nome, descricao) VALUES (1, 'Tecnologia', 'Conferencias, workshops e seminarios de tecnologia e software');
INSERT INTO categoria (id, nome, descricao) VALUES (2, 'Inovacao', 'Encontros sobre inovacao, empreendedorismo e startups');
INSERT INTO categoria (id, nome, descricao) VALUES (3, 'Ciencia de Dados', 'Eventos focados em inteligencia artificial, machine learning e dados');
INSERT INTO categoria (id, nome, descricao) VALUES (4, 'Design & UX', 'Encontros e oficinas sobre design de produtos e experiencia do usuario');
INSERT INTO categoria (id, nome, descricao) VALUES (5, 'Seguranca da Informacao', 'Eventos sobre ciberseguranca, privacidade e conformidade');

-- 2. LOCAIS (5 registros)
INSERT INTO local (id, nome, endereco, capacidade) VALUES (1, 'Auditorio Principal UNIFIO', 'Rodovia BR-153, Km 338, Ourinhos - SP', 300);
INSERT INTO local (id, nome, endereco, capacidade) VALUES (2, 'Laboratorio de Informatica 3', 'Bloco B - UNIFIO, Ourinhos - SP', 40);
INSERT INTO local (id, nome, endereco, capacidade) VALUES (3, 'Espaco Coworking Hub', 'Rua Parana, 450, Centro, Ourinhos - SP', 80);
INSERT INTO local (id, nome, endereco, capacidade) VALUES (4, 'Centro de Convencoes Regional', 'Av. das Flores, 1200, Ourinhos - SP', 500);
INSERT INTO local (id, nome, endereco, capacidade) VALUES (5, 'Sala Multiuso Tech', 'Bloco C - UNIFIO, Ourinhos - SP', 60);

-- 3. PALESTRANTES (5 registros)
INSERT INTO palestrante (id, nome, mini_bio, email) VALUES (1, 'Sergio Roberto Delfino', 'Professor e especialista em Engenharia de Software e Cloud', 'sergio.delfino@unifio.edu.br');
INSERT INTO palestrante (id, nome, mini_bio, email) VALUES (2, 'Ana Paula Ferreira', 'Arquiteta de Solucoes Cloud e DevOps', 'ana.ferreira@techcloud.com');
INSERT INTO palestrante (id, nome, mini_bio, email) VALUES (3, 'Carlos Eduardo Mendes', 'Cientista de Dados senior e pesquisador em IA', 'carlos.mendes@datahub.org');
INSERT INTO palestrante (id, nome, mini_bio, email) VALUES (4, 'Mariana Lima Santos', 'Lead Product Designer e especialista em Acessibilidade', 'mariana.santos@uxdesign.io');
INSERT INTO palestrante (id, nome, mini_bio, email) VALUES (5, 'Roberto Albuquerque', 'Especialista em Red Team e Ciberseguranca Defensiva', 'roberto.albuquerque@secdefense.com');

-- 4. PARTICIPANTES (5 registros)
INSERT INTO participante (id, nome, email, telefone) VALUES (1, 'Lucas Gabriel Silva', 'lucas.silva@aluno.unifio.edu.br', '14998765432');
INSERT INTO participante (id, nome, email, telefone) VALUES (2, 'Beatriz Costa Ramos', 'beatriz.ramos@aluno.unifio.edu.br', '14991234567');
INSERT INTO participante (id, nome, email, telefone) VALUES (3, 'Guilherme Souza Santos', 'guilherme.souza@email.com', '14997654321');
INSERT INTO participante (id, nome, email, telefone) VALUES (4, 'Juliana Martins Prado', 'juliana.prado@email.com', '14996543210');
INSERT INTO participante (id, nome, email, telefone) VALUES (5, 'Rafael Fernandes Dias', 'rafael.dias@email.com', '14995432109');

-- 5. EVENTOS (5 registros)
INSERT INTO evento (id, nome, descricao, data_inicio, data_fim, capacidade, status, categoria_id, local_id, palestrante_id) VALUES (1, 'Semana da Computacao UNIFIO 2026', 'Imersao em arquitetura de microsservicos e computacao em nuvem', '2026-10-15 09:00:00', '2026-10-17 18:00:00', 250, 'AGENDADO', 1, 1, 1);
INSERT INTO evento (id, nome, descricao, data_inicio, data_fim, capacidade, status, categoria_id, local_id, palestrante_id) VALUES (2, 'Workshop de Spring Boot e Docker', 'Oficina pratica desenvolvendo aplicacoes prontas para containers', '2026-10-22 14:00:00', '2026-10-22 18:00:00', 40, 'CONFIRMADO', 1, 2, 2);
INSERT INTO evento (id, nome, descricao, data_inicio, data_fim, capacidade, status, categoria_id, local_id, palestrante_id) VALUES (3, 'Summit de Inteligencia Artificial Aplicada', 'Casos reais de uso de modelos de linguagem e visao computacional', '2026-11-05 08:30:00', '2026-11-05 17:30:00', 80, 'AGENDADO', 3, 3, 3);
INSERT INTO evento (id, nome, descricao, data_inicio, data_fim, capacidade, status, categoria_id, local_id, palestrante_id) VALUES (4, 'Design Systems na Pratica', 'Como unificar componentes de UI da concepcao ao codigo', '2026-11-12 19:00:00', '2026-11-12 22:00:00', 60, 'AGENDADO', 4, 5, 4);
INSERT INTO evento (id, nome, descricao, data_inicio, data_fim, capacidade, status, categoria_id, local_id, palestrante_id) VALUES (5, 'Conferencia de Ciberseguranca e Defesa Digital', 'Tecnicas modernas de prevencao e resposta a incidentes', '2026-11-20 09:00:00', '2026-11-20 18:00:00', 400, 'AGENDADO', 5, 4, 5);

-- 6. INSCRICOES (5 registros)
INSERT INTO inscricao (id, data_inscricao, status, evento_id, participante_id) VALUES (1, '2026-09-10 10:30:00', 'CONFIRMADA', 1, 1);
INSERT INTO inscricao (id, data_inscricao, status, evento_id, participante_id) VALUES (2, '2026-09-11 14:15:00', 'CONFIRMADA', 1, 2);
INSERT INTO inscricao (id, data_inscricao, status, evento_id, participante_id) VALUES (3, '2026-09-12 09:00:00', 'CONFIRMADA', 2, 3);
INSERT INTO inscricao (id, data_inscricao, status, evento_id, participante_id) VALUES (4, '2026-09-13 16:45:00', 'PENDENTE', 3, 4);
INSERT INTO inscricao (id, data_inscricao, status, evento_id, participante_id) VALUES (5, '2026-09-14 11:20:00', 'CONFIRMADA', 5, 5);
