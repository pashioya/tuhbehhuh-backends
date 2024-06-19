package be.kdg.integration5.extractor.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import java.util.Arrays;
import java.util.Collections;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "be.kdg.integration5.extractor.extractor_backend")
public class CodeStyleTest {

    @ArchTest
    static final ArchRule weShouldUseListOfRule =
            noClasses().should().callMethod(Arrays.class, "asList", Object[].class)
                    .orShould().callMethod(Collections.class, "singletonList", Object.class)
                    .because("List.of() is the better function for creating lists (cfr immutability).");

    @ArchTest
    static final ArchRule shouldNotUseJavaUtilDate =
            noClasses().should().haveFullyQualifiedName("java.util.Date")
                    .because("Prefer using java.time.* classes for date and time operations.");
}
