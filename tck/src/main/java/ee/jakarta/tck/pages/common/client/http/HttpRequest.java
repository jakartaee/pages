/*
 * Copyright (c) 2007, 2025 Oracle and/or its affiliates and others.
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Represents an HTTP client Request
 */

public class HttpRequest {

  private static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());

  static {
    if (LOGGER.isLoggable(Level.FINER)) {
      System.setProperty("org.apache.commons.logging.Log",
          "com.sun.ts.tests.common.webclient.log.WebLog");
      System.setProperty(
          "org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
    }
  }

  /**
   * Default HTTP port.
   */
  public static int DEFAULT_HTTP_PORT = 80;

  /**
   * Default HTTP SSL port.
   */
  public static final int DEFAULT_SSL_PORT = 443;

  /**
   * No authentication
   */
  public static final int NO_AUTHENTICATION = 0;

  /**
   * Basic authentication
   */
  public static final int BASIC_AUTHENTICATION = 1;

  /**
   * Digest authenctication
   */
  public static final int DIGEST_AUTHENTICATION = 2;

  /**
   * Method representation of request.
   */
  private HttpUriRequest _method = null;

  /**
   * Response from executing the request
   */
  private HttpResponse _response = null;

  /**
   * Target web container host
   */
  private String _host = null;

  /**
   * Target web container port
   */
  private int _port = DEFAULT_HTTP_PORT;

  /**
   * Is the request going over SSL
   */
  private boolean _isSecure = false;

  /**
   * Cookie store for managing cookies
   */
  private CookieStore _cookieStore = null;

  /**
   * Credentials provider for authentication
   */
  private BasicCredentialsProvider _credentialsProvider = null;

  /**
   * Original request line for this request.
   */
  private String _requestLine = null;

  /**
   * Authentication type for current request
   */
  private int _authType = NO_AUTHENTICATION;

  /**
   * Flag to determine if session tracking will be used or not.
   */
  private boolean _useCookies = false;

  /**
   * Content length of request body.
   */
  private int _contentLength = 0;

  /**
   * Flag for redirect following
   */
  private boolean _followRedirects = false;

  Header[] _headers = null;

  protected HttpClient client = null;

  /**
   * Creates new HttpRequest based of the passed request line. The request line
   * provied must be in the form of:<br>
   *
   * <pre>
   *     METHOD PATH HTTP-VERSION
   *     Ex.  GET /index.html HTTP/1.0
   * </pre>
   */
  public HttpRequest(String requestLine, String host, int port) {
    client = HttpClientBuilder.create().build();
    _method = MethodFactory.getInstance(requestLine);
    _host = host;
    _port = port;

    if (port == DEFAULT_SSL_PORT) {
      _isSecure = true;
    }

    // If we got this far, the request line is in the proper
    // format
    _requestLine = requestLine;
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * <code>getRequestPath</code> returns the request path for this particular
   * request.
   *
   * @return String request path
   */
  public String getRequestPath() {
    try {
      return _method.getURI().getPath();
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * <code>getRequestMethod</code> returns the request type, i.e., GET, POST,
   * etc.
   *
   * @return String request type
   */
  public String getRequestMethod() {
    return _method.getMethod();
  }

  /**
   * <code>isSecureConnection()</code> indicates if the Request is secure or
   * not.
   *
   * @return boolean whether Request is using SSL or not.
   */
  public boolean isSecureRequest() {
    return _isSecure;
  }

  /**
   * <code>setSecureRequest</code> configures this request to use SSL.
   *
   * @param secure
   *          - whether the Request uses SSL or not.
   */
  public void setSecureRequest(boolean secure) {
    _isSecure = secure;
  }

  /**
   * <code>setContent</code> will set the body for this request. Note, this is
   * only valid for POST and PUT operations, however, if called and the request
   * represents some other HTTP method, it will be no-op'd.
   *
   * @param content
   *          request content
   */
  public void setContent(String content) {
    if (_method instanceof HttpEntityEnclosingRequest) {
      try {
        ((HttpEntityEnclosingRequest) _method).setEntity(new StringEntity(content));
      } catch (Exception e) {
        LOGGER.log(Level.WARNING, "Failed to set entity", e);
      }
    }
    _contentLength = content.length();
  }

  /**
   * <code>setAuthenticationCredentials configures the request to
   * perform authentication.
   *
   * <p><code>username</code> and <code>password</code> cannot be null.
   * </p>
   *
   * <p>
   * It is legal for <code>realm</code> to be null.
   * </p>
   *
   * @param username
   *          the user
   * @param password
   *          the user's password
   * @param authType
   *          authentication type
   * @param realm
   *          authentication realm
   */
  public void setAuthenticationCredentials(String username, String password,
      int authType, String realm) {
    if (username == null) {
      throw new IllegalArgumentException("Username cannot be null");
    }

    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    UsernamePasswordCredentials cred = new UsernamePasswordCredentials(username, password);
    AuthScope scope = new AuthScope(_host, _port, realm);
    getCredentialsProvider().setCredentials(scope, cred);
    LOGGER.finer("Added credentials for '" + username
        + "' with password '" + password + "' in realm '" + realm + "'");

    _authType = authType;
  }

  /**
   * <code>addRequestHeader</code> adds a request header to this request. If a
   * request header of the same name already exists, the new value, will be
   * added to the set of already existing values.
   *
   * <strong>NOTE:</strong> that header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void addRequestHeader(String headerName, String headerValue) {
    _method.addHeader(headerName, headerValue);
    LOGGER.finer("Added request header: " + headerName + ": " + headerValue);
  }

  public void addRequestHeader(String header) {
    StringTokenizer st = new StringTokenizer(header, "|");
    while (st.hasMoreTokens()) {
      String h = st.nextToken();
      if (h.toLowerCase().startsWith("cookie")) {
        createCookie(h);
        continue;
      }
      int col = h.indexOf(':');
      addRequestHeader(h.substring(0, col).trim(), h.substring(col + 1).trim());
    }
  }

  /**
   * <code>setRequestHeader</code> sets a request header for this request
   * overwritting any previously existing header/values with the same name.
   *
   * <strong>NOTE:</strong> Header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void setRequestHeader(String headerName, String headerValue) {
    _method.setHeader(headerName, headerValue);
    LOGGER.finer("Set request header: " + headerName + ": " + headerValue);
  }

  /**
   * <code>setFollowRedirects</code> indicates whether HTTP redirects are
   * followed. By default, redirects are not followed.
   */
  public void setFollowRedirects(boolean followRedirects) {
    _followRedirects = followRedirects;
  }

  /**
   * <code>getFollowRedirects</code> indicates whether HTTP redirects are
   * followed.
   */
  public boolean getFollowRedirects() {
    return _followRedirects;
  }

  /**
   * <code>execute</code> will dispatch the current request to the target
   * server.
   *
   * @return The server's response.
   *
   * @throws IOException
   *           if an I/O error occurs during dispatch.
   */
  public ee.jakarta.tck.pages.common.client.http.HttpResponse execute() throws IOException {
    String scheme = _isSecure ? "https" : "http";

    // Configure connection manager
    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", PlainConnectionSocketFactory.getSocketFactory())
        .register("https", SSLConnectionSocketFactory.getSocketFactory())
        .build();

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

    // Configure request config for redirects
    RequestConfig requestConfig = RequestConfig.custom()
        .setRedirectsEnabled(_followRedirects)
        .setCookieSpec(CookieSpecs.DEFAULT)
        .build();

    // Create HTTP client with configuration
    HttpClientBuilder clientBuilder = HttpClientBuilder.create()
        .setConnectionManager(connectionManager)
        .setDefaultRequestConfig(requestConfig);

    // Add credentials if set
    if (_credentialsProvider != null) {
      clientBuilder.setDefaultCredentialsProvider(_credentialsProvider);
    }

    client = clientBuilder.build();

    LOGGER.info("Dispatching request: '" + _requestLine
        + "' to target server at '" + _host + ":" + _port + "'");

    addSupportHeaders();
    _headers = _method.getAllHeaders();

    LOGGER.finer("########## The real value set: " + _followRedirects);

    // Update the request URI to include scheme, host, and port
    try {
      java.net.URI currentUri = _method.getURI();
      java.net.URI fullUri = new java.net.URI(
          scheme,
          null,
          _host,
          _port,
          currentUri.getPath(),
          currentUri.getQuery(),
          currentUri.getFragment()
      );
      if (_method instanceof org.apache.http.client.methods.HttpRequestBase) {
        ((org.apache.http.client.methods.HttpRequestBase) _method).setURI(fullUri);
      }
    } catch (java.net.URISyntaxException e) {
      throw new IOException("Failed to build request URI", e);
    }

    // Execute the request
    _response = client.execute(_method);

    return new ee.jakarta.tck.pages.common.client.http.HttpResponse(_host, _port, _isSecure, _method, _response);
  }

  /**
   * Returns the credentials provider for this request.
   *
   * @return BasicCredentialsProvider credentials provider
   */
  public BasicCredentialsProvider getCredentialsProvider() {
    if (_credentialsProvider == null) {
      _credentialsProvider = new BasicCredentialsProvider();
    }
    return _credentialsProvider;
  }

  /**
   * Returns the cookie store for this request.
   *
   * @return CookieStore cookie store
   */
  public CookieStore getCookieStore() {
    if (_cookieStore == null) {
      _cookieStore = new BasicCookieStore();
    }
    return _cookieStore;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer(255);
    sb.append("[REQUEST LINE] -> ").append(_requestLine).append('\n');

    if (_headers != null && _headers.length != 0) {

      for (Header _header : _headers) {
        sb.append("       [REQUEST HEADER] -> ");
        sb.append(_header.toString()).append('\n');
      }
    }

    if (_contentLength != 0) {
      sb.append("       [REQUEST BODY LENGTH] -> ").append(_contentLength);
      sb.append('\n');
    }

    return sb.toString();

  }

  /*
   * private methods
   * ========================================================================
   */

  private void createCookie(String cookieHeader) {
    String cookieLine = cookieHeader.substring(cookieHeader.indexOf(':') + 1)
        .trim();
    StringTokenizer st = new StringTokenizer(cookieLine, " ;");
    String name = null;
    String value = null;
    String domain = null;
    String path = null;
    int version = 1;

    CookieStore store = getCookieStore();

    if (cookieLine.indexOf("$Version") == -1) {
      version = 0;
    }

    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      int eqIndex = token.indexOf('=');

      if (token.charAt(0) != '$' && !token.startsWith("Domain")
          && !token.startsWith("Path") && eqIndex > 0) {
        name = token.substring(0, eqIndex);
        value = token.substring(eqIndex + 1);
      } else if (token.indexOf("Domain") > -1 && eqIndex > 0) {
        domain = token.substring(eqIndex + 1);
      } else if (token.indexOf("Path") > -1 && eqIndex > 0) {
        path = token.substring(eqIndex + 1);
      }
    }

    if (name != null) {
      org.apache.http.impl.cookie.BasicClientCookie cookie =
          new org.apache.http.impl.cookie.BasicClientCookie(name, value != null ? value : "");
      cookie.setVersion(version);
      if (domain != null) {
        cookie.setDomain(domain);
      }
      if (path != null) {
        cookie.setPath(path);
      }
      store.addCookie(cookie);
    }

  }

  /**
   * Adds any support request headers necessary for this request. These headers
   * will be added based on the state of the request.
   */
  private void addSupportHeaders() {

    // Authentication headers
    // NOTE: Possibly move logic to generic method
    switch (_authType) {
    case NO_AUTHENTICATION:
      break;
    case BASIC_AUTHENTICATION:
      setBasicAuthorizationHeader();
      break;
    case DIGEST_AUTHENTICATION:
      throw new UnsupportedOperationException(
          "Digest Authentication is not currently " + "supported");
    }

    // A Host header will be added to each request to handle
    // cases where virtual hosts are used, or there is no DNS
    // available on the system where the container is running.
    setHostHeader();

    // Content length header
    setContentLengthHeader();

    // Cookies
    setCookieHeader();
  }

  /**
   * Sets a basic authentication header in the request is Request is configured
   * to use basic authentication
   */
  private void setBasicAuthorizationHeader() {
    org.apache.http.auth.Credentials cred = getCredentialsProvider()
        .getCredentials(new AuthScope(_host, _port, null));
    String authString = null;
    if (cred instanceof UsernamePasswordCredentials) {
      UsernamePasswordCredentials upCred = (UsernamePasswordCredentials) cred;
      authString = "Basic " + Base64.getEncoder().encodeToString(
          (upCred.getUserName() + ":" + upCred.getPassword()).getBytes(StandardCharsets.UTF_8));
    } else {
        LOGGER.finer("NULL CREDENTIALS");
    }
    if (authString != null) {
      _method.setHeader("Authorization", authString);
    }
  }

  /**
   * Sets a Content-Length header in the request if content is present
   */
  private void setContentLengthHeader() {
    if (_contentLength > 0) {
      _method.setHeader("Content-Length",
          Integer.toString(_contentLength));
    }
  }

  /**
   * Sets a host header in the request. If the configured host value is an IP
   * address, the Host header will be sent, but without any value.
   *
   * If we adhered to the HTTP/1.1 spec, the Host header must be empty of the
   * target server is identified via IP address. However, no user agents I've
   * tested follow this. And if a custom client library does this, it may not
   * work properly with the target server. For now, the Host request-header will
   * always have a value.
   */
  private void setHostHeader() {
    if (_port == DEFAULT_HTTP_PORT || _port == DEFAULT_SSL_PORT) {
      _method.setHeader("Host", _host);
    } else {
      _method.setHeader("Host", _host + ":" + _port);
    }
  }

  /**
   * Sets a Cookie header if this request is using cookies.
   */
  private void setCookieHeader() {
    if (_useCookies && _cookieStore != null) {
      java.util.List<Cookie> cookies = _cookieStore.getCookies();
      if (cookies != null && !cookies.isEmpty()) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
          Cookie cookie = cookies.get(i);
          if (i > 0) {
            cookieHeader.append("; ");
          }
          cookieHeader.append(cookie.getName()).append("=").append(cookie.getValue());
        }
        if (cookieHeader.length() > 0) {
          _method.setHeader("Cookie", cookieHeader.toString());
        }
      }
    }
  }
}
