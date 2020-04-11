/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.compiler;

import java.util.*;
import java.io.*;
import java.text.MessageFormat;
import org.xml.sax.*;
import org.apache.jasper.JasperException;

/**
 * Class responsible for converting error codes to corresponding localized error messages.
 *
 * @author Jan Luehe
 */
public class Localizer {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("org.apache.jasper.resources.messages");

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode) {
        String errMsg = errCode;
        try {
            errMsg = bundle.getString(errCode);
        } catch (MissingResourceException e) {
        }
        return errMsg;
    }

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @param arg Argument for parametric replacement
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode, String arg) {
        return getMessage(errCode, new Object[] { arg });
    }

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @param arg1 First argument for parametric replacement
     *
     * @param arg2 Second argument for parametric replacement
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode, String arg1, String arg2) {
        return getMessage(errCode, new Object[] { arg1, arg2 });
    }

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @param arg1 First argument for parametric replacement
     *
     * @param arg2 Second argument for parametric replacement
     *
     * @param arg3 Third argument for parametric replacement
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode, String arg1, String arg2, String arg3) {
        return getMessage(errCode, new Object[] { arg1, arg2, arg3 });
    }

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @param arg1 First argument for parametric replacement
     *
     * @param arg2 Second argument for parametric replacement
     *
     * @param arg3 Third argument for parametric replacement
     *
     * @param arg4 Fourth argument for parametric replacement
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode, String arg1, String arg2, String arg3, String arg4) {
        return getMessage(errCode, new Object[] { arg1, arg2, arg3, arg4 });
    }

    /*
     * Returns the localized error message corresponding to the given error code.
     *
     * If the given error code is not defined in the resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code to localize
     *
     * @param args Arguments for parametric replacement
     *
     * @return Localized error message
     */
    public static String getMessage(String errCode, Object[] args) {
        String errMsg = errCode;
        try {
            errMsg = bundle.getString(errCode);
            if (args != null) {
                MessageFormat formatter = new MessageFormat(errMsg);
                errMsg = formatter.format(args);
            }
        } catch (MissingResourceException e) {
        }

        return errMsg;
    }
}
