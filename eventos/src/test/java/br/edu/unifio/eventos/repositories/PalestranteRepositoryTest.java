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

import br.edu.unifio.eventos.entities.Palestrante;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PalestranteRepositoryTest {

    @Autowired
    private PalestranteRepository palestranteRepository;

    @Test
    @Order(1)
    public void deveBuscarTodosOsPalestrantes() {
        List<Palestrante> palestrantes = palestranteRepository.findAll(Sort.by("nome"));

        assertEquals(5, palestrantes.size());
        assertEquals("Ana Paula Ferreira", palestrantes.get(0).getNome());
        assertEquals("Carlos Eduardo Mendes", palestrantes.get(1).getNome());
    }

    @Test
    @Order(2)
    public void deveBuscarUmPalestrantePorId() {
        Palestrante palestrante = palestranteRepository.findById(1).orElseThrow();

        assertNotNull(palestrante);
        assertEquals("Sergio Roberto Delfino", palestrante.getNome());
    }

    @Test
    @Order(3)
    public void deveSalvarUmPalestranteNovo() {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome("Lucas Oliveira");
        palestrante.setMiniBio("Engenheiro de Software");
        palestrante.setEmail("lucas@exemplo.com");

        palestranteRepository.save(palestrante);

        assertNotNull(palestrante.getId());
        assertEquals(6, palestrante.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmPalestrantePorId() {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome("Nome Teste");
        palestrante.setMiniBio("Bio Teste");
        palestrante.setEmail("teste@email.com");

        palestranteRepository.save(palestrante);

        assertTrue(palestranteRepository.existsById(palestrante.getId()));
        palestranteRepository.deleteById(palestrante.getId());
        assertFalse(palestranteRepository.existsById(palestrante.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarONomeDeUmPalestrante() {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome("Nome Teste");
        palestrante.setMiniBio("Bio Teste");
        palestrante.setEmail("teste@email.com");

        palestranteRepository.save(palestrante);

        palestrante.setNome("Outro Nome Teste");
        palestranteRepository.save(palestrante);

        assertEquals("Outro Nome Teste", palestranteRepository.findById(palestrante.getId()).orElseThrow().getNome());
    }
}