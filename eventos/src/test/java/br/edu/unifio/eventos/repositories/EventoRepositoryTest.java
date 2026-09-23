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

import br.edu.unifio.eventos.entities.Categoria;
import br.edu.unifio.eventos.entities.Evento;
import br.edu.unifio.eventos.entities.Local;
import br.edu.unifio.eventos.entities.Palestrante;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventoRepositoryTest {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private PalestranteRepository palestranteRepository;

    @Test
    @Order(1)
    public void deveBuscarTodosOsEventos() {
        List<Evento> eventos = eventoRepository.findAll(Sort.by("nome"));

        assertEquals(5, eventos.size());
        assertEquals("Conferencia de Ciberseguranca e Defesa Digital", eventos.get(0).getNome());
        assertEquals("Design Systems na Pratica", eventos.get(1).getNome());
    }

    @Test
    @Order(2)
    public void deveBuscarUmEventoPorId() {
        Evento evento = eventoRepository.findById(1).orElseThrow();

        assertNotNull(evento);
        assertEquals("Semana da Computacao UNIFIO 2026", evento.getNome());
    }

    @Test
    @Order(3)
    public void deveSalvarUmEventoNovo() {
        Evento evento = new Evento();
        evento.setNome("Hackathon Academico 2026");
        evento.setDescricao("Competicao de programacao de 24 horas");
        evento.setDataInicio(LocalDateTime.of(2026, 12, 1, 8, 0));
        evento.setDataFim(LocalDateTime.of(2026, 12, 2, 8, 0));
        evento.setCapacidade(100);
        evento.setStatus("AGENDADO");

        Categoria categoria = categoriaRepository.findById(1).orElseThrow();
        Local local = localRepository.findById(1).orElseThrow();
        Palestrante palestrante = palestranteRepository.findById(1).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepository.save(evento);

        assertNotNull(evento.getId());
        assertEquals(6, evento.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmEventoPorId() {
        Evento evento = new Evento();
        evento.setNome("Nome Teste");
        evento.setDescricao("Descricao Teste");
        evento.setDataInicio(LocalDateTime.now());
        evento.setDataFim(LocalDateTime.now().plusHours(2));
        evento.setCapacidade(20);
        evento.setStatus("CANCELADO");

        Categoria categoria = categoriaRepository.findById(1).orElseThrow();
        Local local = localRepository.findById(1).orElseThrow();
        Palestrante palestrante = palestranteRepository.findById(1).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepository.save(evento);

        assertTrue(eventoRepository.existsById(evento.getId()));
        eventoRepository.deleteById(evento.getId());
        assertFalse(eventoRepository.existsById(evento.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarONomeDeUmEvento() {
        Evento evento = new Evento();
        evento.setNome("Nome Teste");
        evento.setDescricao("Descricao Teste");
        evento.setDataInicio(LocalDateTime.now());
        evento.setDataFim(LocalDateTime.now().plusHours(2));
        evento.setCapacidade(20);
        evento.setStatus("AGENDADO");

        Categoria categoria = categoriaRepository.findById(1).orElseThrow();
        Local local = localRepository.findById(1).orElseThrow();
        Palestrante palestrante = palestranteRepository.findById(1).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepository.save(evento);

        evento.setNome("Outro Nome Teste");
        eventoRepository.save(evento);

        assertEquals("Outro Nome Teste", eventoRepository.findById(evento.getId()).orElseThrow().getNome());
    }
}