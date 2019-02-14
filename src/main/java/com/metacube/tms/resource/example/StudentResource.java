package com.metacube.tms.resource.example;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.metacube.tms.entity.example.Course;
import com.metacube.tms.service.example.StudentService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 16-Sep-2018
 */
@Slf4j
@RestController
public class StudentResource {
	
	public void testLogging(final String value) {
//        log.trace("doStuff needed more information - {}", value);
//        log.debug("doStuff needed to debug - {}", value);
//        log.info("doStuff took input - {}", value);
//        log.warn("doStuff needed to warn - {}", value);
//        log.error("doStuff encountered an error with value - {}", value);
    }

	@Autowired
	private StudentService studentService;

	@GetMapping("/students/{studentId}/courses")
	public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
		this.testLogging(studentId); //TODO remove
		return studentService.retrieveCourses(studentId);
	}

	@GetMapping("/students/{studentId}/courses/{courseId}")
	public Course retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId) {
		return studentService.retrieveCourse(studentId, courseId);
	}

	@PostMapping("/students/{studentId}/courses")
	public ResponseEntity<Void> registerStudentForCourse(@PathVariable String studentId,
			@RequestBody Course newCourse) {

		Course course = studentService.addCourse(studentId, newCourse);

		if (course == null) {
			return ResponseEntity.noContent().build();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}
