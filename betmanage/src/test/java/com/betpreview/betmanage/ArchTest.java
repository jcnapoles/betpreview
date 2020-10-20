package com.betpreview.betmanage;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.betpreview.betmanage");

        noClasses()
            .that()
                .resideInAnyPackage("com.betpreview.betmanage.service..")
            .or()
                .resideInAnyPackage("com.betpreview.betmanage.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.betpreview.betmanage.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
