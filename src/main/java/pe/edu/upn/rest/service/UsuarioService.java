package pe.edu.upn.rest.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upn.rest.model.entity.Usuario;


public interface UsuarioService extends CrudService<Usuario, Long> {
	Optional<Usuario> findByUsername(String username) throws Exception;
	
	
	List<Usuario> listacamarero() throws Exception; 
	
	
}
