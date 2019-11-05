package pe.edu.upn.rest.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upn.rest.model.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsername(String username);
	
	
	@Query(value="select * from users where username='camarero'", nativeQuery=true)
	List<Usuario> listacamarero();
	
	
}
