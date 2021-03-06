/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gemstone.gemfire.cache;

import com.gemstone.gemfire.cache.client.ServerConnectivityException;

/**
 * Indicates that this client cannot contact any queue servers and
 * therefore cannot perform operations that require a queue, such as
 * registering interest.
 * @author dsmith
 * @since 5.7
 */
public class NoQueueServersAvailableException extends ServerConnectivityException {

  private static final long serialVersionUID = 8484086019155762365L;

  /**
   * Create a new instance of NoPrimaryAvailableException without a detail message or cause.
   */
  public NoQueueServersAvailableException() {
  }

  /**
   * 
   * Create a new instance of NoPrimaryAvailableException with a detail message
   * @param message the detail message
   */
  public NoQueueServersAvailableException(String message) {
    super(message);
  }

  /**
   * Create a new instance of NoPrimaryAvailableException with a detail message and cause
   * @param message the detail message
   * @param cause the cause
   */
  public NoQueueServersAvailableException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create a new instance of NoPrimaryAvailableException with a cause
   * @param cause the cause
   */
  public NoQueueServersAvailableException(Throwable cause) {
    super(cause);
  }

}
