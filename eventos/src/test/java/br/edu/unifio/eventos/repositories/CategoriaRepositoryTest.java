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

import br.edu.unifio.eventos.entities.Categoria;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @Order(1)
    public void deveBuscarTodasAsCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll(Sort.by("nome"));

        assertEquals(5, categorias.size());
        assertEquals("Ciencia de Dados", categorias.get(0).getNome());
        assertEquals("Design & UX", categorias.get(1).getNome());
    }

    @Test
    @Order(2)
    public void deveBuscarUmaCategoriaPorId() {
        Categoria categoria = categoriaRepository.findById(1).orElseThrow();

        assertNotNull(categoria);
        assertEquals("Tecnologia", categoria.getNome());
    }

    @Test
    @Order(3)
    public void deveSalvarUmaCategoriaNova() {
        Categoria categoria = new Categoria();
        categoria.setNome("Marketing Digital");
        categoria.setDescricao("Eventos de comunicacao e trafego");

        categoriaRepository.save(categoria);

        assertNotNull(categoria.getId());
        assertEquals(6, categoria.getId());
    }

    @Test
    @Order(4)
    public void deveExcluirUmaCategoriaPorId() {
        Categoria categoria = new Categoria();
        categoria.setNome("Nome Teste");
        categoria.setDescricao("Descricao Teste");

        categoriaRepository.save(categoria);

        assertTrue(categoriaRepository.existsById(categoria.getId()));
        categoriaRepository.deleteById(categoria.getId());
        assertFalse(categoriaRepository.existsById(categoria.getId()));
    }

    @Test
    @Order(5)
    public void deveAtualizarONomeDeUmaCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("Nome Teste");
        categoria.setDescricao("Descricao Teste");

        categoriaRepository.save(categoria);

        categoria.setNome("Outro Nome Teste");
        categoriaRepository.save(categoria);

        assertEquals("Outro Nome Teste", categoriaRepository.findById(categoria.getId()).orElseThrow().getNome());
    }
}