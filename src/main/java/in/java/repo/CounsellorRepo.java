package in.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.java.entities.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{

	public Counsellor findByEmailAndPwd(String email, String pwd);

}
