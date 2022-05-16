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

package org.apache.shardingsphere.infra.rewrite.fixture;

import org.apache.shardingsphere.infra.config.props.ConfigurationProperties;
import org.apache.shardingsphere.infra.rewrite.context.SQLRewriteContext;
import org.apache.shardingsphere.infra.rewrite.context.SQLRewriteContextDecorator;
import org.apache.shardingsphere.infra.route.context.RouteContext;

public final class FixtureSQLRewriteContextDecorator implements SQLRewriteContextDecorator<FixtureRule> {
    
    @Override
    public void decorate(final FixtureRule rule, final ConfigurationProperties props, final SQLRewriteContext sqlRewriteContext, final RouteContext routeContext) {
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
    
    @Override
    public Class<FixtureRule> getTypeClass() {
        return FixtureRule.class;
    }
}
