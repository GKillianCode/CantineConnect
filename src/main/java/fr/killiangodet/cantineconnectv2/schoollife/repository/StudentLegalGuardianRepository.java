package fr.killiangodet.cantineconnectv2.schoollife.repository;

import fr.killiangodet.cantineconnectv2.schoollife.entity.StudentLegalGuardian;
import fr.killiangodet.cantineconnectv2.schoollife.entity.StudentLegalGuardianId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentLegalGuardianRepository extends JpaRepository<StudentLegalGuardian, StudentLegalGuardianId> {

}
