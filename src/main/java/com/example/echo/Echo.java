/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not  use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.echo;

import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiIssuerAudience;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.UnauthorizedException;

/** The Echo API which Endpoints will be exposing. */
// [START echo_api_annotation]
@Api(
    name = "echo",
    version = "v1",
    namespace =
      @ApiNamespace(
        ownerDomain = "echo.example.com",
        ownerName = "echo.example.com",
        packagePath = ""
      )
    )
// [END echo_api_annotation]
public class Echo {
  /**
   * Echoes the received message back. If n is a non-negative integer, the message is copied that
   * many times in the returned message.
   *
   * Note that name is specified and will override the default name of "{class name}.{method
   * name}". For example, the default is "echo.echo".
   *
   * Note that httpMethod is not specified. This will default to a reasonable HTTP method
   * depending on the API method name. In this case, the HTTP method will default to POST.
   */
//./endpoints-framework-tools-2.0.0/bin/endpoints-framework-tools get-openapi-doc --hostname=rentping-demo.appspot.com --war=build/exploded-app/ com.example.echo.Echo

  @ApiMethod(name = "hi")
  public Message hi() {
    Message message = new Message();
    message.setMessage("hola");
    return doEcho(message, 1);
  }

  /**
   * Echoes the received message back. If n is a non-negative integer, the message is copied that
   * many times in the returned message.
   *
   * Note that name is specified and will override the default name of "{class name}.{method
   * name}". For example, the default is "echo.echo".
   *
   * Note that httpMethod is not specified. This will default to a reasonable HTTP method
   * depending on the API method name. In this case, the HTTP method will default to POST.
   */


  private Message doEcho(Message message, Integer n) {
    if (n != null && n >= 0) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          sb.append(" ");
        }
        sb.append(message.getMessage());
      }
      message.setMessage(sb.toString());
    }
    return message;
  }

  /**
   * Gets the authenticated user's email. If the user is not authenticated, this will return an HTTP
   * 401.
   *
   * Note that name is not specified. This will default to "{class name}.{method name}". For
   * example, the default is "echo.getUserEmail".
   *
   * Note that httpMethod is not required here. Without httpMethod, this will default to GET due
   * to the API method name. httpMethod is added here for example purposes.
   */
  // [START google_id_token_auth]
  @ApiMethod(
      httpMethod = ApiMethod.HttpMethod.GET,
      authenticators = {EspAuthenticator.class},
      audiences = {"YOUR_OAUTH_CLIENT_ID"},
      clientIds = {"YOUR_OAUTH_CLIENT_ID"}
      )
  public Email getUserEmail(User user) throws UnauthorizedException {
    if (user == null) {
      throw new UnauthorizedException("Invalid credentials");
    }

    Email response = new Email();
    response.setEmail(user.getEmail());
    return response;
  }
  // [END google_id_token_auth]

  /**
   * Gets the authenticated user's email. If the user is not authenticated, this will return an HTTP
   * 401.
   *
   * Note that name is not specified. This will default to "{class name}.{method name}". For
   * example, the default is "echo.getUserEmail".
   *
   * Note that httpMethod is not required here. Without httpMethod, this will default to GET due
   * to the API method name. httpMethod is added here for example purposes.
   */

}
