package com.accenture.springboot.rest.service;


import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.accenture.springboot.rest.Models.User;
import com.accenture.springboot.rest.Repo.UserRepo;

@Service
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepo userRepo;
	
	public List<User> findAll() {
		logger.info("List of ALL User Info..Request is accepted");
		return userRepo.findAll();
	}
	
	public User findById(@PathVariable long id) {
	logger.info("Find User By ID..Request is accepted");
		return userRepo.findById(id).orElse(null);
	}
	
	public List<User> getUserByFirstName(@PathVariable String firstName) {
		logger.info("getUserByFirstName..Request is accepted");
		return userRepo.findByFirstName(firstName);
	}
	
	public String saveUser(User user) {
		logger.info("saveUser..Request is accepted");
		logger.debug(" New User Detail Passed: user id: " + user.getId() + " FirstName: " + user.getFirstName()
				+ "LastName: " + user.getLastName() + "Age: " + user.getAge() + "Email:" + user.getEmail()
				+ "Occupation :" + user.getOccupation());
		int retVar = validate(user);
		if (retVar == 0) {
			logger.error("Error...Required proper Email Id " + user.getEmail());
			return "Enter valid EmailId";
		} else if (retVar == 1) {
			logger.error("Error...Required proper AGE " + user.getAge());
			return "Enter valid  Age";
		} else if (retVar == 2) {
			logger.error("Error...Required Value for all fields");
			return "Required Value for all fields";
		}
		try {
			userRepo.save(user);
			logger.debug(" New User Detail Saved: user id: " + user.getId() + " FirstName: " + user.getFirstName()
					+ "LastName: " + user.getLastName() + "Age: " + user.getAge() + "Email:" + user.getEmail()
					+ "Occupation :" + user.getOccupation());
			return "Saved";
		} catch (DataIntegrityViolationException e) {
			logger.fatal("Warning..User already exists");
			return "User already Exists";
		} catch (Exception e) {
			logger.fatal("Fatal..Exception Occured");
			return "Exception Occured";
		}
	}
	
	public String updateUser(@PathVariable long id, User user) {
		logger.info("Update..Request is accepted");
		logger.debug(" New User Detail Passed: user id: " + user.getId() + " FirstName: " + user.getFirstName()
				+ "LastName: " + user.getLastName() + "Age: " + user.getAge() + "Email:" + user.getEmail()
				+ "Occupation :" + user.getOccupation());
		int retVar = validate(user);
		if (retVar == 0) {
			logger.error("Error...Required proper emailid " + user.getEmail());
			return "Enter valid Email Id";
		} else if (retVar == 1) {
			logger.error("Error...Required proper AGE " + user.getAge());
			return "Enter valid  Age";
		} else if (retVar == 2) {
			logger.error("Error...Required Value for all fields");
			return "Required Value for all fields";
		}
		try {

			User updatedUser = userRepo.findById(id).get();
			updatedUser.setFirstName(user.getFirstName());
			updatedUser.setLastName(user.getLastName());
			updatedUser.setOccupation(user.getOccupation());
			updatedUser.setEmail(user.getEmail());
			updatedUser.setAge(user.getAge());
			userRepo.save(updatedUser);
			logger.debug(" User Detail Updated: user id: " + user.getId() + " FirstName: " + user.getFirstName()
					+ "LastName: " + user.getLastName() + "Age: " + user.getAge() + "Email:" + user.getEmail()
					+ "Occupation :" + user.getOccupation());
			return "User updated Successfully";
		} catch (DataIntegrityViolationException e) {
			logger.fatal("Warning..User already exists");
			return "User already Exists";
		} catch (Exception e) {
			logger.fatal("Fatal..Exception Occured");
			return "Exception Occured";
		}
	}

	public String deleteUsers(List<Long> ids) {
		logger.info("Info..Request is accepted");
		try {
	         userRepo.deleteUser(ids);	
			logger.debug("Deleted user with id" + ids);
			return "Success";
		} catch (Exception e) {
			logger.fatal("Fatal..Exception Occured");
			return "Failure";
		}
	}
	
	public Page<User> findPaginated(int pageNo,int pageSize){
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return this.userRepo.findAll(pageable);
	}

	

	public int validate(User user) {
		String email = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+$";
		Pattern thim = Pattern.compile(email);
		if(user.getEmail()==null) {
			return 0;
		}
		boolean sh = thim.matcher(user.getEmail()).matches();
		int retVar = -1;
		if (user.getFirstName() != "" && user.getLastName() != "" && user.getAge() > 0 && user.getAge() <= 120
				&& user.getOccupation() != "" && user.getEmail() != "" && sh == false) {
			retVar = 0;
		} else if (user.getFirstName() != "" && user.getLastName() != "" && (user.getAge() <= 0 || user.getAge() > 120)
				&& user.getEmail() != "" && user.getOccupation() != "" && sh == true) {
			retVar = 1;
		} else if (user.getFirstName() == "" || user.getFirstName() == null || user.getLastName() == ""
				|| user.getLastName() == null || user.getAge() <= 0 || user.getAge() > 120 || user.getOccupation() == ""
				|| user.getOccupation() == null || user.getEmail() == ""
						|| user.getEmail() == null && sh == false) {
			retVar = 2;
		}
		return retVar;
	}
}
