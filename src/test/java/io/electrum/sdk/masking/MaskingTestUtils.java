package io.electrum.sdk.masking;

import io.electrum.sdk.msg.FieldDefinition;
import io.electrum.sdk.msg.encoder.StringEncoder;
import io.electrum.sdk.msg.pack.AsciiLengthIndicatedBytesPacker;
import io.electrum.sdk.msg.validate.condition.CharacterType;
import io.electrum.sdk.msg.validate.condition.StringMaxLength;
import io.electrum.sdk.msg.validate.validator.ConditionValidator;

public class MaskingTestUtils {
   public static FieldDefinition<String> stringNotMasked = new FieldDefinition<String>(
         1,
         new AsciiLengthIndicatedBytesPacker(2),
         StringEncoder.instance(),
         new ConditionValidator<>(new StringMaxLength(60)).and(new CharacterType(CharacterType.Format.AN)));

   public static FieldDefinition<String> stringAllMasked = new FieldDefinition<String>(
         2,
         new AsciiLengthIndicatedBytesPacker(2),
         StringEncoder.instance(),
         new ConditionValidator<>(new StringMaxLength(60)).and(new CharacterType(CharacterType.Format.AN)),
         new MaskAll());

   public static FieldDefinition<String> stringFullMasked = new FieldDefinition<String>(
         3,
         new AsciiLengthIndicatedBytesPacker(2),
         StringEncoder.instance(),
         new ConditionValidator<>(new StringMaxLength(60)).and(new CharacterType(CharacterType.Format.AN)),
         new MaskFull());

   public static FieldDefinition<String> stringPanMasked = new FieldDefinition<String>(
         4,
         new AsciiLengthIndicatedBytesPacker(2),
         StringEncoder.instance(),
         new ConditionValidator<>(new StringMaxLength(60)).and(new CharacterType(CharacterType.Format.AN)),
         new MaskPan());

   public static FieldDefinition<String> stringTrack2Masked = new FieldDefinition<String>(
         5,
         new AsciiLengthIndicatedBytesPacker(2),
         StringEncoder.instance(),
         new ConditionValidator<>(new StringMaxLength(60)).and(new CharacterType(CharacterType.Format.AN)),
         new MaskTrack2());
}
