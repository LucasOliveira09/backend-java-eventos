package br.edu.unifio.eventos.repositories;

import br.edu.unifio.eventos.entities.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class LocalRepositoryTest {

    @Autowired
    private LocalRepository localRepository;

    @Test
    @DisplayName("Deve inserir um local com sucesso")
    void deveInserirLocal() {
        Local local = new Local();
        local.setNome("Auditório Principal");
        local.setEndereco("Bloco A - Campus");
        local.setCapacidade(250);

        Local salvo = localRepository.save(local);

        Assertions.assertNotNull(salvo.getId());
        Assertions.assertEquals("Auditório Principal", salvo.getNome());
        Assertions.assertEquals(250, salvo.getCapacidade());
    }

    @Test
    @DisplayName("Deve buscar um local por ID e validar seus atributos")
    void deveBuscarPorId() {
        Local local = new Local();
        local.setNome("Laboratório 01");
        local.setEndereco("Bloco B - Térreo");
        local.setCapacidade(35);
        Local salvo = localRepository.save(local);

        Optional<Local> resultado = localRepository.findById(salvo.getId());

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(salvo.getId(), resultado.get().getId());
        Assertions.assertEquals("Laboratório 01", resultado.get().getNome());
        Assertions.assertEquals("Bloco B - Térreo", resultado.get().getEndereco());
    }

    @Test
    @DisplayName("Deve listar todos os locais cadastrados")
    void deveListarLocais() {
        Local l1 = new Local(null, "Sala 101", "Bloco C", 40);
        Local l2 = new Local(null, "Sala 102", "Bloco C", 45);
        localRepository.save(l1);
        localRepository.save(l2);

        List<Local> lista = localRepository.findAll();

        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Deve alterar um local existente sem criar novo registro")
    void deveAlterarLocal() {
        Local local = localRepository.save(new Local(null, "Espaço Antigo", "Rua A", 50));
        Integer idOriginal = local.getId();

        local.setNome("Espaço Inovação");
        local.setCapacidade(80);
        localRepository.save(local);

        Optional<Local> resultado = localRepository.findById(idOriginal);
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(idOriginal, resultado.get().getId());
        Assertions.assertEquals("Espaço Inovação", resultado.get().getNome());
        Assertions.assertEquals(80, resultado.get().getCapacidade());
    }

    @Test
    @DisplayName("Deve excluir um local pelo ID")
    void deveExcluirLocal() {
        Local local = localRepository.save(new Local(null, "Sala Provisória", "Bloco D", 20));
        Integer id = local.getId();
        Assertions.assertTrue(localRepository.findById(id).isPresent());

        localRepository.deleteById(id);

        Optional<Local> resultado = localRepository.findById(id);
        Assertions.assertFalse(resultado.isPresent());
    }
}