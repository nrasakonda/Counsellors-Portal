package in.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.java.dto.EnquiryDto;
import in.java.entities.Enquiry;
import in.java.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/enquiry")
	public String loadAddEnquiryForm(Model model) {

		model.addAttribute("enquiryDto", new EnquiryDto());
		model.addAttribute("courses", enquiryService.getCourses());
		return "add-enq";
	}

	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enquiryDto") EnquiryDto enquiryDto, HttpServletRequest request,
			Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("CID");

		boolean isSaved = enquiryService.insertEnquiry(enquiryDto, counsellorId);

		if (isSaved) {
			// ✅ redirect to view enquiries page
			return "redirect:/view-enquiries";
		} else {
			model.addAttribute("emsg", "Failed to add Enquiry");
			model.addAttribute("courses", enquiryService.getCourses());
			return "add-enq"; // stay on same page if error
		}
	}

	@GetMapping("/view-enquiries")
	public String viewEnquiries(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("CID");

		List<Enquiry> enqList = enquiryService.getAllEnquiry(counsellorId);

		model.addAttribute("enquiries", enqList);
		return "view-enqs";

	}

}
