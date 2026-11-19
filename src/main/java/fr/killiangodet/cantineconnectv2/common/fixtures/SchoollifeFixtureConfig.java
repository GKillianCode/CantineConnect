package fr.killiangodet.cantineconnectv2.common.fixtures;

import fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator.PublicIdGenerator;
import fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator.PublicIdType;
import fr.killiangodet.cantineconnectv2.schoollife.entity.LegalGuardian;
import fr.killiangodet.cantineconnectv2.schoollife.entity.Student;
import fr.killiangodet.cantineconnectv2.schoollife.entity.StudentLegalGuardian;
import fr.killiangodet.cantineconnectv2.schoollife.repository.LegalGuardianRepository;
import fr.killiangodet.cantineconnectv2.schoollife.repository.StudentLegalGuardianRepository;
import fr.killiangodet.cantineconnectv2.schoollife.repository.StudentRepository;
import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeGenerator;
import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeType;
import net.datafaker.Faker;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
public class SchoollifeFixtureConfig {

    private final StudentRepository studentRepository;
    private final LegalGuardianRepository legalGuardianRepository;
    private final StudentLegalGuardianRepository studentLegalGuardianRepository;

    private final PublicIdGenerator publicIdGenerator;
    private final SecureLiaisonCodeGenerator secureLiaisonCodeGenerator;

    private final Faker faker;

    SchoollifeFixtureConfig(
            StudentRepository studentRepository,
            LegalGuardianRepository legalGuardianRepository,
            StudentLegalGuardianRepository studentLegalGuardianRepository,
            PublicIdGenerator publicIdGenerator,
            SecureLiaisonCodeGenerator secureLiaisonCodeGenerator
    ) {
        this.studentRepository = studentRepository;
        this.legalGuardianRepository = legalGuardianRepository;
        this.studentLegalGuardianRepository = studentLegalGuardianRepository;
        this.publicIdGenerator = publicIdGenerator;
        this.secureLiaisonCodeGenerator = secureLiaisonCodeGenerator;

        this.faker = new Faker();
    }

    private void generateRandomStudent() {
        for (int i = 0; i < 10; i++) {
            Student student = new Student(
                    publicIdGenerator.generate(PublicIdType.STUDENT),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.timeAndDate().birthday(),
                    secureLiaisonCodeGenerator.generate(SecureLiaisonCodeType.LINK)
            );
            studentRepository.save(student);
            generateRandomLegalGuardian(student);
        }
    }

    private void generateRandomLegalGuardian(Student student) {
        List<LegalGuardian> legalGuardians = new ArrayList<>();
        int numberOfLegalGuardians = faker.number().numberBetween(1, 4);

        for (int i = 0; i < numberOfLegalGuardians; i++) {
            var legalGuardian = new LegalGuardian(
                    publicIdGenerator.generate(PublicIdType.LEGAL_GUARDIAN),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.lorem().characters(8, 16),
                    faker.phoneNumber().cellPhone()
            );
            legalGuardianRepository.save(legalGuardian);
            legalGuardians.add(legalGuardian);
        }

        generateStudentLegalGuardian(student, legalGuardians);
    }

    private void generateStudentLegalGuardian(Student student, List<LegalGuardian> legalGuardians) {
        for (int i = 0; i<legalGuardians.size(); i++) {
            StudentLegalGuardian studentLegalGuardian = new StudentLegalGuardian(student, legalGuardians.get(i));
            studentLegalGuardianRepository.save(studentLegalGuardian);
        }
    }

    public void loadDataFixtures() {
        generateRandomStudent();
    }
}
