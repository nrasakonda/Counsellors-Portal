package in.java.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.java.dto.DashboardResponceDto;
import in.java.entities.Counsellor;
import in.java.entities.Enquiry;
import in.java.repo.CounsellorRepo;
import in.java.repo.EnquiryRepo;
import in.java.service.CounsellorService;

@Service
public class CounsellorServiceImplementation implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Autowired
	private EnquiryRepo enquiryRepo;

	@Override
	public Counsellor login(String email, String pwd) {
		return counsellorRepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public Boolean register(Counsellor counsellor) {
		Counsellor saveCounsellor = counsellorRepo.save(counsellor);
		return saveCounsellor.getCounsellorId() != null;
	}

	@Override
	public DashboardResponceDto getDashboardInfo(Integer counsellorId) {
		List<Enquiry> enquiryList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

		int totalCount = enquiryList.size();

		Map<String, Long> statusWiseCount = enquiryList.stream()
				.collect(Collectors.groupingBy(Enquiry::getEnquiryStatus, Collectors.counting()));

		int openValue = statusWiseCount.getOrDefault("OPEN", 0l).intValue();
		int enrolledValue = statusWiseCount.getOrDefault("ENROLLED", 0L).intValue();
		int lostValue = statusWiseCount.getOrDefault("LOST", 0l).intValue();

		DashboardResponceDto responceDto = DashboardResponceDto.builder().totalEnquiries(totalCount)
				.openEnquiries(openValue).enrolledEnquiries(enrolledValue).lostEnquiries(lostValue).build();
//		responceDto.setTotalEnquiries(totalCount);
//		responceDto.setOpenEnquiries(openValue);
//		responceDto.setEnrolledEnquiries(entrolledValue);
//		responceDto.setLostEnquiries(lostValue);

		return responceDto;
	}

}
