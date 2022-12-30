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

package org.apache.shardingsphere.test.e2e.engine.rdl;

import org.apache.shardingsphere.test.e2e.cases.SQLCommandType;
import org.apache.shardingsphere.test.e2e.framework.param.array.E2ETestParameterFactory;
import org.apache.shardingsphere.test.e2e.framework.param.model.AssertionTestParameter;
import org.apache.shardingsphere.test.e2e.framework.runner.ParallelRunningStrategy;
import org.apache.shardingsphere.test.e2e.framework.runner.ParallelRunningStrategy.ParallelLevel;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

@ParallelRunningStrategy(ParallelLevel.SCENARIO)
public final class GeneralRDLE2EIT extends BaseRDLE2EIT {
    
    public GeneralRDLE2EIT(final AssertionTestParameter testParam) {
        super(testParam);
    }
    
    @Parameters(name = "{0}")
    public static Collection<AssertionTestParameter> getTestParameters() {
        return E2ETestParameterFactory.getAssertionTestParameters(SQLCommandType.RDL);
    }
    
    @Test
    public void assertExecute() throws SQLException, ParseException {
        assertNotNull("Assertion SQL is required", getAssertion().getAssertionSQL());
        try (Connection connection = getTargetDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                executeSQLCase(statement);
                sleep();
                assertResultSet(statement);
            }
        }
    }
    
    private void executeSQLCase(final Statement statement) throws SQLException, ParseException {
        statement.execute(getSQL());
    }
    
    private void assertResultSet(final Statement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery(getAssertion().getAssertionSQL().getSql())) {
            assertResultSet(resultSet);
        }
    }
}
