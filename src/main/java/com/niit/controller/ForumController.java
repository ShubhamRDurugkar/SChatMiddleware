package com.niit.controller;

import java.util.Date;
import java.util.List;

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

import com.niit.dao.ForumDAO;
import com.niit.model.Forum;
import com.niit.model.ForumComment;

@RestController
public class ForumController {

	@Autowired
	ForumDAO forumDAO;


	// ---------------- Add Forum -----------------------------------

	@PostMapping(value = "/addForum")
	public ResponseEntity<String> addForum(@RequestBody Forum forum) {
		forum.setCreatedDate(new Date());
		forum.setStatus("A");
		if (forumDAO.addForum(forum)) {
			return new ResponseEntity<String>("Forum Added- Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Forum insert failed", HttpStatus.NOT_FOUND);
		}
	}

	// -----------------list Forums ---------------------------------

	@GetMapping(value = "/listForums")
	public ResponseEntity<List<Forum>> listForum() {
		List<Forum> listForums = forumDAO.listForum("shubhamRD");
		if (listForums.size() != 0) {
			return new ResponseEntity<List<Forum>>(listForums, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Forum>>(listForums, HttpStatus.NOT_FOUND);
		}
	}

	// ------------------Update Forum -----------------------------------

	@PutMapping(value = "/updateForum/{forumId}")
	public ResponseEntity<String> updateForum(@PathVariable("forumId") int forumId, @RequestBody Forum forum) {
		System.out.println("Updating Forum " + forumId);
		Forum uforum = forumDAO.getForum(forumId);
		if (uforum == null) {
			System.out.println("Forum with forumId " + forumId + " Not Found");
			return new ResponseEntity<String>("Update Forum Failue", HttpStatus.NOT_FOUND);
		}
		
		uforum.setForumContent(forum.getForumContent());
		uforum.setForumName(forum.getForumName());
		uforum.setCreatedDate(new Date());
		uforum.setStatus(forum.getStatus());
		uforum.setLoginname(forum.getLoginname());
		forumDAO.updateForum(uforum);
		return new ResponseEntity<String>("Update Forum Success", HttpStatus.OK);
	}

	// -----------------------Get Forum ------------------------------------

	@GetMapping(value = "/getForum/{forumId}")
	public ResponseEntity<Forum> getForum(@PathVariable("forumId") int forumId) {
		System.out.println("Get Forum " + forumId);
		Forum forum = forumDAO.getForum(forumId);
		if (forum == null) {
			System.out.println("Forum retrieval failure..");
			return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
		} else {
			System.out.println("<==========Forum retrieved========>");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}

	// -----------------------Approve Forum ----------------------------------

	@PutMapping(value = "/approveForum/{forumId}")
	public ResponseEntity<String> approveForum(@PathVariable("forumId") int forumId) {
		System.out.println("Approve Forum with Forum ID: " + forumId);
		Forum forum = forumDAO.getForum(forumId);
		if (forum == null) {
			System.out.println("Not forum with forum Id: " + forumId + " found for Approval");
			return new ResponseEntity<String>("No Forum found for Approval", HttpStatus.NOT_FOUND);
		} else {
			forum.setStatus("A");
			forumDAO.approveForum(forum);
			return new ResponseEntity<String>("Forum " + forumId + " Approved Successfully", HttpStatus.OK);
		}
	}

	// --------------------Reject Forum ----------------------------------

	@PutMapping(value = "/rejectForum/{forumId}")
	public ResponseEntity<String> rejectForum(@PathVariable("forumId") int forumId) {
		System.out.println("Reject Forum with Forum ID: " + forumId);
		Forum forum = forumDAO.getForum(forumId);
		if (forum == null) {
			System.out.println("Not forum with forum Id: " + forumId + " found for Approval");
			return new ResponseEntity<String>("No Forum with Forum Id " + forumId + " found for Disapproval",
					HttpStatus.NOT_FOUND);
		} else {
			forum.setStatus("NA");
			forumDAO.rejectForum(forum);
			return new ResponseEntity<String>("Forum " + forumId + " Disapproved Successfully", HttpStatus.OK);
		}
	}

	// -------------------------Delete Forum ---------------------

	@DeleteMapping(value = "/deleteForum/{forumId}")
	public ResponseEntity<String> deleteForum(@PathVariable("forumId") int forumId) {
		System.out.println("Delete forum with forum Id: " + forumId);
		Forum forum = forumDAO.getForum(forumId);
		if (forum == null) {
			System.out.println("No forum " + forumId + " found to delete");
			return new ResponseEntity<String>("No forum with forum Id: " + forumId + " found to delete",
					HttpStatus.NOT_FOUND);
		} else {
			forumDAO.deleteForum(forum);
			return new ResponseEntity<String>("Forum with Forum Id " + forumId + " deleted successfully", HttpStatus.OK);
		}

	}
	
	// ---------------- Add ForumComments -----------------------------------

		@PostMapping(value = "/addForumComment")
		public ResponseEntity<String> addForumComments(@RequestBody ForumComment forumComment) {
			
			System.out.println("In AddForumComment() method");
			forumComment.setForumId(forumComment.getForumId());
			forumComment.setCommentDate(new Date());
			forumComment.setCommentText(forumComment.getCommentText());
			forumComment.setLoginname(forumComment.getLoginname());
		
			/*Forum forum = forumDAO.getForum(1);
			String username = forum.getUsername();
			int forumId = forum.getForumId();
			forumComment.setForumId(forumId);
			forumComment.setUsername(username);*/
			
			if (forumDAO.addForumComment(forumComment)) {
				return new ResponseEntity<String>("ForumComment Added- Success", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("ForumComment insert failed", HttpStatus.NOT_FOUND);
			}
		}

		// -------------------------Delete ForumComment	 ---------------------

		@DeleteMapping(value = "/deleteForumComment/{commentId}")
		public ResponseEntity<String> deleteForumComment(@PathVariable("commentId") int commentId) {
			System.out.println("Delete forumComment with comment Id: " + commentId);
			ForumComment forumComment = forumDAO.getForumComment(commentId);
			if (forumComment == null) {
				System.out.println("No forum " + commentId + " found to delete");
				return new ResponseEntity<String>("No forumcomment with comment Id: " + commentId + " found to delete",
						HttpStatus.NOT_FOUND);
			} else {
				forumDAO.deleteForumComment(forumComment);
				return new ResponseEntity<String>("ForumComment with comment Id " + commentId + " deleted successfully", HttpStatus.OK);
			}

		}
		// -----------------------Get ForumComment ------------------------------------

		@GetMapping(value = "/getForumComment/{commentId}")
		public ResponseEntity<ForumComment> getForumComment(@PathVariable("commentId") int commentId) {
			System.out.println("Get ForumComment " + commentId);
			ForumComment forumComment = forumDAO.getForumComment(commentId);
			if (forumComment == null) {
				return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
			}
		}

		// -----------------list Forums ---------------------------------
		@GetMapping(value = "/listForumComments")
		public ResponseEntity<List<ForumComment>> listForumComments() {
			System.out.println("In listForumComments() method");
			List<ForumComment> listForumComments = forumDAO.listForumComments(1);
			if (listForumComments.size() != 0) {
				return new ResponseEntity<List<ForumComment>>(listForumComments, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<ForumComment>>(listForumComments, HttpStatus.NOT_FOUND);
			}
		}
		

}
