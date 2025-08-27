package in.java.dto;

import lombok.Data;

@Data
public class EnquiryFilterRequestDto {

	private String enquiryStatus;
	private String classMode;
	private Integer courseId;
}
