package in.java.service;

import org.springframework.stereotype.Service;

import in.java.dto.DashboardResponceDto;
import in.java.entities.Counsellor;

@Service
public interface CounsellorService {

	public Counsellor login(String email, String pwd);

	public Boolean register(Counsellor counsellor);

	public DashboardResponceDto getDashboardInfo(Integer counsellorId);

}
