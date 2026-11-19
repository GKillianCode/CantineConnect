package fr.killiangodet.cantineconnectv2.common.fixtures;

import fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator.PublicIdGenerator;
import fr.killiangodet.cantineconnectv2.schoollife.repository.LegalGuardianRepository;
import fr.killiangodet.cantineconnectv2.schoollife.repository.StudentLegalGuardianRepository;
import fr.killiangodet.cantineconnectv2.schoollife.repository.StudentRepository;
import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeGenerator;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DataFixtureConfig {

    @Bean
    public CommandLineRunner loadData(
            StudentRepository studentRepository,
            LegalGuardianRepository legalGuardianRepository,
            StudentLegalGuardianRepository studentLegalGuardianRepository,
            PublicIdGenerator publicIdGenerator,
            SecureLiaisonCodeGenerator secureLiaisonCodeGenerator
    ) {
        return args -> {
            System.out.println("Data fixtures clear started...");
            clearDataFixtures(studentRepository, legalGuardianRepository);
            System.out.println("Data fixtures clear completed.");

            System.out.println("Data fixtures load started...");
            System.out.println("// Schoollife fixtures");
            SchoollifeFixtureConfig schoollifeFixture = new SchoollifeFixtureConfig(
                    studentRepository,
                    legalGuardianRepository,
                    studentLegalGuardianRepository,
                    publicIdGenerator,
                    secureLiaisonCodeGenerator
            );
            schoollifeFixture.loadDataFixtures();
            System.out.println("Data fixtures load completed.");
        };
    }

    public void clearDataFixtures(StudentRepository studentRepository, LegalGuardianRepository legalGuardianRepository) {
        studentRepository.deleteAll();
        legalGuardianRepository.deleteAll();
    }
}
