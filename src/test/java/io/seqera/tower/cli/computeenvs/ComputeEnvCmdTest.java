/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.seqera.tower.cli.computeenvs;

import io.seqera.tower.ApiException;
import io.seqera.tower.cli.BaseCmdTest;
import io.seqera.tower.cli.exceptions.ComputeEnvNotFoundException;
import io.seqera.tower.cli.responses.ComputeEnvDeleted;
import io.seqera.tower.cli.responses.ComputeEnvList;
import io.seqera.tower.cli.responses.ComputeEnvView;
import io.seqera.tower.model.AwsBatchConfig;
import io.seqera.tower.model.ComputeEnv;
import io.seqera.tower.model.ComputeEnvStatus;
import io.seqera.tower.model.ForgeConfig;
import io.seqera.tower.model.ListComputeEnvsResponseEntry;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;

import java.time.OffsetDateTime;
import java.util.List;

import static io.seqera.tower.cli.commands.AbstractApiCmd.USER_WORKSPACE_NAME;
import static org.apache.commons.lang3.StringUtils.chop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class ComputeEnvCmdTest extends BaseCmdTest {

    @Test
    void testDelete(MockServerClient mock) {
        mock.when(
                request().withMethod("DELETE").withPath("/compute-envs/vYOK4vn7spw7bHHWBDXZ2"), exactly(1)
        ).respond(
                response().withStatusCode(204)
        );

        ExecOut out = exec(mock, "compute-envs", "delete", "-i", "vYOK4vn7spw7bHHWBDXZ2");

        assertEquals("", out.stdErr);
        assertEquals(new ComputeEnvDeleted("vYOK4vn7spw7bHHWBDXZ2", USER_WORKSPACE_NAME).toString(), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    void testDeleteInvalidAuth(MockServerClient mock) {
        mock.when(
                request().withMethod("DELETE").withPath("/compute-envs/vYOK4vn7spw7bHHWBDXZ8"), exactly(1)
        ).respond(
                response().withStatusCode(401)
        );

        ExecOut out = exec(mock, "compute-envs", "delete", "-i", "vYOK4vn7spw7bHHWBDXZ8");

        assertEquals(errorMessage(out.app, new ApiException(401, "Unauthorized")), out.stdErr);
        assertEquals("", out.stdOut);
        assertEquals(-1, out.exitCode);
    }

    @Test
    void testDeleteNotFound(MockServerClient mock) {
        mock.when(
                request().withMethod("DELETE").withPath("/compute-envs/vYOK4vn7spw7bHHWBDXZ3"), exactly(1)
        ).respond(
                response().withStatusCode(403)
        );

        ExecOut out = exec(mock, "compute-envs", "delete", "-i", "vYOK4vn7spw7bHHWBDXZ3");

        assertEquals(errorMessage(out.app, new ComputeEnvNotFoundException("vYOK4vn7spw7bHHWBDXZ3", USER_WORKSPACE_NAME)), out.stdErr);
        assertEquals("", out.stdOut);
        assertEquals(-1, out.exitCode);
    }

    @Test
    void testList(MockServerClient mock) {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"computeEnvs\":[{\"id\":\"vYOK4vn7spw7bHHWBDXZ2\",\"name\":\"demo\",\"platform\":\"aws-batch\",\"status\":\"AVAILABLE\",\"message\":null,\"lastUsed\":null,\"primary\":null,\"workspaceName\":null,\"visibility\":null}]}").withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "list");

        assertEquals("", out.stdErr);
        assertEquals(chop(new ComputeEnvList(USER_WORKSPACE_NAME, List.of(
                new ListComputeEnvsResponseEntry()
                        .id("vYOK4vn7spw7bHHWBDXZ2")
                        .name("demo")
                        .platform("aws-batch")
                        .status(ComputeEnvStatus.AVAILABLE)
        )).toString()), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    void testListEmpty(MockServerClient mock) {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"computeEnvs\":[]}").withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "list");

        assertEquals("", out.stdErr);
        assertEquals(chop(new ComputeEnvList(USER_WORKSPACE_NAME, List.of()).toString()), out.stdOut);
        assertEquals(0, out.exitCode);
    }


    @Test
    void testViewAwsForge(MockServerClient mock) {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44ow"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "isnEDBLvHDAIteOEF44ow");

        assertEquals("", out.stdErr);
        assertEquals(StringUtils.chop(new ComputeEnvView("isnEDBLvHDAIteOEF44ow", USER_WORKSPACE_NAME,
                new ComputeEnv()
                        .id("isnEDBLvHDAIteOEF44ow")
                        .name("demo")
                        .platform(ComputeEnv.PlatformEnum.AWS_BATCH)
                        .dateCreated(OffsetDateTime.parse("2021-09-08T11:19:24Z"))
                        .lastUpdated(OffsetDateTime.parse("2021-09-08T11:20:08Z"))
                        .status(ComputeEnvStatus.AVAILABLE)
                        .credentialsId("6g0ER59L4ZoE5zpOmUP48D")
                        .config(
                                new AwsBatchConfig()
                                        .region("eu-west-1")
                                        .cliPath("/home/ec2-user/miniconda/bin/aws")
                                        .workDir("s3://nextflow-ci/jordeu")
                                        .platform("aws-batch")
                                        .forge(
                                                new ForgeConfig()
                                                        .type(ForgeConfig.TypeEnum.SPOT)
                                                        .minCpus(0)
                                                        .maxCpus(123)
                                                        .gpuEnabled(false)
                                                        .ebsAutoScale(true)
                                                        .disposeOnDeletion(true)
                                                        .fusionEnabled(true)
                                        )
                        )
        ).toString()), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    void testViewAwsManual(MockServerClient mock) {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/53aWhB2qJroy0i51FOrFAC"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view_aws_manual")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "53aWhB2qJroy0i51FOrFAC");

        assertEquals("", out.stdErr);
        assertEquals(StringUtils.chop(new ComputeEnvView("53aWhB2qJroy0i51FOrFAC", USER_WORKSPACE_NAME,
                new ComputeEnv()
                        .id("53aWhB2qJroy0i51FOrFAC")
                        .name("manual")
                        .platform(ComputeEnv.PlatformEnum.AWS_BATCH)
                        .dateCreated(OffsetDateTime.parse("2021-09-08T15:19:08Z"))
                        .lastUpdated(OffsetDateTime.parse("2021-09-08T15:19:08Z"))
                        .status(ComputeEnvStatus.AVAILABLE)
                        .credentialsId("6g0ER59L4ZoE5zpOmUP48D")
                        .config(
                                new AwsBatchConfig()
                                        .region("eu-west-1")
                                        .computeQueue("TowerForge-isnEDBLvHDAIteOEF44ow-work")
                                        .headQueue("TowerForge-isnEDBLvHDAIteOEF44ow-head")
                                        .cliPath("/home/ec2-user/miniconda/bin/aws")
                                        .workDir("s3://nextflow-ci/jordeu")
                                        .platform("aws-batch")

                        )
        ).toString()), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    void testViewNotFound(MockServerClient mock) {
        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44or"), exactly(1)
        ).respond(
                response().withStatusCode(403)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "isnEDBLvHDAIteOEF44or");

        assertEquals(errorMessage(out.app, new ComputeEnvNotFoundException("isnEDBLvHDAIteOEF44or", USER_WORKSPACE_NAME)), out.stdErr);
        assertEquals("", out.stdOut);
        assertEquals(-1, out.exitCode);
    }

    @Test
    void testViewInvalidAuth(MockServerClient mock) {
        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44om"), exactly(1)
        ).respond(
                response().withStatusCode(401)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "isnEDBLvHDAIteOEF44om");

        assertEquals(errorMessage(out.app, new ApiException(401, "Unauthorized")), out.stdErr);
        assertEquals("", out.stdOut);
        assertEquals(-1, out.exitCode);
    }


}
