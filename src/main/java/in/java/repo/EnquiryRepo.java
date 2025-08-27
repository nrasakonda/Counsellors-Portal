package in.java.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.java.entities.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {

	public List<Enquiry> findByCounsellorCounsellorId(Integer counsellorId);
}
