package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.daoimpl.ProfileUpdateDAO;
import com.niit.model.ProfilePicture;
import com.niit.model.UserDetail;

@RestController
public class ProfilePictureController {
	@Autowired
	ProfileUpdateDAO profileUpdateDAO;
	
	/*@RequestMapping(value="/uploadProfilePicture",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(@RequestParam(value="image")  CommonsMultipartFile image,HttpSession session){
		try{
			UserDetail userDetail=(UserDetail)session.getAttribute("loginname");
			if(userDetail==null){
	    		return new ResponseEntity<String>("Unauthorized access",HttpStatus.UNAUTHORIZED);
	    	}
			ProfilePicture profilePicture=new ProfilePicture();
			profilePicture.setLoginname(userDetail.getLoginname());
			profilePicture.setImage(image.getBytes());
			profilePictureDAO.insertOrUpdateProfilePicture(profilePicture);
			return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
    	}
		catch(Exception e){
    		System.out.print(e);
    		return new ResponseEntity<String>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@RequestMapping(value="/getProfilePicture/{loginname}",method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePicture(@PathVariable String loginname){
			ProfilePicture profilePicture=profilePictureDAO.getProfilePicture(loginname);
			if(profilePicture==null)
				return null;
			return profilePicture.getImage();
    	
	}*/
	@RequestMapping(value="/doUpload",method=RequestMethod.POST)
	public ResponseEntity<?> uploadPicture(@RequestParam(value="file")CommonsMultipartFile fileupload,HttpSession session)
	{
	
		UserDetail userDetail=(UserDetail)session.getAttribute("userdetail");
		
		if(userDetail==null) 
		{
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		else
		{
			System.out.println("Uploading the picture..");
			ProfilePicture profilePicture=new ProfilePicture();
			profilePicture.setLoginname(userDetail.getLoginname());
			profilePicture.setImage(fileupload.getBytes());
			profileUpdateDAO.save(profilePicture);
			System.out.println("Successfully uploaded..!!");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getImage/{loginname}")
	public @ResponseBody byte[] getProfilePic(@PathVariable("loginame") String loginname)
	{
		
		ProfilePicture profilePicture=profileUpdateDAO.getProfilePicture(loginname);
		
		if(profilePicture==null)
		{
			return null;
		}
		else
		{
			return profilePicture.getImage();
		}
	}
	
}