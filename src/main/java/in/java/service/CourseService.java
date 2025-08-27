package in.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.java.entities.Course;

@Service
public interface CourseService {

	public List<Course> getAllCourses();

}
