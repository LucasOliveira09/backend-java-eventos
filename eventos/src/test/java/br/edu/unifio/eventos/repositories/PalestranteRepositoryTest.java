package br.edu.unifio.eventos.repositories;

import br.edu.unifio.eventos.entities.Palestrante;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PalestranteRepositoryTest {

    @Autowired
    private PalestranteRepository palestranteRepository;

    @Test
    @DisplayName("Deve inserir um palestrante com sucesso")
    void deveInserirPalestrante() {
        Palestrante palestrante = new Palestrante();
        palestrante.setNome("Carlos Mendes");
        palestrante.setMiniBio("Especialista em Inteligência Artificial");
        palestrante.setEmail("carlos.mendes@email.com");

        Palestrante salvo = palestranteRepository.save(palestrante);

        Assertions.assertNotNull(salvo.getId());
        Assertions.assertEquals("Carlos Mendes", salvo.getNome());
        Assertions.assertEquals("carlos.mendes@email.com", salvo.getEmail());
    }

    @Test
    @DisplayName("Deve buscar um palestrante por ID e validar seus atributos")
    void deveBuscarPorId() {
        Palestrante palestrante = palestranteRepository.save(
            new Palestrante(null, "Ana Paula", "Arquiteta Cloud", "ana.paula@email.com")
        );

        Optional<Palestrante> resultado = palestranteRepository.findById(palestrante.getId());

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(palestrante.getId(), resultado.get().getId());
        Assertions.assertEquals("Ana Paula", resultado.get().getNome());
        Assertions.assertEquals("ana.paula@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve listar todos os palestrantes cadastrados")
    void deveListarPalestrantes() {
        palestranteRepository.save(new Palestrante(null, "Palestrante 1", "Bio 1", "p1@email.com"));
        palestranteRepository.save(new Palestrante(null, "Palestrante 2", "Bio 2", "p2@email.com"));

        List<Palestrante> lista = palestranteRepository.findAll();

        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Deve alterar um palestrante existente sem criar novo registro")
    void deveAlterarPalestrante() {
        Palestrante palestrante = palestranteRepository.save(
            new Palestrante(null, "Nome Antigo", "Bio Antiga", "antigo@email.com")
        );
        Integer idOriginal = palestrante.getId();

        palestrante.setNome("Nome Atualizado");
        palestrante.setMiniBio("Bio Revisada");
        palestranteRepository.save(palestrante);

        Optional<Palestrante> resultado = palestranteRepository.findById(idOriginal);
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(idOriginal, resultado.get().getId());
        Assertions.assertEquals("Nome Atualizado", resultado.get().getNome());
        Assertions.assertEquals("Bio Revisada", resultado.get().getMiniBio());
    }

    @Test
    @DisplayName("Deve excluir um palestrante pelo ID")
    void deveExcluirPalestrante() {
        Palestrante palestrante = palestranteRepository.save(
            new Palestrante(null, "Para Excluir", "Bio", "excluir@email.com")
        );
        Integer id = palestrante.getId();
        Assertions.assertTrue(palestranteRepository.findById(id).isPresent());

        palestranteRepository.deleteById(id);

        Optional<Palestrante> resultado = palestranteRepository.findById(id);
        Assertions.assertFalse(resultado.isPresent());
    }
}