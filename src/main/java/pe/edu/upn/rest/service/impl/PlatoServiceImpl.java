package pe.edu.upn.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upn.rest.model.entity.Plato;
import pe.edu.upn.rest.model.repository.PlatoRepository;
import pe.edu.upn.rest.service.PlatoService;

@Service
public class PlatoServiceImpl implements PlatoService {

	@Autowired
	private PlatoRepository platoRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Plato> findAll() throws Exception {
		return platoRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Plato> findById(Integer id) throws Exception {
		return platoRepository.findById(id);
	}

	@Transactional
	@Override
	public Plato save(Plato entity) throws Exception {
		return platoRepository.save(entity);
	}

	@Transactional
	@Override
	public Plato update(Plato entity) throws Exception {
		return platoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		platoRepository.deleteById(id);

	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		platoRepository.deleteAll();
	}

}
