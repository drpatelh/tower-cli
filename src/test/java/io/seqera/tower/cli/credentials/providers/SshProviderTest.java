/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.seqera.tower.cli.credentials.providers;

import io.seqera.tower.cli.BaseCmdTest;
import io.seqera.tower.cli.responses.CredentialsCreated;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;

import java.io.IOException;

import static io.seqera.tower.cli.commands.AbstractApiCmd.USER_WORKSPACE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class SshProviderTest extends BaseCmdTest {

    @Test
    void testCreate(MockServerClient mock) throws IOException {

        mock.when(
                request().withMethod("POST").withPath("/credentials").withBody("{\"credentials\":{\"keys\":{\"privateKey\":\"privat_key\",\"passphrase\":\"my_secret\",\"provider\":\"ssh\"},\"name\":\"ssh\",\"provider\":\"ssh\"}}"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"credentialsId\":\"1cz5A8cuBkB5iJliCwJCFU\"}").withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "credentials", "create", "ssh", "-n", "ssh", "-k", tempFile("privat_key", "id_rsa", ""), "-p", "my_secret");

        assertEquals("", out.stdErr);
        assertEquals(new CredentialsCreated("ssh", "1cz5A8cuBkB5iJliCwJCFU", "ssh", USER_WORKSPACE_NAME).toString(), out.stdOut);
        assertEquals(0, out.exitCode);

    }

}
