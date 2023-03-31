package com.example.model;

import com.example.CustomArgsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("model")
class OwnerTest {

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        Owner owner = new Owner(1L, "Joe", "Buck");
        owner.setCity("Key West");
        owner.setTelephone("1231231234");

        assertAll("Properties Test",
                () -> {
                    String firstName = owner.getFirstName();
                    assertNotNull(firstName);

                    // Executed only if the previous assertion is valid.
                    assertAll("First Name Properties",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("e"))
                    );
                },
                () -> {
                    // Grouped assertion, so processed independently
                    // of results of first name assertions.
                    String lastName = owner.getLastName();
                    assertNotNull(lastName);

                    // Executed only if the previous assertion is valid.
                    assertAll("Last Name Properties",
                            () -> assertTrue(lastName.startsWith("B")),
                            () -> assertTrue(lastName.endsWith("k"))
                    );
                }
        );
    }

    @DisplayName("Value Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Framework", "Boot"})
    void testValueSource(String val)  {
        System.out.println(val);
    }

    @DisplayName("Enum Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void testEnumSource(OwnerType ownerType)  {
        System.out.println(ownerType);
    }

    @DisplayName("CSV Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 3"
    })
    void csvInputTest(String stateName, int val1, int val2)  {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    @DisplayName("CSV File Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFileInputTest(String stateName, int val1, int val2)  {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    @DisplayName("Method Provider Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("testMethodProvider")
    void methodProviderTest(String stateName, int val1, int val2)  {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    static Stream<Arguments> testMethodProvider() {
        return Stream.of(
                Arguments.of("FL", 1, 1),
                Arguments.of("OH", 2, 2),
                Arguments.of("MI", 3, 3)
        );
    }

    @DisplayName("Custom Provider Source Test -")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void customProviderTest(String stateName, int val1, int val2)  {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

}