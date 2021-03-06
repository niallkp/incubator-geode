/*
 *
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
 *
 */

package com.vmware.gemfire.tools.pulse.internal.service;

import java.util.Formatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vmware.gemfire.tools.pulse.internal.data.Cluster;
import com.vmware.gemfire.tools.pulse.internal.data.Repository;
import com.vmware.gemfire.tools.pulse.internal.json.JSONArray;
import com.vmware.gemfire.tools.pulse.internal.json.JSONException;
import com.vmware.gemfire.tools.pulse.internal.json.JSONObject;
import com.vmware.gemfire.tools.pulse.internal.util.StringUtils;
import com.vmware.gemfire.tools.pulse.internal.util.TimeUtils;

/**
 * Class MemberClientsService
 * 
 * This class contains implementations of getting Memeber's Clients.
 * 
 * @author Sachin K
 * @since version 7.5
 */
@Component
@Service("MemberClients")
@Scope("singleton")
public class MemberClientsService implements PulseService {

  // String constants used for forming a json response
  private final String NAME = "name";
  private final String HOST = "host";

  public JSONObject execute(final HttpServletRequest request) throws Exception {

    // get cluster object
    Cluster cluster = Repository.get().getCluster();

    // json object to be sent as response
    JSONObject responseJSON = new JSONObject();

    try {
      JSONObject requestDataJSON = new JSONObject(
          request.getParameter("pulseData"));
      String memberName = requestDataJSON.getJSONObject("MemberClients")
          .getString("memberName");

      JSONArray clientListJson = new JSONArray();

      Cluster.Member clusterMember = cluster.getMember(StringUtils
          .makeCompliantName(memberName));
      if (clusterMember != null) {
        responseJSON.put("memberId", clusterMember.getId());
        responseJSON.put(this.NAME, clusterMember.getName());
        responseJSON.put(this.HOST, clusterMember.getHost());

        // member's clients

        Cluster.Client[] memberClients = clusterMember.getMemberClients();
        for (Cluster.Client memberClient : memberClients) {
          JSONObject regionJSON = new JSONObject();
          regionJSON.put("clientId", memberClient.getId());
          regionJSON.put(this.NAME, memberClient.getName());
          regionJSON.put(this.HOST, memberClient.getHost());
          regionJSON.put("queueSize", memberClient.getQueueSize());
          regionJSON.put("clientCQCount", memberClient.getClientCQCount()); 
          if(memberClient.isConnected()){
            regionJSON.put("isConnected", "Yes");
          }else{
            regionJSON.put("isConnected", "No");
          }
          
          if(memberClient.isSubscriptionEnabled()){ 
            regionJSON.put("isSubscriptionEnabled", "Yes"); 
          }else{ 
            regionJSON.put("isSubscriptionEnabled", "No"); 
          } 

          regionJSON.put("uptime",
              TimeUtils.convertTimeSecondsToHMS(memberClient.getUptime()));
          Formatter fmt = new Formatter();
          regionJSON.put("cpuUsage",
              fmt.format("%.4f", memberClient.getCpuUsage()).toString());
          // regionJSON.put("cpuUsage", memberClient.getCpuUsage());
          regionJSON.put("threads", memberClient.getThreads());
          regionJSON.put("gets", memberClient.getGets());
          regionJSON.put("puts", memberClient.getPuts());

          clientListJson.put(regionJSON);
          fmt.close();

        }
        responseJSON.put("memberClients", clientListJson);
      }
      // Send json response
      return responseJSON;

    } catch (JSONException e) {
      throw new Exception(e);
    }
  }
}
