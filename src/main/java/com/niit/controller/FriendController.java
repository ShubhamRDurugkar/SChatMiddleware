package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.FriendDAO;
import com.niit.model.Friend;
import com.niit.model.UserDetail;

@RestController
public class FriendController {
	@Autowired
	FriendDAO friendDAO;
	
	@PostMapping(value="/sendFriendRequest")
	public ResponseEntity<String> sendFriendRequest(@RequestBody Friend friend)
	{
		if(friendDAO.sendFriendRequest(friend))
		{
			return new 	ResponseEntity<String>("Friend request sent..!!",HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("Failure to send friend request",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/deleteFriendRequest/{friendId}")
	public ResponseEntity<String> deleteFriendRequest(@PathVariable("friendId") int friendId)
	{
		if(friendDAO.deleteFriendRequest(friendId))
		{
			return new 	ResponseEntity<String>("Friend request deleted..!!",HttpStatus.OK);	
		}
		else
		{
			return new ResponseEntity<String>("Failure to delete friend request",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value="/acceptFriendRequest/{friendId}")
	public ResponseEntity<String> acceptFriendRequest(@PathVariable("friendId") int friendId)
	{
		if(friendDAO.acceptFriendRequest(friendId))
		{
			return new 	ResponseEntity<String>("Friend request accepted..!!",HttpStatus.OK);	
		}
		else
		{
			return new ResponseEntity<String>("Failure to accept friend request",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value="/showAllFriends")
	public ResponseEntity<List<Friend>> showAllFriends(HttpSession session)
	{
		//String loginname=((UserDetail)session.getAttribute(("userdetail"))).getLoginname();
		List<Friend> listAllFriends=friendDAO.showAllFriends("Shubham");
		if(listAllFriends.size()>0)
		{
			return new 	ResponseEntity<List<Friend>>(listAllFriends,HttpStatus.OK);	
		}
		else
		{
			return new ResponseEntity<List<Friend>>(listAllFriends,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value="/showPendingRequests")
	public ResponseEntity<List<Friend>> showPendingFriendRequests(HttpSession session)
	{
		//String loginname=((UserDetail)session.getAttribute(("userdetail"))).getLoginname();
		List<Friend> listPendingRequests=friendDAO.showRequestPendingList("Shubham");
		if(listPendingRequests.size()>0)
		{
			return new 	ResponseEntity<List<Friend>>(listPendingRequests,HttpStatus.OK);	
		}
		else
		{
			return new ResponseEntity<List<Friend>>(listPendingRequests,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
