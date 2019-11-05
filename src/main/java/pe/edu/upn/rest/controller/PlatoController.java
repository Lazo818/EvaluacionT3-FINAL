package pe.edu.upn.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upn.rest.model.entity.Pedido;
import pe.edu.upn.rest.model.entity.Plato;
import pe.edu.upn.rest.model.entity.Tipo;
import pe.edu.upn.rest.model.entity.Usuario;
import pe.edu.upn.rest.service.PedidoService;
import pe.edu.upn.rest.service.PlatoService;
import pe.edu.upn.rest.service.TipoService;
import pe.edu.upn.rest.service.UsuarioService;

@Controller
@RequestMapping("/plato")
@SessionAttributes( {"plato" ,"pedido" , "usuario"} )
public class PlatoController {

	@Autowired
	private PlatoService platoService;

	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public String inicio(Model model) {
		try {
			List<Plato> plato = platoService.findAll();
			model.addAttribute("platos", plato);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/plato/inicio";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<Plato> optional = platoService.findById(id);
			if (optional.isPresent()) {
				List<Tipo> tipos = tipoService.findAll();
				model.addAttribute("plato", optional.get());
				model.addAttribute("tipos", tipos);
			} else {
				return "redirect:/plato";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/plato/edit";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("plato") Plato plato, Model model, SessionStatus status) {
		try {
			platoService.save(plato);
			status.setComplete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/plato";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		Plato plato = new Plato();
		model.addAttribute("plato", plato);
		try {
			List<Tipo> tipos = tipoService.findAll();
			model.addAttribute("tipos", tipos);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/plato/nuevo";
	}

	@GetMapping("/del/{id}")
	public String eliminar(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<Plato> plato = platoService.findById(id);
			if (plato.isPresent()) {
				platoService.deleteById(id);
			}
		} catch (Exception e) {
			model.addAttribute("dangerDel", "ERROR");
			try {
				List<Plato> platos = platoService.findAll();
				model.addAttribute("platos", platos);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return "/plato/inicio";
		}
		return "redirect:/plato";
	}

	
	
	@GetMapping("/info/{id}")
	private String infos(@PathVariable("id") Integer id, Model model , Model model1) {
		Pedido pedido = new Pedido();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Plato> plato = platoService.findById(id);
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if (plato.isPresent()) {
				if(optional.isPresent()) {
					model.addAttribute("usuario", optional.get());
					pedido.setUsuario(optional.get());
					
					model.addAttribute("plato", plato.get());
					pedido.setPlato(plato.get());
					
					model.addAttribute("pedido", pedido);
				}
				
				
			} else {
				return "redirect:/plato/inicio";
			}
		} catch (Exception e) {

		}
		
		return "/plato/info";
	}
	
	@PostMapping("/savepedido")
	private String save(@ModelAttribute("pedido") Pedido pedido, SessionStatus status , Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			Optional<Usuario> optional = usuarioService.findByUsername(username);
			if(optional.isPresent()) {
				model.addAttribute("usuario", optional.get());
			}
			pedidoService.save(pedido);
			status.setComplete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/pedido";
		
	}
}
