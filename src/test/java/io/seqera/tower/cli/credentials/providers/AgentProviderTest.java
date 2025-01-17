/*
 * Copyright (c) 2021, Seqera Labs.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.seqera.tower.cli.credentials.providers;

import io.seqera.tower.cli.BaseCmdTest;
import io.seqera.tower.cli.responses.CredentialsAdded;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;

import java.io.IOException;

import static io.seqera.tower.cli.commands.AbstractApiCmd.USER_WORKSPACE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class AgentProviderTest extends BaseCmdTest {

    @Test
    void testAdd(MockServerClient mock) throws IOException {

        mock.when(
                request().withMethod("POST").withPath("/credentials").withBody("{\"credentials\":{\"keys\":{\"connectionId\":\"connection_id\",\"workDir\":\"/work\"},\"name\":\"agent_test\",\"provider\":\"tw-agent\"}}"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"credentialsId\":\"1cz5A8cuBkB5iJliCwJCFJ\"}").withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "credentials", "add", "agent", "-n", "agent_test", "--connection-id", "connection_id", "--work-dir", "/work");

        assertEquals("", out.stdErr);
        assertEquals(new CredentialsAdded("tw_agent", "1cz5A8cuBkB5iJliCwJCFJ", "agent_test", USER_WORKSPACE_NAME).toString(), out.stdOut);
        assertEquals(0, out.exitCode);

    }

}
