package fr.killiangodet.cantineconnectv2.common.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "fr.killiangodet.cantineconnectv2")
public class ArchitectureTest {

    // Controller Layer Rules
    @ArchTest
    public static final ArchRule classes_ending_with_Controller_should_be_in_controller_package =
            classes()
                    .that().haveSimpleNameEndingWith("Controller")
                    .should().resideInAPackage("..controller..")
                    .because("Controllers should be in the controller package");

    @ArchTest
    public static final ArchRule classes_ending_with_Controller_should_be_an_annotation =
            classes()
                    .that().haveSimpleNameEndingWith("Controller")
                    .should().beAnnotatedWith(Controller.class)
                    .orShould().beAnnotatedWith(RestController.class)
                    .because("Controllers should be annotated with @RestController");

    @ArchTest
    public static final ArchRule annotated_controllers_must_follow_naming_and_location =
            classes()
                    .that().areAnnotatedWith(Controller.class)
                    .should().haveSimpleNameEndingWith("Controller")
                    .andShould().resideInAPackage("..controller..")
                    .allowEmptyShould(true);

    // Service Layer Rules
    @ArchTest
    public static final ArchRule classes_ending_with_Service_should_be_in_service_package =
            classes()
                    .that().haveSimpleNameEndingWith("Service")
                    .should().resideInAPackage("..service..")
                    .because("Services should be in the service package");

    @ArchTest
    public static final ArchRule classes_ending_with_Service_should_be_an_annotation = classes()
            .that().haveSimpleNameEndingWith("Service")
            .should().beAnnotatedWith(Service.class)
            .because("Services should be annotated with @Service");

    @ArchTest
    public static final ArchRule annotated_services_must_follow_naming_and_location =
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .andShould().resideInAPackage("..service..")
                    .allowEmptyShould(true);

    // Repository Layer Rules
    @ArchTest
    public static final ArchRule classes_ending_with_Repository_should_be_in_repository_package =
            classes()
                    .that().haveSimpleNameEndingWith("Repository")
                    .should().resideInAPackage("..repository..")
                    .because("Repositories should be in the repository package");

    @ArchTest
    public static final ArchRule classes_ending_with_Repository_should_be_an_annotation = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .should().beAnnotatedWith(Repository.class)
            .because("Repositories should be annotated with @Repository");

    @ArchTest
    public static final ArchRule annotated_repositories_must_follow_naming_and_location =
            classes()
                    .that().areAnnotatedWith(Repository.class)
                    .should().haveSimpleNameEndingWith("Repository")
                    .andShould().resideInAPackage("..repository..")
                    .allowEmptyShould(true);

    // Dependency Rules
    @ArchTest
    public static final ArchRule controllers_should_not_access_repositories =
            noClasses()
                    .that().resideInAPackage("..controller..")
                    .should().dependOnClassesThat().resideInAPackage("..repository..");


    @ArchTest
    public static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .layer("Controller").definedBy("..controller..")
                    .layer("Service").definedBy("..service..")
                    .layer("Repository").definedBy("..repository..")

                    .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                    .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

    // No Circular Dependencies Rule
    @ArchTest
    public static final ArchRule no_circular_dependencies_between_classes =
            slices()
                    .matching("fr.killiangodet.cantineconnectv2..(*)")
                    .should()
                    .beFreeOfCycles();
}
