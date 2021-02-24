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

package org.apache.shardingsphere.test.integration.engine.junit.parallel.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.test.integration.engine.junit.parallel.ParallelRunnerExecutor;
import org.apache.shardingsphere.test.integration.engine.param.model.ParameterizedArray;

import java.io.Closeable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Parallel runner executor with scenario.
 */
@Slf4j
public final class ScenarioParallelRunnerExecutor implements ParallelRunnerExecutor {
    
    private final Map<ScenarioKey, ScenarioExecutorService> executorServices = new ConcurrentHashMap<>();
    
    private final Collection<Future<?>> taskFeatures = new LinkedList<>();
    
    @Override
    public void execute(final ParameterizedArray parameterizedArray, final Runnable childStatement) {
        taskFeatures.add(getExecutorService(new ScenarioKey(parameterizedArray)).submit(childStatement));
    }
    
    private ScenarioExecutorService getExecutorService(final ScenarioKey scenarioKey) {
        if (executorServices.containsKey(scenarioKey)) {
            return executorServices.get(scenarioKey);
        }
        ScenarioExecutorService newExecutorService = new ScenarioExecutorService(scenarioKey);
        if (null != executorServices.putIfAbsent(scenarioKey, newExecutorService)) {
            newExecutorService.close();
        }
        return executorServices.get(scenarioKey);
    }
    
    @Override
    public void finished() {
        taskFeatures.forEach(future -> {
            try {
                future.get();
            } catch (final InterruptedException | ExecutionException ignored) {
            }
        });
        executorServices.values().forEach(ScenarioExecutorService::close);
    }
    
    /**
     * Scenario key.
     */
    @EqualsAndHashCode
    private static final class ScenarioKey {
        
        private final String adapter;
        
        private final String scenario;
        
        private final String databaseTypeName;
        
        private ScenarioKey(final ParameterizedArray parameterizedArray) {
            adapter = parameterizedArray.getAdapter();
            scenario = parameterizedArray.getScenario();
            databaseTypeName = parameterizedArray.getDatabaseType().getName();
        }
        
        @Override
        public String toString() {
            return String.join("-", adapter, scenario, databaseTypeName);
        }
    }
    
    /**
     * Scenario executor service.
     */
    private static final class ScenarioExecutorService implements Closeable {
        
        private final ExecutorService executorService;
        
        ScenarioExecutorService(final ScenarioKey scenarioKey) {
            String threadPoolNameFormat = String.join("-", "ScenarioExecutorPool", scenarioKey.toString(), "%d");
            executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat(threadPoolNameFormat).build());
        }
        
        /**
         * Submit task.
         * 
         * @param childStatement child statement
         * @return task future
         */
        public Future<?> submit(final Runnable childStatement) {
            return executorService.submit(childStatement);
        }
        
        @Override
        public void close() {
            executorService.shutdownNow();
        }
    }
}
