package com.xmeme.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XMemeLogger {
    private static final int MAX_STACK_LENGTH = 15;
    private static final Logger ZA_CLASS_LOGGER = Logger.getLogger(XMemeLogger.class.getName());

    public static void info(Logger classLogger, Object msg) {
        info(classLogger, msg, null);
    }

    public static void info(Logger classLogger, Object msg, Exception execptionObject) {
        log(classLogger, Level.INFO, msg, execptionObject);
    }

    public static void severe(Logger classLogger, Object msg) {
        severe(classLogger, msg, null);
    }


    public static void severe(Logger classLogger, Object msg, Exception execptionObject) {
        log(classLogger, Level.SEVERE, msg, execptionObject);
    }

    public static void warning(Logger classLogger, Object msg) {
        warning(classLogger, msg, null);
    }

    public static void warning(Logger classLogger, Exception execptionObject) {
        warning(classLogger, null, execptionObject);
    }

    public static void warning(Logger classLogger, Object msg, Exception execptionObject) {
        log(classLogger, Level.WARNING, msg, execptionObject);
    }

    private static void log(Logger classLogger, Level level, Object msgobj, Exception e) {
        String msg =
                classLogger.getName() + Constants.COLON + getMethodName(e);
        msg += Constants.SPILTTER_PIPE + IOCommonUtil.getString(msgobj);
        msg += addStackElements(e);
        classLogger.log(level, msg);
    }

    private static String getMethodName(Exception exceptionObject) {
        try {
            return IOCommonUtil.isValidObject(exceptionObject) ?
                    exceptionObject.getStackTrace()[0].getMethodName()
                    : Thread.currentThread().getStackTrace()[5].getMethodName();
        } catch (Exception exceptionobject) {
            ZA_CLASS_LOGGER.log(Level.INFO, exceptionObject.getMessage(), exceptionObject);
        }
        return Constants.EMPTY_STRING;

    }


    private static String addStackElements(Exception e) {
        String stackPrint = Constants.EMPTY_STRING;
        if (e != null) {
            StackTraceElement[] listOfStackTrace = e.getStackTrace();
            if (listOfStackTrace != null) {
                int stackLength = listOfStackTrace.length;

                if (stackLength > MAX_STACK_LENGTH) {
                    stackLength = MAX_STACK_LENGTH;
                }
                stackPrint = IntStream.range(0, stackLength - 1)
                        .filter(index_of_stack -> IOCommonUtil.isValidObject(listOfStackTrace[index_of_stack]))
                        .mapToObj(index_of_stack -> Constants.NEW_LINE + Constants.LINE_TAB + listOfStackTrace[index_of_stack])
                        .collect(Collectors.joining());
            }
        }
        return stackPrint;
    }
}
