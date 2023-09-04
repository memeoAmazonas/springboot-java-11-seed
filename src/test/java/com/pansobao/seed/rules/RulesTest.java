package com.pansobao.seed.rules;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

/**
 * packages: Debe ir el nombre del package del ms.
  */
@AnalyzeClasses(packages = "com.pansobao.seed")
public class RulesTest {

    @ArchTest
    public static final ArchRule execptions_respect_convention_names= classes()
            .that().resideInAnyPackage("..exception..")
            .should().haveSimpleNameEndingWith("Exception");

    @ArchTest
    static ArchRule no_generic_exception = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static ArchRule no_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    static ArchRule no_java_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;


}
