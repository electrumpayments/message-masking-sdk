package io.electrum.sdk.masking2.json;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.internal.JsonFormatter;
import net.minidev.json.JSONArray;

import java.util.Map;
import java.util.Set;

public class JsonMaskingUtil {

   /**
    * Takes in a string containing JSON and a {@link Set} of {@link JsonMaskingUnit}s which dictate how the JSON
    * should be masked.
    *
    * @param json  the string containing the JSON to be masked
    * @param units masking units which specify which masking schemes should be applied to fields
    *              identified by JSONPath strings
    * @return a pretty-printed string which is a masked version of the originally supplied string.
    *              Null values are not valid JSON and result in a JsonMaskingException
    * @throws JsonMaskingException if the string provided cannot be parsed as JSON, or if the supplied JsonPath
    *                              is invalid
    */
   public static String maskInJsonString(String json, Set<JsonMaskingUnit> units)
           throws JsonMaskingException {
      if (units == null) {
         throw new JsonMaskingException("Null Set of JsonMaskingUnit objects - masking aborted");
      }
      try {
         Configuration pathConfiguration = Configuration.builder().options(Option.AS_PATH_LIST).build();
         DocumentContext pathContext = JsonPath.using(pathConfiguration).parse(json);
         DocumentContext docContext = JsonPath.parse(json);

         for (JsonMaskingUnit unit : units) {
            JsonPath jsonPath = JsonPath.compile(unit.getJsonPath());

            // First get a list of all the sub paths that apply for the given path (in the case that it includes some
            // sort of wildcard behaviour)
            JSONArray paths;

            try {
               paths = pathContext.read(jsonPath);
            } catch (PathNotFoundException pnfe) {
               // Failure to find any matches for the path results in an exception
               // Catch it and move on
               continue;
            }

            for (Object path : paths) {
               JsonPath subPath = JsonPath.compile((String) path);

               Object maskTarget = docContext.read(subPath);

               // We only want to process actual data values, not container types
               if (maskTarget instanceof JSONArray || maskTarget instanceof Map) {
                  continue;
               }

               // If its not some sort of map (JSON object) or JSONArray, the only options left are
               // Number and String. We need a string, so we convert accordingly
               String newValue;

               if (maskTarget instanceof Number) {
                  newValue = maskTarget.toString();
               } else if (maskTarget instanceof String) {
                  newValue = (String) maskTarget;
               } else if (maskTarget == null) {
                  newValue = null;
               } else {
                  throw new JsonMaskingException("Can't safely convert object of type " + maskTarget.getClass().toString()
                          + " to String");
               }

               pathContext.set(subPath, unit.getMasker().mask(newValue));
            }
         }

         return JsonFormatter.prettyPrint(pathContext.jsonString());
      } catch (InvalidJsonException ije) {
         throw new JsonMaskingException("Error attempting to parse the supplied String as JSON", ije);
      } catch (InvalidPathException ipe) {
         throw new JsonMaskingException("Error evaluating JSONPath", ipe);
      } catch (IllegalArgumentException iae) {
         throw new JsonMaskingException("Cannot mask empty or null JSON", iae);
      }
   }
}
