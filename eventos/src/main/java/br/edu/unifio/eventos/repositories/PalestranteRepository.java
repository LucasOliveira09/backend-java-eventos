package br.edu.unifio.eventos.repositories;

import br.edu.unifio.eventos.entities.Palestrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalestranteRepository extends JpaRepository<Palestrante, Integer> {
}
