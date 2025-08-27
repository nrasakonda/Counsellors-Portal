package in.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.java.dto.DashboardResponceDto;
import in.java.entities.Counsellor;
import in.java.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;

	@GetMapping("/")
	public String index(Model model) {
		Counsellor counsellorObj = new Counsellor();
		model.addAttribute("counsellor", counsellorObj);
		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(Counsellor counsellor, Model model, HttpServletRequest requiest) {
		Counsellor login = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		if (login == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		} else {
			HttpSession session = requiest.getSession(true);
			session.setAttribute("CID", login.getCounsellorId());
			return "redirect:/dashboard";
		}
	}

	@GetMapping("/register")
	public String register(Model model) {
		Counsellor counsellorObj = new Counsellor();
		model.addAttribute("counsellor", counsellorObj);
		return "register";
	}

	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {
		Boolean register = counsellorService.register(counsellor);
		if (register) {
			model.addAttribute("smsg", "Registration success");
		} else {
			model.addAttribute("emsg", "Registration failed");
		}
		return "register";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("CID");
		DashboardResponceDto dashboardInfoObj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboard", dashboardInfoObj);
		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/";
	}

}
