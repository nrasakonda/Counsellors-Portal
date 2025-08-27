package in.java.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponceDto {

	private Integer totalEnquiries;
	private Integer openEnquiries;
	private Integer enrolledEnquiries;
	private Integer lostEnquiries;
}
