package br.edu.unifio.eventos.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.eventos.entities.Participante;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParticipanteRepositoryTest {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Test
    @Order(1)
    public void deveBuscarTodosOsParticipantes() {
        List<Participante> participantes = participanteRepository.findAll(Sort.by("nome"));

        assertEquals(5, participantes.size());
        assertEquals("Beatriz Costa Ramos", participantes.get(0).getNome());
        assertEquals("Guilherme Souza Santos", participantes.get(1).getNome());
    }

    @Test
    @Order(2)
    public void deveBuscarUmParticipantePorId() {
        Participante participante = participanteRepository.findById(1).orElseThrow();

        assertNotNull(participante);
        assertEquals("Lucas Gabriel Silva", participante.getNome());
    }

    @Test
    @Order(3)
    public void deveSalvarUmParticipanteNovo() {
        Participante participante = new Participante();
        participante.setNome("Fernando Rocha");
        participante.setEmail("fernando@email.com");
        participante.setTelefone("14991112233");

        participanteRepository.save(participante);

        assertNotNull(participante.getId());
        assertEquals(6, participante.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmParticipantePorId() {
        Participante participante = new Participante();
        participante.setNome("Nome Teste");
        participante.setEmail("teste@email.com");
        participante.setTelefone("14999990000");

        participanteRepository.save(participante);

        assertTrue(participanteRepository.existsById(participante.getId()));
        participanteRepository.deleteById(participante.getId());
        assertFalse(participanteRepository.existsById(participante.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarONomeDeUmParticipante() {
        Participante participante = new Participante();
        participante.setNome("Nome Teste");
        participante.setEmail("teste@email.com");
        participante.setTelefone("14999990000");

        participanteRepository.save(participante);

        participante.setNome("Outro Nome Teste");
        participanteRepository.save(participante);

        assertEquals("Outro Nome Teste", participanteRepository.findById(participante.getId()).orElseThrow().getNome());
    }
}