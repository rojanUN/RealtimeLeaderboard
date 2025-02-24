package com.sh.roadmap.util;

import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;

@UtilityClass
public class ExceptionUtil {

    public static String getStackTraceString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}

