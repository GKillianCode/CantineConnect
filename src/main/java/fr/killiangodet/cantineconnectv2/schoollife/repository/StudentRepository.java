package fr.killiangodet.cantineconnectv2.schoollife.repository;

import fr.killiangodet.cantineconnectv2.schoollife.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
