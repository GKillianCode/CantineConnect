package fr.killiangodet.cantineconnect;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "fr.killiangodet.cantineconnect")
class ArchitectureTest {

    // The SHARED package must not depend on any Bounded Context
    @ArchTest
    static final ArchRule shared_must_not_depend_on_modules =
            noClasses()
                    .that().resideInAPackage("..shared..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("..billing..", "..booking..", "..inventory..");

    // BILLING can only access the API layer of BOOKING and INVENTORY
    @ArchTest
    static final ArchRule billing_boundary_protection =
            noClasses()
                    .that().resideInAPackage("..billing..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "..booking.domain..", "..booking.application..", "..booking.infrastructure..",
                            "..inventory.domain..", "..inventory.application..", "..inventory.infrastructure.."
                    );

    // BOOKING can only access the API layer of BILLING and INVENTORY
    @ArchTest
    static final ArchRule booking_boundary_protection =
            noClasses()
                    .that().resideInAPackage("..booking..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "..billing.domain..", "..billing.application..", "..billing.infrastructure..",
                            "..inventory.domain..", "..inventory.application..", "..inventory.infrastructure.."
                    );

    // INVENTORY can only access the API layer of BILLING and BOOKING
    @ArchTest
    static final ArchRule inventory_boundary_protection =
            noClasses()
                    .that().resideInAPackage("..inventory..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "..billing.domain..", "..billing.application..", "..billing.infrastructure..",
                            "..booking.domain..", "..booking.application..", "..booking.infrastructure.."
                    );

    // The domain layer must not depend on Spring or JPA
    @ArchTest
    static final ArchRule domain_purety =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("org.springframework..", "jakarta.persistence..");
}