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

import br.edu.unifio.eventos.entities.Local;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocalRepositoryTest {

    @Autowired
    private LocalRepository localRepository;

    @Test
    @Order(1)
    public void deveBuscarTodosOsLocais() {
        List<Local> locais = localRepository.findAll(Sort.by("nome"));

        assertEquals(5, locais.size());
        assertEquals("Auditorio Principal UNIFIO", locais.get(0).getNome());
        assertEquals("Centro de Convencoes Regional", locais.get(1).getNome());
    }

    @Test
    @Order(2)
    public void deveBuscarUmLocalPorId() {
        Local local = localRepository.findById(1).orElseThrow();

        assertNotNull(local);
        assertEquals("Auditorio Principal UNIFIO", local.getNome());
    }

    @Test
    @Order(3)
    public void deveSalvarUmLocalNovo() {
        Local local = new Local();
        local.setNome("Teatro Municipal");
        local.setEndereco("Rua das Artes, 100");
        local.setCapacidade(450);

        localRepository.save(local);

        assertNotNull(local.getId());
        assertEquals(6, local.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmLocalPorId() {
        Local local = new Local();
        local.setNome("Nome Teste");
        local.setEndereco("Endereco Teste");
        local.setCapacidade(50);

        localRepository.save(local);

        assertTrue(localRepository.existsById(local.getId()));
        localRepository.deleteById(local.getId());
        assertFalse(localRepository.existsById(local.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarONomeDeUmLocal() {
        Local local = new Local();
        local.setNome("Nome Teste");
        local.setEndereco("Endereco Teste");
        local.setCapacidade(50);

        localRepository.save(local);

        local.setNome("Outro Nome Teste");
        localRepository.save(local);

        assertEquals("Outro Nome Teste", localRepository.findById(local.getId()).orElseThrow().getNome());
    }
}