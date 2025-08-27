package in.java.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.java.entities.Course;
import in.java.repo.CourseRepo;
import in.java.service.CourseService;

@Service
public class CourseServiceImplementation implements CourseService {
	
	@Autowired
	private CourseRepo courseRepo;

	@Override
	public List<Course> getAllCourses() {
		return courseRepo.findAll();
	}

}
