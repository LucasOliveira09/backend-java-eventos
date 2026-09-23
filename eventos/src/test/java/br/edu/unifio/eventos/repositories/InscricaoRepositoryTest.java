package br.edu.unifio.eventos.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.eventos.entities.Evento;
import br.edu.unifio.eventos.entities.Inscricao;
import br.edu.unifio.eventos.entities.Participante;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InscricaoRepositoryTest {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Test
    @Order(1)
    public void deveBuscarTodasAsInscricoes() {
        List<Inscricao> inscricoes = inscricaoRepository.findAll(Sort.by("id"));

        assertEquals(5, inscricoes.size());
        assertEquals("CONFIRMADA", inscricoes.get(0).getStatus());
        assertEquals(1, inscricoes.get(0).getEvento().getId());
    }

    @Test
    @Order(2)
    public void deveBuscarUmaInscricaoPorId() {
        Inscricao inscricao = inscricaoRepository.findById(1).orElseThrow();

        assertNotNull(inscricao);
        assertEquals("CONFIRMADA", inscricao.getStatus());
        assertEquals(1, inscricao.getParticipante().getId());
    }

    @Test
    @Order(3)
    public void deveSalvarUmaInscricaoNova() {
        Inscricao inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        Evento evento = eventoRepository.findById(1).orElseThrow();
        Participante participante = participanteRepository.findById(1).orElseThrow();

        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);

        inscricaoRepository.save(inscricao);

        assertNotNull(inscricao.getId());
        assertEquals(6, inscricao.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmaInscricaoPorId() {
        Inscricao inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CANCELADA");

        Evento evento = eventoRepository.findById(1).orElseThrow();
        Participante participante = participanteRepository.findById(1).orElseThrow();

        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);

        inscricaoRepository.save(inscricao);

        assertTrue(inscricaoRepository.existsById(inscricao.getId()));
        inscricaoRepository.deleteById(inscricao.getId());
        assertFalse(inscricaoRepository.existsById(inscricao.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarOStatusDeUmaInscricao() {
        Inscricao inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("PENDENTE");

        Evento evento = eventoRepository.findById(1).orElseThrow();
        Participante participante = participanteRepository.findById(1).orElseThrow();

        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);

        inscricaoRepository.save(inscricao);

        inscricao.setStatus("CONFIRMADA");
        inscricaoRepository.save(inscricao);

        assertEquals("CONFIRMADA", inscricaoRepository.findById(inscricao.getId()).orElseThrow().getStatus());
    }
}