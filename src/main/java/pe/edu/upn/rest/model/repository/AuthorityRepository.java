package pe.edu.upn.rest.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upn.rest.model.entity.Authority;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
