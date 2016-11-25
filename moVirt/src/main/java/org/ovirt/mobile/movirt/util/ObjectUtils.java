package org.ovirt.mobile.movirt.util;

import android.util.Log;

import java.io.Closeable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

public final class ObjectUtils {
    private static final String TAG = ObjectUtils.class.getSimpleName();

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * Checks if ids has the same length as number of params,
     *
     * @param ids
     * @param params
     * @throws IllegalArgumentException if wrong number of params
     */
    public static void requireSignature(String[] ids, String... params) {
        int length = params.length;

        if (ids.length != length) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(Locale.ENGLISH, "Expected %d parameters", length));
            if (length > 0) {
                sb.append(":");

                for (String p : params) {
                    sb.append(" ").append(p);
                }
            }

            throw new IllegalArgumentException(sb.toString());
        }
    }

    /**
     * @param o    object to be checked against
     * @param name name of the object o
     * @throws IllegalArgumentException if o is null
     */
    public static void requireNotNull(Object o, String name) {
        if (o == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * @param value value to be converted
     * @return int, unknown types are set to -1
     */
    public static int convertToInt(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return -1;
        }
    }

    /**
     * @param value value to be parsed
     * @return long of value, -1 if it cannot be parsed
     */
    public static long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String throwableToString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }

    /**
     * @param closeables objects to be closed while ignoring exceptions
     * @return true if all objects were closed correctly
     * false if some object wasn't closed correctly or if no objects were passed
     */
    @SafeVarargs
    public static <T extends Closeable> boolean closeSilently(T... closeables) {
        if (closeables == null || closeables.length == 0) {
            return false;
        }

        boolean closed = true;
        for (Closeable c : closeables) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception x) {
                try {
                    Log.e(TAG, throwableToString(x));
                } catch (Exception ignored) {
                }
                closed = false;
            }
        }

        return closed;
    }

    /**
     * Closes everything and throws last exception which occured
     *
     * @param closeables objects to be closed
     * @throws Exception last exception
     */
    @SafeVarargs
    public static <T extends Closeable> void close(T... closeables) throws Exception {
        if (closeables == null || closeables.length == 0) {
            return;
        }

        Exception throwOut = null;

        for (Closeable c : closeables) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception x) {
                throwOut = x;
            }
        }

        if (throwOut != null) {
            throw throwOut;
        }
    }
}
