package in.java.service;

import java.util.List;

import in.java.entities.Enquiry;

public interface EnquiryService {

    // Save enquiry
    boolean insertEnquiry(Enquiry enquiry);

    // Get all enquiries of a counsellor
    List<Enquiry> getAllEnquiry(Integer counsellorId);
}
