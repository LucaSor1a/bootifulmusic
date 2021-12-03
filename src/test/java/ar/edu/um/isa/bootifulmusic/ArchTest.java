package ar.edu.um.isa.bootifulmusic;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.edu.um.isa.bootifulmusic");

        noClasses()
            .that()
            .resideInAnyPackage("ar.edu.um.isa.bootifulmusic.service..")
            .or()
            .resideInAnyPackage("ar.edu.um.isa.bootifulmusic.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ar.edu.um.isa.bootifulmusic.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
