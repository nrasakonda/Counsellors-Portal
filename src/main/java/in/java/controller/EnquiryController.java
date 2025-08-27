package in.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.java.dto.EnquiryDto;
import in.java.entities.Course;
import in.java.entities.Enquiry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

    // Hardcoded course list
    private List<Course> courses = new ArrayList<>();

    public EnquiryController() {
        courses.add(new Course(1, "Java"));
        courses.add(new Course(2, "Python"));
        courses.add(new Course(3, "AWS"));
        courses.add(new Course(4, "Spring Boot"));
    }

    @GetMapping("/enquiry")
    public String loadAddEnquiryForm(Model model) {
        model.addAttribute("enquiryDto", new EnquiryDto());
        model.addAttribute("courses", courses);
        return "add-enq";
    }

    @PostMapping("/enquiry")
    public String addEnquiry(@ModelAttribute("enquiryDto") EnquiryDto enquiryDto,
                             HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");

        // Manually map courseId to Course object
        Course selectedCourse = courses.stream()
                                       .filter(c -> c.getCourseId().equals(enquiryDto.getCourseId()))
                                       .findFirst()
                                       .orElse(new Course(0, "Unknown"));

        Enquiry enquiry = new Enquiry();
        enquiry.setStudentName(enquiryDto.getStudentName());
        enquiry.setStudentPhno(enquiryDto.getStudentPhno());
        enquiry.setClassMode(enquiryDto.getClassMode());
        enquiry.setEnquiryStatus(enquiryDto.getEnquiryStatus());
        enquiry.setCourse(selectedCourse);
        enquiry.setCounsellorId(counsellorId); // add counsellor mapping if needed

        // Save logic (in memory or just skip DB persistence)
        // Example: store in session or static list
        List<Enquiry> allEnquiries = (List<Enquiry>) session.getAttribute("enquiries");
        if (allEnquiries == null) {
            allEnquiries = new ArrayList<>();
        }
        allEnquiries.add(enquiry);
        session.setAttribute("enquiries", allEnquiries);

        // Redirect to view page
        return "redirect:/view-enquiries";
    }

    @GetMapping("/view-enquiries")
    public String viewEnquiries(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        List<Enquiry> enqList = (List<Enquiry>) session.getAttribute("enquiries");
        if (enqList == null) {
            enqList = new ArrayList<>();
        }
        model.addAttribute("enquiries", enqList);
        return "view-enqs";
    }
}
