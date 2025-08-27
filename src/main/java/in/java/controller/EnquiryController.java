package in.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.java.dto.EnquiryDto;
import in.java.entities.Course;
import in.java.entities.Counsellor;
import in.java.entities.Enquiry;
import in.java.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    // Hardcoded courses
    private List<Course> getHardcodedCourses() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course();
        c1.setCourseId(1);
        c1.setCourseName("Java");

        Course c2 = new Course();
        c2.setCourseId(2);
        c2.setCourseName("Python");

        Course c3 = new Course();
        c3.setCourseId(3);
        c3.setCourseName("Data Science");

        Course c4 = new Course();
        c4.setCourseId(4);
        c4.setCourseName("Spring Boot");

        Course c5 = new Course();
        c5.setCourseId(5);
        c5.setCourseName("React");

        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        courses.add(c4);
        courses.add(c5);

        return courses;
    }

    @GetMapping("/enquiry")
    public String loadAddEnquiryForm(Model model) {
        model.addAttribute("enquiryDto", new EnquiryDto());
        model.addAttribute("courses", getHardcodedCourses());
        return "add-enq";
    }

    @PostMapping("/enquiry")
    public String addEnquiry(@ModelAttribute("enquiryDto") EnquiryDto enquiryDto,
                             HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CID") == null) {
            model.addAttribute("emsg", "Session expired! Please login again.");
            model.addAttribute("courses", getHardcodedCourses());
            return "add-enq";
        }

        Integer counsellorId = (Integer) session.getAttribute("CID");

        // Create Enquiry entity
        Enquiry enquiry = new Enquiry();
        enquiry.setStudentName(enquiryDto.getStudentName());
        enquiry.setStudentPhno(enquiryDto.getStudentPhno());
        enquiry.setClassMode(enquiryDto.getClassMode());
        enquiry.setEnquiryStatus(enquiryDto.getEnquiryStatus());

        // Assign hardcoded course
        Course selectedCourse = getHardcodedCourses().stream()
                .filter(c -> c.getCourseId() == enquiryDto.getCourseId())
                .findFirst()
                .orElse(null);

        if (selectedCourse == null) {
            model.addAttribute("emsg", "Invalid course selected!");
            model.addAttribute("courses", getHardcodedCourses());
            return "add-enq";
        }

        enquiry.setCourse(selectedCourse);

        // Assign counsellor
        Counsellor c = new Counsellor();
        c.setId(counsellorId);  // assuming Counsellor entity has setId()
        enquiry.setCounsellor(c);

        boolean isSaved = enquiryService.insertEnquiry(enquiry);

        if (isSaved) {
            return "redirect:/view-enquiries";
        } else {
            model.addAttribute("emsg", "Failed to add Enquiry");
            model.addAttribute("courses", getHardcodedCourses());
            return "add-enq";
        }
    }

    @GetMapping("/view-enquiries")
    public String viewEnquiries(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CID") == null) {
            model.addAttribute("emsg", "Session expired! Please login again.");
            return "add-enq";
        }

        Integer counsellorId = (Integer) session.getAttribute("CID");
        List<Enquiry> enqList = enquiryService.getAllEnquiry(counsellorId);
        model.addAttribute("enquiries", enqList);

        return "view-enqs";
    }
}
