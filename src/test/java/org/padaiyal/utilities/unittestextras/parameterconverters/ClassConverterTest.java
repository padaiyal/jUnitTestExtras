package org.padaiyal.utilities.unittestextras.parameterconverters;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the functionality of ClassConverter.
 */
public class ClassConverterTest {

  /**
   * Tests converting from strings to classes with invalid inputs.
   */
  @Test
  public void testConvertExceptionNameToClassWithInvalidInputs() {
    // Null input
    Assertions.assertThrows(
        NullPointerException.class,
        () -> ClassConverter.convertClassNameToClass(null));

    // Unsupported/Unknown class
    Assertions.assertThrows(
        ArgumentConversionException.class,
        () -> ClassConverter.convertClassNameToClass(
            "UnknownException"
        )
    );
    // Using class' simple name
    Assertions.assertThrows(
        ArgumentConversionException.class,
        () -> ClassConverter.convertClassNameToClass(
            "NullPointerException"
        )
    );
  }

  /**
   * Tests converting from strings to classes with valid inputs.
   */
  @Test
  public void testConvertExceptionNameToClassWithValidInputs() {

    Assertions.assertEquals(
        NullPointerException.class,
        ClassConverter.convertClassNameToClass("java.lang.NullPointerException")
    );
    Assertions.assertEquals(
        IllegalArgumentException.class,
        ClassConverter.convertClassNameToClass("java.lang.IllegalArgumentException")
    );

    Assertions.assertEquals(
        Arrays.class,
        ClassConverter.convertClassNameToClass("java.util.Arrays")
    );

    Assertions.assertEquals(
        ClassConverter.class,
        ClassConverter.convertClassNameToClass("org.padaiyal.utilities.unittestextras."
            + "parameterconverters.ClassConverter")
    );
  }

  /**
   * Test converting class name to class when providing name as test parameter.
   *
   * @param expectedClassName The class expected name.
   * @param expectedClass     The expected class.
   */
  @ParameterizedTest
  @CsvSource({
      "java.util.Arrays, java.util.Arrays",
      "java.lang.NullPointerException, java.lang.NullPointerException",
      "java.lang.IllegalArgumentException, java.lang.IllegalArgumentException",
      "org.padaiyal.utilities.unittestextras.parameterconverters.ClassConverter, "
          + "org.padaiyal.utilities.unittestextras.parameterconverters.ClassConverter"
  })
  public void testConvertToClassAsParameter(
      String expectedClassName,
      @ConvertWith(ClassConverter.class) Class<?> expectedClass
  ) {
    Assertions.assertEquals(expectedClassName, expectedClass.getName());
  }
}