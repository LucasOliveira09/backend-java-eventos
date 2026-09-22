package br.edu.unifio.eventos.repositories;

import br.edu.unifio.eventos.entities.Categoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

// TODOS OS COMENTARIOS SERVEM PARA MEU APRENDIZADO E LER O CODIGO MELHOR, USEI A IA PARA ENTENDER, NÃO PARA CODAR PRA MIM!

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=never",
    "spring.jpa.defer-datasource-initialization=false"
})
class CategoriaRepositoryTest {
    
    @Autowired 
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Deve inserir uma categoria com sucesso")
    void deveInserirCategoria() {
        // 1. Cenário (Arrange): Criamos a categoria com id null porque o banco vai gerar o ID
        Categoria categoria = new Categoria();
        categoria.setNome("Tecnologia");
        categoria.setDescricao("Conferências e workshops de TI");

        // 2. Ação (Act): Salvamos usando o repository
        Categoria salva = categoriaRepository.save(categoria);

        // 3. Validação (Assert): Confirmamos se o banco gerou o ID e gravou os dados certos
        Assertions.assertNotNull(salva.getId());
        Assertions.assertEquals("Tecnologia", salva.getNome());
        Assertions.assertEquals("Conferências e workshops de TI", salva.getDescricao());
    }

    @Test
    @DisplayName("Deve buscar uma categoria por ID e validar seus atributos")
    void deveBuscarPorId() {
        // 1. Cenário: Criamos e salvamos primeiro para ter um registro real no banco com ID gerado
        Categoria categoria = new Categoria();
        categoria.setNome("Design");
        categoria.setDescricao("Workshops de UI e UX");
        Categoria salva = categoriaRepository.save(categoria);

        // 2. Ação: Buscamos pelo ID gerado
        Optional<Categoria> resultado = categoriaRepository.findById(salva.getId());

        // 3. Validação: Checamos se encontrou e validamos 2 atributos (nome e descrição)
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(salva.getId(), resultado.get().getId());
        Assertions.assertEquals("Design", resultado.get().getNome());
        Assertions.assertEquals("Workshops de UI e UX", resultado.get().getDescricao());
    }

    @Test
    @DisplayName("Deve listar as categorias cadastradas")
    void deveListarCategorias() {
        // 1. Cenário: Inserimos pelo menos duas categorias
        Categoria cat1 = new Categoria();
        cat1.setNome("Inteligência Artificial");
        cat1.setDescricao("Machine Learning e LLMs");
        categoriaRepository.save(cat1);

        Categoria cat2 = new Categoria();
        cat2.setNome("Cibersegurança");
        cat2.setDescricao("Defesa digital e perícia");
        categoriaRepository.save(cat2);

        // 2. Ação: Buscamos todos os registros
        List<Categoria> lista = categoriaRepository.findAll();

        // 3. Validação: A lista não pode estar vazia e precisa ter pelo menos 2 registros
        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Deve alterar uma categoria existente sem criar um novo registro")
    void deveAlterarCategoria() {
        // 1. Cenário: Salva uma categoria inicial
        Categoria categoria = new Categoria();
        categoria.setNome("Inovacao Aberta");
        categoria.setDescricao("Descricao antiga");
        Categoria salva = categoriaRepository.save(categoria);
        Integer idOriginal = salva.getId();

        // 2. Ação: Alteramos atributos do MESMO objeto (mantendo o mesmo ID) e chamamos save()
        salva.setNome("Inovação e Startups");
        salva.setDescricao("Descricao atualizada e revisada");
        categoriaRepository.save(salva);

        // 3. Validação: Buscamos do banco novamente para comprovar a alteração persistida
        Optional<Categoria> resultado = categoriaRepository.findById(idOriginal);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(idOriginal, resultado.get().getId()); // Garante que o ID não mudou (não duplicou)
        Assertions.assertEquals("Inovação e Startups", resultado.get().getNome());
        Assertions.assertEquals("Descricao atualizada e revisada", resultado.get().getDescricao());
    }

    @Test
    @DisplayName("Deve excluir uma categoria pelo ID")
    void deveExcluirCategoria() {
        // 1. Cenário: Salva e confirma com assert que ele de fato existe antes da deleção
        Categoria categoria = new Categoria();
        categoria.setNome("DevOps Provisório");
        categoria.setDescricao("Para testar deleção");
        Categoria salva = categoriaRepository.save(categoria);
        Integer id = salva.getId();

        Assertions.assertTrue(categoriaRepository.findById(id).isPresent());

        // 2. Ação: Exclui pelo ID
        categoriaRepository.deleteById(id);

        // 3. Validação: Confirma que agora a busca não retorna nada
        Optional<Categoria> resultado = categoriaRepository.findById(id);
        Assertions.assertFalse(resultado.isPresent());
    }
}
