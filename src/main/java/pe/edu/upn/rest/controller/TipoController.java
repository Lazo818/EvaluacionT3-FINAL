package pe.edu.upn.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upn.rest.model.entity.Tipo;
import pe.edu.upn.rest.service.TipoService;

@Controller
@RequestMapping("/tipo")
@SessionAttributes("tipo")
public class TipoController {
	
	@Autowired
	private TipoService tipoService;
	
	
	@GetMapping
	public String inicio(Model model) {
		try {
			List<Tipo> tipo = tipoService.findAll();
			model.addAttribute("tipos", tipo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/tipo/inicio";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<Tipo> optional = tipoService.findById(id);
			if (optional.isPresent()) {
				
				model.addAttribute("tipo", optional.get());
				
			} else {
				return "redirect:/tipo";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/tipo/edit";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("plato") Tipo tipo, Model model, SessionStatus status) {
		try {
			tipoService.save(tipo);
			status.setComplete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/tipo";
	}
	



	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		Tipo tipo = new Tipo();
		model.addAttribute("tipo", tipo);
		
		return "/tipo/nuevo";
	}

	@GetMapping("/del/{id}")
	public String eliminar(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<Tipo> tipo = tipoService.findById(id);
			if (tipo.isPresent()) {
				tipoService.deleteById(id);
			}
		} catch (Exception e) {
			model.addAttribute("dangerDel", "ERROR");
			try {
				List<Tipo> tipos = tipoService.findAll();
				model.addAttribute("tipos", tipos);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return "/tipo/inicio";
		}
		return "redirect:/tipo";
	}

	@GetMapping("/info/{id}")
	public String info(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<Tipo> tipo = tipoService.findById(id);
			if (tipo.isPresent()) {
				model.addAttribute("tipo", tipo.get());
			} else {
				return "redirect:/tipo";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/tipo/info";
	}
}