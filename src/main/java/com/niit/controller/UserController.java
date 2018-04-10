package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDetailDAO;
import com.niit.model.UserDetail;

@RestController
public class UserController {

	@Autowired
	UserDetailDAO userDAO;

	//------------------CheckLogin-----------------
	@PostMapping(value="/login")
	public ResponseEntity<UserDetail> checkLogin(@RequestBody UserDetail userDetail,HttpSession session)
	{
		if(userDAO.checkLogin(userDetail))
		{
			UserDetail tempUser=(UserDetail)userDAO.getUser(userDetail.getLoginname());
			userDAO.updateOnlineStatus("Y", tempUser);
			session.setAttribute("userdetail",tempUser);
			return new ResponseEntity<UserDetail>(tempUser,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(userDetail,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// ----------------- Register User ---------------
	@PostMapping(value = "/registerUser")
	public ResponseEntity<UserDetail> registerUser(@RequestBody UserDetail user) {
		user.setIsOnline("N");
		user.setRole("ROLE_USER");
		if (userDAO.registerUser(user)) {
			return new ResponseEntity<UserDetail>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDetail>(user, HttpStatus.NOT_FOUND);
		}
	}

	// ----------- Update User -----------------------------
	@PutMapping(value = "/updateUser/{loginname}")
	public ResponseEntity<String> updateUser(@PathVariable("loginname") String loginname, @RequestBody UserDetail userDetail) {
		System.out.println("In updating user " + loginname);
		UserDetail mUser = userDAO.getUser(loginname);
		if (mUser == null) {
			System.out.println("No user found with loginname " + loginname);
			return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
		}

	/*	mUser.setEmailId(userDetail.getEmailId());
		mUser.setMobileNo(userDetail.getMobileNo());
		mUser.setAddress(userDetail.getAddress());*/
		mUser.setUsername(userDetail.getUsername());
		userDAO.updateUser(mUser);
		return new ResponseEntity<String>("User updated successfully", HttpStatus.OK);
	}

	// --------------------- List Users --------------------------
	@GetMapping(value = "/listUsers")
	public ResponseEntity<List<UserDetail>> listUsers() {
		List<UserDetail> listUsers = userDAO.listUsers();
		if (listUsers.size() != 0) {
			return new ResponseEntity<List<UserDetail>>(listUsers, HttpStatus.OK);
		}
		return new ResponseEntity<List<UserDetail>>(listUsers, HttpStatus.NOT_FOUND);
	}

	// --------------------- Get User ----------------------
	@GetMapping(value = "/getUser/{loginname}")
	public ResponseEntity<UserDetail> getUser(@PathVariable("loginname") String loginname) {
		UserDetail user = userDAO.getUser(loginname);
		if (user == null) {
			System.out.println("No user found");
			return new ResponseEntity<UserDetail>(user, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<UserDetail>(user, HttpStatus.OK);
		}
	}

	//-----------------------Delete user-----------------------
	@DeleteMapping(value = "/deleteUser/{loginname}")
	public ResponseEntity<String> deleteUser(@PathVariable("loginname") String loginname) {
		System.out.println("In delete user" + loginname);
		UserDetail user = userDAO.getUser(loginname);
		if (user == null) {
			System.out.println("No user with LoginName " + loginname + " found to delete");
			return new ResponseEntity<String>("No user found to delete", HttpStatus.NOT_FOUND);
		} else {
			userDAO.deleteUser(user);
			return new ResponseEntity<String>("User with LoginName " + loginname + " deleted successfully", HttpStatus.OK);
		}
	}
	
	
	//--------------------------------------Upload image-----------------------------------------
	
}