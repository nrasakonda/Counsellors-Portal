package in.java.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import in.java.entities.Enquiry;
import in.java.service.EnquiryService;

@Service
public class EnquiryServiceImplementation implements EnquiryService {

    // In-memory storage for enquiries
    private final List<Enquiry> enquiryList = new CopyOnWriteArrayList<>();

    @Override
    public boolean insertEnquiry(Enquiry enquiry) {
        try {
            // Auto-generate an ID for the enquiry
            int nextId = enquiryList.size() + 1;
            enquiry.setEnquiryId(nextId);

            enquiryList.add(enquiry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Enquiry> getAllEnquiry(Integer counsellorId) {
        List<Enquiry> filtered = new ArrayList<>();
        for (Enquiry enq : enquiryList) {
            if (enq.getCounsellor() != null && enq.getCounsellor().getId().equals(counsellorId)) {
                filtered.add(enq);
            }
        }
        return filtered;
    }
}
