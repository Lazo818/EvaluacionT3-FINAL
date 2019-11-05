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

import pe.edu.upn.rest.model.entity.Pedido;
import pe.edu.upn.rest.service.PedidoService;


@Controller
@RequestMapping("/pedido")
@SessionAttributes( {"plato" ,"pedido" , "usuario"} )
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public String inicio(Model model) {
		try {
			List<Pedido> pedido = pedidoService.findAll();
			model.addAttribute("pedidos", pedido);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/pedido/inicio";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int id, Model model) {
		try {
			Optional<Pedido> optional = pedidoService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("pedido", optional.get());
			} else {
				return "redirect:/pedido";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/pedido/edit";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("pedido") Pedido pedido, Model model, SessionStatus status) {
		try {
			pedidoService.save(pedido);
			status.setComplete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/pedido";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		Pedido pedido= new Pedido();
		model.addAttribute("pedido", pedido);
		return "/pedido/nuevo";
	}

	@GetMapping("/del/{id}")
	public String eliminar(@PathVariable("id") int id, Model model) {
		try {
			Optional<Pedido> pedido= pedidoService.findById(id);
			if (pedido.isPresent()) {
				pedidoService.deleteById(id);
			}
		} catch (Exception e) {
			model.addAttribute("dangerDel", "ERROR");
			try {
				List<Pedido> pedidos= pedidoService.findAll();
				model.addAttribute("pedidos", pedidos);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return "/pedido/inicio";
		}
		return "redirect:/pedido";
	}

	@GetMapping("/info/{id}")
	public String info(@PathVariable("id") int id, Model model) {
		try {
			Optional<Pedido> pedido = pedidoService.findById(id);
			if (pedido.isPresent()) {
				model.addAttribute("pedido", pedido.get());
			} else {
				return "redirect:/pedido";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/pedido/info";
	}
}
