package in.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.java.entities.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

}
