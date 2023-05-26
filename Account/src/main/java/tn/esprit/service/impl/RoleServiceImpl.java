package tn.esprit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.model.Role;
import tn.esprit.repository.RoleRepository;
import tn.esprit.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<Role> getAllRoles() {
		
		return roleRepo.findAll();
	}

}
