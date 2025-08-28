package in.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.java.dto.EnquiryDto;
import in.java.dto.EnquiryFilterRequestDto;
import in.java.entities.Course;
import in.java.entities.Enquiry;

@Service
public interface EnquiryService {

	public boolean insertEnquiry(EnquiryDto enquiryDto, Integer counsellorId);

	public boolean updateEnquiry(EnquiryDto enquiryDto);

	public List<Enquiry> getAllEnquiry(Integer counsellorId);

	public List<Enquiry> getEnquiryByFilter(EnquiryFilterRequestDto filter, Integer counsellorId);

	public Enquiry getEnquiry(Integer enquiryId);

	public List<Course> getCourses();

	boolean deleteEnquiry(Integer enquiryId, Integer counsellorId);
}
