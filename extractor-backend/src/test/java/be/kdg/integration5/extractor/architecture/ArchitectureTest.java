package be.kdg.integration5.extractor.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "be.kdg.integration5.extractor", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {
    private static final String DOMAIN_LAYER = "be.kdg.integration5.extractor.extractor_backend.domain..";

    private static final String ADAPTER_LAYER = "be.kdg.integration5.extractor.extractor_backend.adapters..";
    private static final String CORE_LAYER = "be.kdg.integration5.extractor.extractor_backend.core..";
    private static final String PORT_LAYER = "be.kdg.integration5.extractor.extractor_backend.ports..";

    @ArchTest
    static final ArchRule domainShouldNotDependOnAnyOtherLayerRule =
            noClasses().that().resideInAPackage(DOMAIN_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            ADAPTER_LAYER,
                            PORT_LAYER,
                            CORE_LAYER
                    )
                    .because("This conflicts with hexagonal architecture: Domain should not depend on other layers.");

}
