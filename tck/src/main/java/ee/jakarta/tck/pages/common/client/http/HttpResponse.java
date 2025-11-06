/*
 * Copyright (c) 2006, 2025 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package ee.jakarta.tck.pages.common.client.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * This class represents an HTTP response from the server.
 */

public class HttpResponse {

  /**
   * Default encoding based on Servlet Specification
   */
  private static final String DEFAULT_ENCODING = "ISO-8859-1";

  /**
   * Content-Type header
   */
  private static final String CONTENT_TYPE = "Content-Type";

  /**
   * Wrapped HttpUriRequest used to get request info
   */
  private HttpUriRequest _request = null;

  /**
   * Wrapped HttpResponse used to pull response info from.
   */
  private org.apache.http.HttpResponse _response = null;

  /**
   * Charset encoding returned in the response
   */
  private String _encoding = DEFAULT_ENCODING;

  /**
   * The response body. Initialized after first call to one of the
   * getResponseBody methods and cached for subsequent calls.
   */
  private byte[] _responseBody = null;

  /**
   * Host name used for processing request
   */
  private String _host = null;

  /**
   * Port number used for processing request
   */
  private int _port;

  /**
   * Issecure
   */
  private boolean _isSecure;

  /** Creates new HttpResponse */
  public HttpResponse(String host, int port, boolean isSecure,
      HttpUriRequest request, org.apache.http.HttpResponse response) {

    _host = host;
    _port = port;
    _isSecure = isSecure;
    _request = request;
    _response = response;
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * Returns the HTTP status code returned by the server
   *
   * @return HTTP status code
   */
  public String getStatusCode() {
    return Integer.toString(_response.getStatusLine().getStatusCode());
  }

  /**
   * Returns the HTTP reason-phrase returned by the server
   *
   * @return HTTP reason-phrase
   */
  public String getReasonPhrase() {
    return _response.getStatusLine().getReasonPhrase();
  }

  /**
   * Returns the headers received in the response from the server.
   *
   * @return response headers
   */
  public Header[] getResponseHeaders() {
    return _response.getAllHeaders();
  }

  /**
   * Returns the headers designated by the name provided.
   *
   * @return response headers
   */
  public Header[] getResponseHeaders(String headerName) {
    return _response.getHeaders(headerName);
  }

  /**
   * Returns the response header designated by the name provided.
   *
   * @return a specfic response header or null if the specified header doesn't
   *         exist.
   */
  public Header getResponseHeader(String headerName) {
    return _response.getFirstHeader(headerName);
  }

  /**
   * Returns the response body as a byte array.
   *
   * @return response body as an array of bytes.
   *
   * @exception IOException If an error occurs reading the reading the response body
   */
  public byte[] getResponseBodyAsBytes() throws IOException {
    return getResponseBytes();
  }

  /**
   * Returns the response body as a string using the charset specified in the server's response.
   *
   * @return response body as a String
   *
   * @exception IOException If an error occurs reading the reading the response body
   */
  public String getResponseBodyAsString() throws IOException {
    return new String(getResponseBytes(), getResponseEncoding());
  }

  /**
   * Returns the response body as an InputStream.
   *
   * @return response body as an InputStream
   *
   * @exception IOException If an error occurs reading the reading the response body
   */
  public InputStream getResponseBodyAsStream() throws IOException {
    return new ByteArrayInputStream(getResponseBytes());
  }

  /**
   * Returns the charset encoding for this response.
   *
   * @return charset encoding
   */
  public String getResponseEncoding() {
    Header content = _response.getFirstHeader(CONTENT_TYPE);
    if (content != null) {
      String headerVal = content.getValue();
      int idx = headerVal.indexOf(";charset=");
      if (idx > -1) {
        // content encoding included in response
        _encoding = headerVal.substring(idx + 9);
      }
    }
    return _encoding;
  }

  /**
   * Displays a String representation of the response.
   *
   * @return string representation of response
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer(255);

    sb.append("[RESPONSE STATUS LINE] -> ");
    sb.append(_response.getStatusLine().getProtocolVersion()).append(' ');
    sb.append(_response.getStatusLine().getStatusCode()).append(' ');
    sb.append(_response.getStatusLine().getReasonPhrase()).append('\n');
    Header[] headers = _response.getAllHeaders();
    if (headers != null && headers.length != 0) {
      for (int i = 0; i < headers.length; i++) {
        sb.append("       [RESPONSE HEADER] -> ");
        sb.append(headers[i].toString()).append('\n');
      }
    }

    String resBody;
    try {
      resBody = getResponseBodyAsString();
    } catch (IOException ioe) {
      resBody = "UNEXECTED EXCEPTION: " + ioe.toString();
    }
    if (resBody != null && resBody.length() != 0) {
      sb.append("------ [RESPONSE BODY] ------\n");
      sb.append(resBody);
      sb.append("\n-----------------------------\n\n");
    }
    return sb.toString();
  }

  /*
   * Eventually they need to come from _method
   */

  public String getHost() {
    return _host;
  }

  public int getPort() {
    return _port;
  }

  public String getProtocol() {
    return _isSecure ? "https" : "http";
  }

  public String getPath() {
    try {
      return _request.getURI().getPath();
    } catch (Exception e) {
      return "";
    }
  }

  /*
   * Private Methods
   * ==========================================================================
   */

  /**
   * If previously read, the cached copy of the response body is returned, otherwise the response body is read, the raw
   * bytes cached and then returned.
   *
   * @return the response body as a byte array
   */
  private byte[] getResponseBytes() throws IOException {
    if (_responseBody == null) {
      if (_response.getEntity() != null) {
          BufferedInputStream bis = new BufferedInputStream( _response.getEntity().getContent());
          _responseBody = bis.readAllBytes();
      } else {
        _responseBody = null;
      }
    }
    return _responseBody;
  }
}
