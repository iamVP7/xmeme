package com.xmeme.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;


public class IOCommonUtil {
    private static final Logger IO_LOGGER = Logger.getLogger(IOCommonUtil.class.getName());

    /**
     * <p>check object is null or not</p>
     * @param objectToCheck object to check for null
     * @return true if not null; false if null
     */
    public static boolean isValidObject(Object objectToCheck) {
        return objectToCheck != null;
    }

    /**
     * <p>check number  is valid long number of not</p>
     * @param objectToCheck object to check for null
     * @return true if not -1; false if -1
     */
    public static boolean isValidLong(long numberToCheck) {
        return numberToCheck != -1L;
    }

    /**
     * <p>check if string is not null and nut empty</p>
     * @param objectToCheck
     * @return
     */
    public static boolean isValidString(String objectToCheck) {
        return isValidObject(objectToCheck) && !objectToCheck.equalsIgnoreCase(Constants.EMPTY_STRING);
    }

    public static JSONObject addJSONKeyValue(JSONObject objecToModifiy, String keyToAdd, Object valueToInsert) {
        try {

            if (!isValidJSON(objecToModifiy)) {
                objecToModifiy = new JSONObject();
            }
            if (isValidString(keyToAdd) && isValidObject(valueToInsert)) {
                objecToModifiy.put(keyToAdd, valueToInsert);
            }
        } catch (JSONException exception) {
            XMemeLogger.info(IO_LOGGER,exception.getMessage());
        }
        return objecToModifiy;
    }

    public static boolean isValidJSON(JSONObject jsonObjectToCheck) {
        return IOCommonUtil.isValidObject(jsonObjectToCheck) && !jsonObjectToCheck.isEmpty();
    }

    public static long getCurrentMillSecondsinUTC() {
        return Instant.now(Clock.systemUTC()).toEpochMilli();
    }

    public static boolean isValidList(List<?> listToCheck) {
        return listToCheck != null && !listToCheck.isEmpty();
    }

    public static String getString(Object msgobj) {

        if (msgobj != null) {

            if (msgobj instanceof String || msgobj instanceof JSONArray || msgobj instanceof JSONObject) {
                return msgobj.toString();
            } else if (msgobj instanceof long[] || msgobj instanceof int[] || msgobj instanceof double[]) {
                Object[] convertedMsg = convertPrimitiveArraytoObject(msgobj);
                JSONArray arrayOfObjects = getAsObject(convertedMsg);
                if (isValidJSONArray(arrayOfObjects)) {
                    return arrayOfObjects.toString();
                }

            } else {
                return msgobj.toString();
            }
        } else {
            return Constants.EMPTY_STRING;
        }
        return Constants.EMPTY_STRING;
    }

    private static Object[] convertPrimitiveArraytoObject(Object msgObj) {

        if (msgObj instanceof long[]) {
            long[] convertedPrimitve = ((long[]) msgObj);
            return Arrays.stream(convertedPrimitve).boxed().toArray();
        } else if (msgObj instanceof int[]) {
            int[] convertedPrimitve = ((int[]) msgObj);
            return Arrays.stream(convertedPrimitve).boxed().toArray();
        } else if (msgObj instanceof double[]) {
            double[] convertedPrimitve = ((double[]) msgObj);
            return Arrays.stream(convertedPrimitve).boxed().toArray();
        }
        return null;
    }

    private static JSONArray getAsObject(Object[] valueToConvert) {
        JSONArray convertedArray = new JSONArray();

        for (int indexOfObject = 0; indexOfObject < valueToConvert.length; indexOfObject++) {
            Object value = valueToConvert[indexOfObject];
            try {
                convertedArray.put(getString(value));
            } catch (JSONException iteratException) {
                XMemeLogger.warning(IO_LOGGER, iteratException.getMessage(), iteratException);
            }
        }
        return convertedArray;
    }

    public static boolean isValidJSONArray( JSONArray jsonArrayToCheck ) {
        return isJSONArray(jsonArrayToCheck) && !jsonArrayToCheck.isEmpty();
    }

    /**
     @param jsonArrayToCheck is the JSONArray like ["abcs", "sdfsdf"]
     @return true, if number of items is more than 0
     */
    public static boolean isValidJSONArray( Object jsonArrayToCheck ) {
        return isJSONArray(jsonArrayToCheck) && !((JSONArray) jsonArrayToCheck).isEmpty();
    }

    public static boolean isJSONArray( Object valToCheck ) {
        return isValidObject(valToCheck) && valToCheck instanceof JSONArray;
    }
}
