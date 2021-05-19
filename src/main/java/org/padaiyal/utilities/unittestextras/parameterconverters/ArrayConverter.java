package org.padaiyal.utilities.unittestextras.parameterconverters;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.padaiyal.utilities.I18nUtility;


/**
 * Converts a string into arrays of different types.
 */
public class ArrayConverter extends SimpleArgumentConverter {

  /**
   * Initializes all dependant values.
   */
  public static Runnable dependantValuesInitializer = () -> {
    I18nUtility.addResourceBundle(
        ArrayConverter.class,
        ArrayConverter.class.getSimpleName(),
        Locale.US
    );
  };

  static {
    dependantValuesInitializer.run();
  }


  /**
   * Private constructor.
   */
  private ArrayConverter() {

  }

  /**
   * Converts a string into an array of specified types.
   *
   * @param arrayObject The string to convert.
   * @param targetType  The type of array to convert it to.
   * @throws ArgumentConversionException When the provided targetType is not supported.
   */
  public static Object[] convertStringToArray(Object arrayObject, Class<?> targetType) {
    if (arrayObject == null) {
      return null;

    }
    Objects.requireNonNull(targetType);
    if (!(arrayObject instanceof String)) {
      throw new ArgumentConversionException(
          I18nUtility.getFormattedString(
              "ArrayConverter.error.conversionNotSupported",
              arrayObject.getClass().getName(), targetType.getName()
          )
      );
    }
    Object[] result = ((String) arrayObject).split(",");

    if (String[].class.isAssignableFrom(targetType)) {
      return result;

    } else if (Integer[].class.isAssignableFrom(targetType)) {
      return Arrays.stream(result)
          .parallel()
          .map(element -> Integer.parseInt(element.toString()))
          .toArray();

    } else if (Long[].class.isAssignableFrom(targetType)) {
      return Arrays.stream(result)
          .parallel()
          .map(element -> Long.parseLong(element.toString()))
          .toArray();

    } else if (Float[].class.isAssignableFrom(targetType)) {
      return Arrays.stream(result)
          .parallel()
          .map(element -> Float.parseFloat(element.toString()))
          .toArray();

    } else if (Double[].class.isAssignableFrom(targetType)) {
      return Arrays.stream(result)
          .parallel()
          .map(element -> Double.parseDouble(element.toString()))
          .toArray();

    } else {
      throw new ArgumentConversionException(
          I18nUtility.getFormattedString(
              "ArrayConverter.error.conversionNotSupported",
              arrayObject.getClass().getName(),
              targetType.getName()
      ));
    }
  }

  /**
   * Converts a string into an array of specified types.
   *
   * @param source     The string to convert.
   * @param targetType The type of array to convert it to.
   * @throws ArgumentConversionException When the provided targetType is not supported.
   */
  @Override
  protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
    return convertStringToArray(source, targetType);
  }

}
