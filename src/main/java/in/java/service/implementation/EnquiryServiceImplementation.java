package in.java.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.java.dto.EnquiryDto;
import in.java.dto.EnquiryFilterRequestDto;
import in.java.entities.Counsellor;
import in.java.entities.Course;
import in.java.entities.Enquiry;
import in.java.repo.CounsellorRepo;
import in.java.repo.CourseRepo;
import in.java.repo.EnquiryRepo;
import in.java.service.EnquiryService;

@Service
public class EnquiryServiceImplementation implements EnquiryService {

	@Autowired
	private EnquiryRepo enquiryRepo;

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public boolean insertEnquiry(EnquiryDto enquiryDto, Integer counsellorId) {
		Counsellor counsellor = counsellorRepo.findById(counsellorId)
				.orElseThrow(() -> new RuntimeException("Counsellor not found"));
		Course course = courseRepo.findById(enquiryDto.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course not found"));
		Enquiry enquiry = new Enquiry();

		// Copy the data from EnquiryDto to Enquiry
		BeanUtils.copyProperties(enquiryDto, enquiry);
		enquiry.setCounsellor(counsellor);
		enquiry.setCourse(course);
		Enquiry saveEnquiry = enquiryRepo.save(enquiry);
		return saveEnquiry.getEnquiryId() != null;
	}

	@Override
	public boolean updateEnquiry(EnquiryDto enquiryDto) {
		Optional<Enquiry> byId = enquiryRepo.findById(enquiryDto.getEnquiryId());
		if (byId.isPresent()) {
			Enquiry enquiry = byId.get();
			enquiry.setEnquiryStatus(enquiryDto.getEnquiryStatus());
			enquiryRepo.save(enquiry);
			return true;
		}
		return false;
	}

	@Override
	public Enquiry getEnquiry(Integer enquiryId) {
		return enquiryRepo.findById(enquiryId).orElseThrow();
	}

	@Override
	public List<Enquiry> getAllEnquiry(Integer counsellorId) {
		return enquiryRepo.findByCounsellorCounsellorId(counsellorId);
	}

	@Override
	public List<Enquiry> getEnquiryByFilter(EnquiryFilterRequestDto filterDto, Integer counsellorId) {
		Enquiry enquiry = new Enquiry();
		Counsellor counsellor = counsellorRepo.findById(counsellorId)
				.orElseThrow(() -> new RuntimeException("Counsellor not found"));

		enquiry.setCounsellor(counsellor);

		if (filterDto.getClassMode() != null && !filterDto.getClassMode().isEmpty()) {
			enquiry.setClassMode(filterDto.getClassMode());
		}

		if (filterDto.getEnquiryStatus() != null && !filterDto.getEnquiryStatus().isEmpty()) {
			enquiry.setEnquiryStatus(filterDto.getEnquiryStatus());
		}

		if (filterDto.getCourseId() != null && filterDto.getCourseId() > 0) {
			Course course = courseRepo.findById(filterDto.getCourseId())
					.orElseThrow(() -> new RuntimeException("Course not found"));
			enquiry.setCourse(course);
		}

		return enquiryRepo.findAll(Example.of(enquiry));

//		List<Enquiry> enquiries = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

//		return enquiries.stream()
//				.filter(e -> filter.getEnquiryStatus() == null
//						|| e.getEnquiryStatus().equalsIgnoreCase(filter.getEnquiryStatus()))
//				.filter(e -> filter.getClassMode() == null || e.getClassMode().equalsIgnoreCase(filter.getClassMode()))
//				.filter(e -> filter.getCourseName() == null
//						|| e.getCourse().getCourseName().equalsIgnoreCase(filter.getCourseName()))
//				.collect(Collectors.toList());
	}

	@Override
	public List<Course> getCourses() {
		return courseRepo.findAll();
	}
}
