package io.electrum.sdk.masking2.json;

import io.electrum.sdk.masking2.MaskAll;
import io.electrum.sdk.masking2.MaskFull;
import io.electrum.sdk.masking2.MaskTrack2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class JsonMaskingUtilTest {

   private String originalJson = "{\n" +
           "   \"thing\" : [\n" +
           "      1,\n" +
           "      {\n" +
           "         \"thing\" : {\n" +
           "            \"field\" : \"value\"\n" +
           "         }\n" +
           "      },\n" +
           "      3\n" +
           "   ],\n" +
           "   \"otherThing\" : {\n" +
           "      \"thing\" : 145\n" +
           "   }\n" +
           "}";

   @Test
   public void testPathNotFound() throws Exception {
      JsonMaskingUnit unit = new JsonMaskingUnit("$..absent", new MaskAll());

      Set<JsonMaskingUnit> units = new HashSet<>();
      units.add(unit);

      String masked = JsonMaskingUtil.maskInJsonString(originalJson, units);

      // Since the JsonPath used in the masking unit does not refer to any element in the JSON, there should be no
      // masking
      Assert.assertEquals(masked, originalJson);
   }

   @Test
   public void testDeepScan() throws Exception {
      JsonMaskingUnit unit1 = new JsonMaskingUnit("$..thing", new MaskFull());
      JsonMaskingUnit unit2 = new JsonMaskingUnit("$.thing[1].thing.field", new MaskAll());

      Set<JsonMaskingUnit> units = new HashSet<>();
      units.add(unit1);
      units.add(unit2);

      String masked = JsonMaskingUtil.maskInJsonString(originalJson, units);

      // Even though "thing" appears a number of times, we only want non-object, non-array fields to get masked,
      // of which there is only one. The other field is specified in full.
      String expected = "{\n" +
              "   \"thing\" : [\n" +
              "      1,\n" +
              "      {\n" +
              "         \"thing\" : {\n" +
              "            \"field\" : \"*****\"\n" +
              "         }\n" +
              "      },\n" +
              "      3\n" +
              "   ],\n" +
              "   \"otherThing\" : {\n" +
              "      \"thing\" : \"*\"\n" +
              "   }\n" +
              "}";

      Assert.assertEquals(masked, expected);
   }

   @Test(expectedExceptions = JsonMaskingException.class)
   public void testNullString() throws Exception {
      JsonMaskingUnit unit1 = new JsonMaskingUnit("$..thing", new MaskFull());
      JsonMaskingUnit unit2 = new JsonMaskingUnit("$.thing[1].thing.field", new MaskAll());

      Set<JsonMaskingUnit> units = new HashSet<>();
      units.add(unit1);
      units.add(unit2);

      JsonMaskingUtil.maskInJsonString(null, units);
   }

   @Test(expectedExceptions = JsonMaskingException.class)
   public void testEmptyString() throws Exception {
      JsonMaskingUnit unit1 = new JsonMaskingUnit("$..thing", new MaskFull());
      JsonMaskingUnit unit2 = new JsonMaskingUnit("$.thing[1].thing.field", new MaskAll());

      Set<JsonMaskingUnit> units = new HashSet<>();
      units.add(unit1);
      units.add(unit2);

      JsonMaskingUtil.maskInJsonString("", units);
   }

   @Test
   public void testValidEmptyJson() throws Exception {
      JsonMaskingUnit unit = new JsonMaskingUnit("$..*", new MaskTrack2());

      Set<JsonMaskingUnit> units = new HashSet<>();
      units.add(unit);

      String json = "{}";

      // Pretty printing adds a newline, 3 spaces and another newline
      String expected = "{\n   \n}";

      String masked = JsonMaskingUtil.maskInJsonString(json, units);

      Assert.assertEquals(masked, expected);
   }
}
