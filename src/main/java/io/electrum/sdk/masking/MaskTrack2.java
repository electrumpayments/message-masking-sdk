package io.electrum.sdk.masking;

public class MaskTrack2 extends Masker {
   private static final String SEPARATOR_1 = "D";
   private static final String SEPARATOR_2 = "=";

   @Override
   public String mask(String value) {
      if (value == null) {
         return null;
      }

      if (isDisabled()) {
         return value;
      }

      // Assumes the same separator will be used throughout the track data
      String separator = findSeparator(value);

      StringBuilder maskedValue = new StringBuilder();

      String[] splitTrack = value.split(separator);

      maskedValue.append(new MaskPan().mask(splitTrack[0]));
      maskedValue.append(separator);

      for (int i = 1; i < splitTrack.length; i++) {
         String maskedSection = new MaskFull().mask(splitTrack[i]);
         maskedValue.append(maskedSection);
         // Don't add a separator on at the end
         if (i != splitTrack.length - 1) {
            maskedValue.append(separator);
         }
      }

      return maskedValue.toString();
   }

   private String findSeparator(String value) {
      String separator = SEPARATOR_1;
      int fieldSeparatorIndex = value.indexOf(SEPARATOR_1);
      if (fieldSeparatorIndex == -1) {
         fieldSeparatorIndex = value.indexOf(SEPARATOR_2);
         separator = SEPARATOR_2;
      }

      if (fieldSeparatorIndex == -1) {
         throw new IllegalStateException("No field separator found in track 2 data: " + value);
      }

      return separator;
   }
}
