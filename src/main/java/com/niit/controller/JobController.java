package com.niit.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDAO;
import com.niit.model.ApplyJob;
import com.niit.model.Job;
@RestController
public class JobController {
	@Autowired
	JobDAO jobDAO;


	// ---------------- Add Job -----------------------------------

	@PostMapping(value = "/addJob")
	public ResponseEntity<String> addJob(@RequestBody Job job) {
		job.setLastDateToApply(new Date());
		if (jobDAO.addJob(job)) {
			System.out.println("==========>Job details added successfully..");
			return new ResponseEntity<String>("Job Added- Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Job insert failed", HttpStatus.NOT_FOUND);
		}
	}

	// -----------------list Jobs ---------------------------------

	@GetMapping(value = "/listJobs")
	public ResponseEntity<List<Job>> listJob() {
		List<Job> listJobs = jobDAO.listAllJobs();
		System.out.println("==========> Job details retrieved<==========");
		if (listJobs.size() != 0) {
			return new ResponseEntity<List<Job>>(listJobs, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Job>>(listJobs, HttpStatus.NOT_FOUND);
		}
	}

	// ------------------Update Job -----------------------------------

	@PutMapping(value = "/updateJob/{jobId}")
	public ResponseEntity<String> updateJob(@PathVariable("jobId") int jobId, @RequestBody Job job) {
		System.out.println("Updating Job " + jobId);
		Job ujob = jobDAO.getJob(jobId);
		if (ujob == null) {
			System.out.println("Job with jobId " + jobId + " Not Found");
			return new ResponseEntity<String>("Update Job Failue", HttpStatus.NOT_FOUND);
		}
		
		ujob.setJobId(jobId);
		ujob.setCompany(job.getCompany());
		ujob.setLastDateToApply(new Date());
		ujob.setJobDescription(job.getJobDescription());
		ujob.setJobDesignation(job.getJobDesignation());
		ujob.setLocation(job.getLocation());
		jobDAO.updateJob(ujob);
		System.out.println("==========>Updated job details <===========");
		return new ResponseEntity<String>("Update Job Success", HttpStatus.OK);
	}

	// -----------------------Get Job ------------------------------------

	@GetMapping(value = "/getJob/{jobId}")
	public ResponseEntity<Job> getJob(@PathVariable("jobId") int jobId) {
		System.out.println("Get Job " + jobId);
		Job job = jobDAO.getJob(jobId);
		if (job == null) {
			System.out.println("Job retrieval failure..");
			return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
		} else {
			System.out.println("<==========Job retrieved========>");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
	}
	
	// ---------------- Apply Job -----------------------------------

		@PostMapping(value = "/applyJob")
		public ResponseEntity<String> addJob(@RequestBody ApplyJob applyJob) {
			applyJob.setAppliedDate(new Date());
			applyJob.setJobId(2);
			if (jobDAO.applyJob(applyJob)) {
				System.out.println("==========> ApplyJob details added successfully..");
				return new ResponseEntity<String>("ApplyJob Added- Success", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Applyjob failed", HttpStatus.NOT_FOUND);
			}
		}
		// -----------------list applied Jobs ---------------------------------

		@GetMapping(value = "/listAppliedJobs")
		public ResponseEntity<List<ApplyJob>> listAppliedJob() {
			List<ApplyJob> listAppliedJobs = jobDAO.getAllAppliedJobDetails();
			System.out.println("==========> Job details retrieved<==========");
			if (listAppliedJobs.size() != 0) {
				return new ResponseEntity<List<ApplyJob>>(listAppliedJobs, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<ApplyJob>>(listAppliedJobs, HttpStatus.NOT_FOUND);
			}
		}

}
