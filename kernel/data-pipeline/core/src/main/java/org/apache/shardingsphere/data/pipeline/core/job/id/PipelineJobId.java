/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.data.pipeline.core.job.id;

import org.apache.shardingsphere.data.pipeline.core.context.PipelineContextKey;
import org.apache.shardingsphere.data.pipeline.core.job.type.PipelineJobType;

import java.util.Optional;

/**
 * Pipeline job id.
 */
public interface PipelineJobId {
    
    String CURRENT_VERSION = "02";
    
    /**
     * Get pipeline job type.
     * 
     * @return pipeline job type
     */
    PipelineJobType getJobType();
    
    /**
     * Get pipeline context key.
     *
     * @return pipeline context key
     */
    PipelineContextKey getContextKey();
    
    /**
     * Get parent job id.
     *
     * @return parent job id
     */
    default Optional<String> getParentJobId() {
        return Optional.empty();
    }
    
    /**
     * Get sequence.
     *
     * @return sequence
     */
    default Optional<Integer> getSequence() {
        return Optional.empty();
    }
}
