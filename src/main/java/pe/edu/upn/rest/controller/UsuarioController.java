package pe.edu.upn.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upn.rest.model.entity.Usuario;
import pe.edu.upn.rest.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes({"usuario"})
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	@GetMapping
	public String inicio(Model model) {
		try {
			List<Usuario> usuario = usuarioService.findAll();
			model.addAttribute("usuarios", usuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/usuario/inicio";
	}
	
	
	@GetMapping("/camarero")
	public String camarero(Model model) {
		try {
			List<Usuario> usuario = usuarioService.listacamarero();
			model.addAttribute("usuarios", usuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/usuario/camarero";
	}
	
	
	@GetMapping("/register")
	public String register(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "/usuario/register";
	}
	
	@GetMapping("/del/{id}")
	public String eliminar(@PathVariable("id") Long id, Model model) {
		try {
			Optional<Usuario> usuario = usuarioService.findById(id);
			if(usuario.isPresent()) {
				usuarioService.deleteById(id);
			}
		} catch (Exception e) {
			
			model.addAttribute("dangerDel", "ERROR - Violación contra el principio de Integridad referencia");
			try {
				List<Usuario> usuario = usuarioService.findAll();
				model.addAttribute("medicos", usuario);
			} catch (Exception e2) {
				// TODO: handle exception
			} 
			return "/usuario/camerero";
		}
		return "redirect:/usuario/camarero";
	}
	
	
	
	
	@PostMapping("/save")
	public String save(@ModelAttribute("usuario") Usuario usuario, 
			Model model, SessionStatus status) {
		
		try {
			// Verificar que el username ya exista.
			Optional<Usuario> optional 
				= usuarioService.findByUsername(usuario.getUsername());
			if(optional.isPresent()) {
				model.addAttribute("dangerRegister"
						, "ERROR - El username " 
							+ usuario.getUsername() 
							+ " ya existe ");
				return "/usuario/register";
			} else {
				usuario.setPassword(passwordEncoder
						.encode( usuario.getPassword() ));
				usuario.addAuthority("ROLE_CLIENTE");
				usuarioService.save(usuario);
				status.setComplete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/login";
	}
}









