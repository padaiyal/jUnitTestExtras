package org.padaiyal.utilities.unittestextras.parameterconverters;

import java.util.Locale;
import java.util.Objects;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.padaiyal.utilities.I18nUtility;

/**
 * Used to convert a given string into an exception class.
 */
public class ClassConverter implements ArgumentConverter {

  static {
    I18nUtility.addResourceBundle(
        ClassConverter.class,
        ClassConverter.class.getSimpleName(),
        Locale.US
    );
  }

  /**
   * Private constructor.
   */
  private ClassConverter() {

  }

  /**
   * Converts a given class name to the class.
   *
   * @param expectedClassName The name of the class to convert.
   * @return The exception class object corresponding to the input string.
   * @throws ArgumentConversionException When an error occurs during conversion.
   */
  public static Class<?> convertClassNameToClass(
      String expectedClassName
  ) throws ArgumentConversionException {
    // Input validation
    Objects.requireNonNull(expectedClassName);

    try {
      return Class.forName(expectedClassName);
    } catch (ClassNotFoundException e) {

      throw new ArgumentConversionException(
          I18nUtility.getFormattedString(
              "ClassConverter.unableToConvertStringToClass",
              expectedClassName
          ),
          e
      );
    }
  }

  /**
   * Converts a given class name to its respective class.
   *
   * @param expectedClassName The name of the class to convert.
   * @param context           The parameter context where the converted object will be used.
   * @return The exception class object corresponding to the input string.
   * @throws ArgumentConversionException When an error occurs during conversion.
   */
  @Override
  public Object convert(Object expectedClassName, ParameterContext context)
      throws ArgumentConversionException {
    return convertClassNameToClass(expectedClassName.toString());
  }
}
