package br.edu.unifio.eventos.repositories;

import br.edu.unifio.eventos.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class InscricaoRepositoryTest {

    @Autowired private InscricaoRepository inscricaoRepository;
    @Autowired private EventoRepository eventoRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private LocalRepository localRepository;
    @Autowired private PalestranteRepository palestranteRepository;

    private Evento eventoBase;
    private Participante participanteBase;

    @BeforeEach
    void setup() {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Geral", "Eventos gerais"));
        Local local = localRepository.save(new Local(null, "Sala 1", "Bloco A", 50));
        Palestrante palestrante = palestranteRepository.save(new Palestrante(null, "Palestrante Base", "Bio", "p@email.com"));

        eventoBase = eventoRepository.save(new Evento(
                null, "Evento Teste", "Descricao",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                50, "CONFIRMADO", categoria, local, palestrante
        ));

        participanteBase = participanteRepository.save(
                new Participante(null, "Participante Base", "aluno@email.com", "14999998888")
        );
    }

    private Inscricao criarInscricao(String status) {
        return new Inscricao(
                null,
                LocalDateTime.now(),
                status,
                eventoBase,
                participanteBase
        );
    }

    @Test
    @DisplayName("Deve inserir uma inscrição associada a evento e participante")
    void deveInserirInscricao() {
        Inscricao inscricao = criarInscricao("CONFIRMADA");

        Inscricao salva = inscricaoRepository.save(inscricao);

        Assertions.assertNotNull(salva.getId());
        Assertions.assertEquals("CONFIRMADA", salva.getStatus());
        Assertions.assertEquals(eventoBase.getId(), salva.getEvento().getId());
        Assertions.assertEquals(participanteBase.getId(), salva.getParticipante().getId());
    }

    @Test
    @DisplayName("Deve buscar inscrição por ID e validar seus atributos")
    void deveBuscarPorId() {
        Inscricao salva = inscricaoRepository.save(criarInscricao("CONFIRMADA"));

        Optional<Inscricao> resultado = inscricaoRepository.findById(salva.getId());

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(salva.getId(), resultado.get().getId());
        Assertions.assertEquals("CONFIRMADA", resultado.get().getStatus());
        Assertions.assertNotNull(resultado.get().getDataInscricao());
    }

    @Test
    @DisplayName("Deve listar todas as inscrições cadastradas")
    void deveListarInscricoes() {
        inscricaoRepository.save(criarInscricao("CONFIRMADA"));
        inscricaoRepository.save(criarInscricao("PENDENTE"));

        List<Inscricao> lista = inscricaoRepository.findAll();

        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Deve alterar uma inscrição existente sem criar novo registro")
    void deveAlterarInscricao() {
        Inscricao salva = inscricaoRepository.save(criarInscricao("PENDENTE"));
        Integer idOriginal = salva.getId();

        salva.setStatus("CONFIRMADA");
        inscricaoRepository.save(salva);

        Optional<Inscricao> resultado = inscricaoRepository.findById(idOriginal);
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(idOriginal, resultado.get().getId());
        Assertions.assertEquals("CONFIRMADA", resultado.get().getStatus());
    }

    @Test
    @DisplayName("Deve excluir uma inscrição pelo ID")
    void deveExcluirInscricao() {
        Inscricao salva = inscricaoRepository.save(criarInscricao("CANCELADA"));
        Integer id = salva.getId();
        Assertions.assertTrue(inscricaoRepository.findById(id).isPresent());

        inscricaoRepository.deleteById(id);

        Optional<Inscricao> resultado = inscricaoRepository.findById(id);
        Assertions.assertFalse(resultado.isPresent());
    }
}