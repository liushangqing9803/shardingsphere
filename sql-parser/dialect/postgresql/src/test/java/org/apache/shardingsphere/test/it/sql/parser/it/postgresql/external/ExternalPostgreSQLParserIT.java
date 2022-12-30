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

package org.apache.shardingsphere.test.it.sql.parser.it.postgresql.external;

import org.apache.shardingsphere.test.it.sql.parser.external.ExternalSQLParserIT;
import org.apache.shardingsphere.test.it.sql.parser.external.ExternalSQLParserTestParameter;
import org.apache.shardingsphere.test.it.sql.parser.external.loader.ExternalSQLParserTestParameterLoader;
import org.apache.shardingsphere.test.it.sql.parser.external.loader.strategy.impl.GitHubTestParameterLoadStrategy;
import org.junit.runners.Parameterized.Parameters;

import java.net.URI;
import java.util.Collection;

public final class ExternalPostgreSQLParserIT extends ExternalSQLParserIT {
    
    public ExternalPostgreSQLParserIT(final ExternalSQLParserTestParameter testParam) {
        super(testParam);
    }
    
    @Parameters(name = "{0} (PostgreSQL) -> {1}")
    public static Collection<ExternalSQLParserTestParameter> getTestParameters() {
        String caseURL = "https://github.com/postgres/postgres/tree/master/src/test/regress/sql";
        String resultURL = "https://github.com/postgres/postgres/tree/master/src/test/regress/expected";
        return new ExternalSQLParserTestParameterLoader(new GitHubTestParameterLoadStrategy()).load(URI.create(caseURL), URI.create(resultURL), "PostgreSQL", "CSV");
    }
}
