package pe.edu.upn.rest.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upn.rest.model.entity.Plato;



@Repository
public interface PlatoRepository 
	extends JpaRepository<Plato, Integer> {

}
