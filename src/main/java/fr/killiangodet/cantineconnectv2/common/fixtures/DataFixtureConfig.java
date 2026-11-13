package fr.killiangodet.cantineconnectv2.common.fixtures;

import fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator.PublicIdGenerator;
import fr.killiangodet.cantineconnectv2.common.service.publicIdGenerator.PublicIdType;
import fr.killiangodet.cantineconnectv2.schoollife.entity.Student;
import fr.killiangodet.cantineconnectv2.schoollife.repository.StudentRepository;
import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeGenerator;
import fr.killiangodet.cantineconnectv2.schoollife.service.liaisonCodeGenerator.SecureLiaisonCodeType;
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
            PublicIdGenerator publicIdGenerator,
            SecureLiaisonCodeGenerator secureLiaisonCodeGenerator,
            StudentRepository studentRepository
    ) {
        return args -> {
            Faker faker = new Faker();

            System.out.println("Data fixtures clear started...");
            clearDataFixtures(studentRepository);
            System.out.println("Data fixtures clear completed.");

            System.out.println("Data fixtures load started...");
            generateRandomStudent(faker, publicIdGenerator, secureLiaisonCodeGenerator, studentRepository);
            System.out.println("Data fixtures load completed.");
        };
    }

    public void clearDataFixtures(StudentRepository studentRepository) {
        studentRepository.deleteAll();
    }

    public void generateRandomStudent(
            Faker faker,
            PublicIdGenerator publicIdGenerator,
            SecureLiaisonCodeGenerator secureLiaisonCodeGenerator,
            StudentRepository studentRepository
    ) {
        for (int i = 0; i < 10; i++) {
            Student student = new Student(
                    publicIdGenerator.generate(PublicIdType.STUDENT),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.timeAndDate().birthday(),
                    secureLiaisonCodeGenerator.generate(SecureLiaisonCodeType.LINK)
            );
            studentRepository.save(student);
        }
    }

}
