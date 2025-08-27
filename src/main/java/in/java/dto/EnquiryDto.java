package in.java.dto;

import lombok.Data;

@Data
public class EnquiryDto {
	
	private Integer enquiryId;
	private String studentName;
	private String studentPhno;
	private String classMode;
	private String enquiryStatus;
	private Integer courseId;
}
