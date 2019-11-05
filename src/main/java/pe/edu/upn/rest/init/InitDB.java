package pe.edu.upn.rest.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upn.rest.model.entity.Usuario;
import pe.edu.upn.rest.model.repository.AuthorityRepository;
import pe.edu.upn.rest.model.repository.UsuarioRepository;

@Service
public class InitDB implements CommandLineRunner{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		this.usuarioRepository.deleteAll();
		this.authorityRepository.deleteAll();
		
		Usuario invitado = new Usuario();
		invitado.setUsername("invitado");
		invitado.setPassword(passwordEncoder.encode("invitado"));
		invitado.setApellidos("Flores");
		invitado.setNombres("invitado");
		invitado.setEnable(true);
		

		Usuario admin = new Usuario();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setApellidos("General");
		admin.setNombres("admin");
		admin.setEnable(true);
		
        Usuario camarero = new Usuario();
        camarero.setUsername("camarero");
        camarero.setPassword(passwordEncoder.encode("camarero"));
        camarero.setApellidos("Flores");
        camarero.setNombres("Juan");
        camarero.setEnable(true);
        
        Usuario camarero2 = new Usuario();
        camarero2.setUsername("camarero2");
        camarero2.setPassword(passwordEncoder.encode("camarero2"));
        camarero2.setApellidos("Flores");
        camarero2.setNombres("Juan");
        camarero2.setEnable(true);
        
       invitado.addAuthority("ROLE_INVITADO");
        invitado.addAuthority("ACCESS_MEDICO_READ");
        
        
        admin.addAuthority("ROLE_GERENTE");
        admin.addAuthority("ACCESS_REST1");
        admin.addAuthority("ACCESS_REST2");
        
        
        camarero.addAuthority("ROLE_CAMARERO");
        camarero.addAuthority("ACCESS_REST1");
        camarero2.addAuthority("ROLE_CAMARERO");
        camarero2.addAuthority("ACCESS_REST1");
        
        List<Usuario> usuarios = Arrays.asList(invitado ,admin, camarero,camarero2);        
        this.usuarioRepository.saveAll(usuarios);	
	}
}
