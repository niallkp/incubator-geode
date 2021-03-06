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
package com.gemstone.gemfire.internal.admin.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;

import com.gemstone.gemfire.DataSerializer;
import com.gemstone.gemfire.cache.persistence.PersistentID;
import com.gemstone.gemfire.distributed.internal.membership.InternalDistributedMember;

/**
 * @author dsmith
 *
 */
public class CompactResponse extends AdminResponse {
  private HashSet<PersistentID> persistentIds;
  
  public CompactResponse() {
  }
  
  public CompactResponse(InternalDistributedMember sender, HashSet<PersistentID> persistentIds) {
    this.setRecipient(sender);
    this.persistentIds = persistentIds;
  }
  
  public HashSet<PersistentID> getPersistentIds() {
    return persistentIds;
  }
  
  @Override
  public void fromData(DataInput in) throws IOException, ClassNotFoundException {
    super.fromData(in);
    persistentIds = DataSerializer.readHashSet(in);
  }

  @Override
  public void toData(DataOutput out) throws IOException {
    super.toData(out);    
    DataSerializer.writeHashSet(persistentIds, out);
  }
  
  public CompactResponse(InternalDistributedMember sender) {
    this.setRecipient(sender);
  }

  public int getDSFID() {
    return COMPACT_RESPONSE;
  }
}
