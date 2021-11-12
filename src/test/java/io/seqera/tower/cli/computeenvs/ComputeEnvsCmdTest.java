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
package io.seqera.tower.cli.computeenvs;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.seqera.tower.ApiException;
import io.seqera.tower.JSON;
import io.seqera.tower.cli.BaseCmdTest;
import io.seqera.tower.cli.exceptions.ComputeEnvNotFoundException;
import io.seqera.tower.cli.responses.ComputeEnvCreated;
import io.seqera.tower.cli.responses.ComputeEnvDeleted;
import io.seqera.tower.cli.responses.ComputeEnvList;
import io.seqera.tower.cli.responses.ComputeEnvView;
import io.seqera.tower.cli.responses.ComputeEnvs.ComputeEnvExport;
import io.seqera.tower.cli.responses.ComputeEnvs.ComputeEnvsPrimaryGet;
import io.seqera.tower.cli.responses.ComputeEnvs.ComputeEnvsPrimarySet;
import io.seqera.tower.model.AwsBatchConfig;
import io.seqera.tower.model.ComputeConfig;
import io.seqera.tower.model.ComputeEnv;
import io.seqera.tower.model.ComputeEnvStatus;
import io.seqera.tower.model.ForgeConfig;
import io.seqera.tower.model.ListComputeEnvsResponseEntry;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.seqera.tower.cli.commands.AbstractApiCmd.USER_WORKSPACE_NAME;
import static io.seqera.tower.cli.utils.JsonHelper.parseJson;
import static io.seqera.tower.cli.utils.JsonHelper.prettyJson;
import static org.apache.commons.lang3.StringUtils.chop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class ComputeEnvsCmdTest extends BaseCmdTest {

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
    void testViewAwsForge(MockServerClient mock) throws JsonProcessingException {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44ow"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "isnEDBLvHDAIteOEF44ow");

        assertEquals("", out.stdErr);
        assertEquals(StringUtils.chop(new ComputeEnvView("isnEDBLvHDAIteOEF44ow", USER_WORKSPACE_NAME,
                parseJson("{\"id\": \"isnEDBLvHDAIteOEF44ow\", \"dateCreated\": \"2021-09-08T11:19:24Z\", \"lastUpdated\": \"2021-09-08T11:20:08Z\"}", ComputeEnv.class)
                        .name("demo")
                        .platform(ComputeEnv.PlatformEnum.AWS_BATCH)
                        .status(ComputeEnvStatus.AVAILABLE)
                        .credentialsId("6g0ER59L4ZoE5zpOmUP48D")
                        .config(
                                parseJson(" {\"discriminator\": \"aws-batch\"}", AwsBatchConfig.class)
                                        .region("eu-west-1")
                                        .cliPath("/home/ec2-user/miniconda/bin/aws")
                                        .workDir("s3://nextflow-ci/jordeu")
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
    void testViewAwsManual(MockServerClient mock) throws JsonProcessingException {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/53aWhB2qJroy0i51FOrFAC"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view_aws_manual")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "view", "-i", "53aWhB2qJroy0i51FOrFAC");

        assertEquals("", out.stdErr);
        assertEquals(StringUtils.chop(new ComputeEnvView("53aWhB2qJroy0i51FOrFAC", USER_WORKSPACE_NAME,
                parseJson("{\"id\": \"53aWhB2qJroy0i51FOrFAC\", \"dateCreated\": \"2021-09-08T15:19:08Z\", \"lastUpdated\": \"2021-09-08T15:19:08Z\"}", ComputeEnv.class)
                        .name("manual")
                        .platform(ComputeEnv.PlatformEnum.AWS_BATCH)
                        .status(ComputeEnvStatus.AVAILABLE)
                        .credentialsId("6g0ER59L4ZoE5zpOmUP48D")
                        .config(
                                parseJson(" {\"discriminator\": \"aws-batch\"}", AwsBatchConfig.class)
                                        .region("eu-west-1")
                                        .computeQueue("TowerForge-isnEDBLvHDAIteOEF44ow-work")
                                        .headQueue("TowerForge-isnEDBLvHDAIteOEF44ow-head")
                                        .cliPath("/home/ec2-user/miniconda/bin/aws")
                                        .workDir("s3://nextflow-ci/jordeu")
                        )
        ).toString()), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    void testViewAwsManualJSON(MockServerClient mock) throws JsonProcessingException {

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/53aWhB2qJroy0i51FOrFAC"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view_aws_manual")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "--output", "json", "compute-envs", "view", "-i", "53aWhB2qJroy0i51FOrFAC");

        assertEquals("", out.stdErr);
        assertEquals(prettyJson(new ComputeEnvView("53aWhB2qJroy0i51FOrFAC", USER_WORKSPACE_NAME,
                parseJson("{\"id\": \"53aWhB2qJroy0i51FOrFAC\", \"dateCreated\": \"2021-09-08T15:19:08Z\", \"lastUpdated\": \"2021-09-08T15:19:08Z\"}", ComputeEnv.class)
                        .name("manual")
                        .platform(ComputeEnv.PlatformEnum.AWS_BATCH)
                        .status(ComputeEnvStatus.AVAILABLE)
                        .credentialsId("6g0ER59L4ZoE5zpOmUP48D")
                        .config(
                                parseJson(" {\"discriminator\": \"aws-batch\"}", AwsBatchConfig.class)
                                        .region("eu-west-1")
                                        .computeQueue("TowerForge-isnEDBLvHDAIteOEF44ow-work")
                                        .headQueue("TowerForge-isnEDBLvHDAIteOEF44ow-head")
                                        .cliPath("/home/ec2-user/miniconda/bin/aws")
                                        .workDir("s3://nextflow-ci/jordeu")
                                        .volumes(List.of())

                        )
        ).getJSON()), out.stdOut);
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

    @Test
    public void testCreateWithoutSubCommands(MockServerClient mock) {
        ExecOut out = exec(mock, "compute-envs", "create");
        assertEquals(-1, out.exitCode);
        assertTrue(out.stdErr.contains("Missing Required Subcommand"));
    }

    @Test
    public void testExport(MockServerClient mock) throws JsonProcessingException {
        mock.when(
                request().withMethod("GET").withPath("/compute-envs"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_envs_list")).withContentType(MediaType.APPLICATION_JSON)
        );

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/3xkkzYH2nbD3nZjrzKm0oR"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "export", "-n", "ce1");

        ComputeConfig computeConfig = parseJson("{\n" +
                "      \"region\": \"eu-west-1\",\n" +
                "      \"computeJobRole\": null,\n" +
                "      \"headJobRole\": null,\n" +
                "      \"cliPath\": \"/home/ec2-user/miniconda/bin/aws\",\n" +
                "      \"workDir\": \"s3://nextflow-ci/jordeu\",\n" +
                "      \"preRunScript\": null,\n" +
                "      \"postRunScript\": null,\n" +
                "      \"headJobCpus\": null,\n" +
                "      \"headJobMemoryMb\": null,\n" +
                "      \"forge\": {\n" +
                "        \"type\": \"SPOT\",\n" +
                "        \"minCpus\": 0,\n" +
                "        \"maxCpus\": 123,\n" +
                "        \"gpuEnabled\": false,\n" +
                "        \"ebsAutoScale\": true,\n" +
                "        \"instanceTypes\": null,\n" +
                "        \"allocStrategy\": null,\n" +
                "        \"imageId\": null,\n" +
                "        \"vpcId\": null,\n" +
                "        \"subnets\": null,\n" +
                "        \"securityGroups\": null,\n" +
                "        \"fsxMount\": null,\n" +
                "        \"fsxName\": null,\n" +
                "        \"fsxSize\": null,\n" +
                "        \"disposeOnDeletion\": true,\n" +
                "        \"ec2KeyPair\": null,\n" +
                "        \"allowBuckets\": null,\n" +
                "        \"ebsBlockSize\": null,\n" +
                "        \"fusionEnabled\": true,\n" +
                "        \"bidPercentage\": null,\n" +
                "        \"efsCreate\": null,\n" +
                "        \"efsId\": null,\n" +
                "        \"efsMount\": null\n" +
                "      },\n" +
                "      \"discriminator\": \"aws-batch\"\n" +
                "    },\n" +
                "    \"lastUsed\": null,\n" +
                "    \"deleted\": null,\n" +
                "    \"message\": null,\n" +
                "    \"primary\": null,\n" +
                "    \"credentialsId\": \"6g0ER59L4ZoE5zpOmUP48D\"\n" +
                "  }", ComputeConfig.class);

        String configOutput = new JSON().getContext(ComputeConfig.class).writerWithDefaultPrettyPrinter().writeValueAsString(computeConfig);

        assertEquals("", out.stdErr);
        assertEquals(new ComputeEnvExport(configOutput, null).toString(), out.stdOut);
        assertEquals(0, out.exitCode);

    }

    @Test
    public void testImport(MockServerClient mock) throws IOException {
        mock.when(
                request().withMethod("GET").withPath("/credentials").withQueryStringParameter("platformId", "aws-batch"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"credentials\":[{\"id\":\"6g0ER59L4ZoE5zpOmUP48D\",\"name\":\"aws\",\"description\":null,\"discriminator\":\"aws\",\"baseUrl\":null,\"category\":null,\"deleted\":null,\"lastUsed\":\"2021-09-09T07:20:53Z\",\"dateCreated\":\"2021-09-08T05:48:51Z\",\"lastUpdated\":\"2021-09-08T05:48:51Z\"}]}").withContentType(MediaType.APPLICATION_JSON)
        );

        mock.when(
                request().withMethod("POST").withPath("/compute-envs").withBody("{\"computeEnv\":{\"name\":\"json\",\"platform\":\"aws-batch\",\"config\":{\"region\":\"eu-west-1\",\"cliPath\":\"/home/ec2-user/miniconda/bin/aws\",\"workDir\":\"s3://nextflow-ci/jordeu\",\"forge\":{\"type\":\"SPOT\",\"minCpus\":0,\"maxCpus\":123,\"gpuEnabled\":false,\"ebsAutoScale\":true,\"disposeOnDeletion\":true,\"fusionEnabled\":false,\"efsCreate\":true},\"discriminator\":\"aws-batch\"},\"credentialsId\":\"6g0ER59L4ZoE5zpOmUP48D\"}}"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"computeEnvId\":\"3T6xWeFD63QIuzdAowvSTC\"}").withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "import", tempFile(new String(loadResource("cejson"), StandardCharsets.UTF_8), "ce", "json"), "-n", "json", "-i", "6g0ER59L4ZoE5zpOmUP48D");

        assertEquals("", out.stdErr);
        assertEquals(new ComputeEnvCreated(ComputeEnv.PlatformEnum.AWS_BATCH.getValue(), "json", USER_WORKSPACE_NAME).toString(), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    public void getPrimary(MockServerClient mock) throws JsonProcessingException {
        mock.when(
                request().withMethod("GET").withPath("/compute-envs"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody("{\"computeEnvs\":[{\"id\":\"isnEDBLvHDAIteOEF44ow\",\"name\":\"demo\",\"platform\":\"aws-batch\",\"status\":\"AVAILABLE\",\"message\":null,\"lastUsed\":null,\"primary\":true,\"workspaceName\":null,\"visibility\":null}]}").withContentType(MediaType.APPLICATION_JSON)
        );

        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44ow"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view")).withContentType(MediaType.APPLICATION_JSON)
        );

        ExecOut out = exec(mock, "compute-envs", "primary", "get");

        ComputeEnv ce = parseJson("{\"id\":\"isnEDBLvHDAIteOEF44ow\",\"name\":\"demo\",\"platform\":\"aws-batch\",\"status\":\"AVAILABLE\",\"message\":null,\"lastUsed\":null,\"primary\":true,\"workspaceName\":null,\"visibility\":null}", ComputeEnv.class);

        assertEquals("", out.stdErr);
        assertEquals(new ComputeEnvsPrimaryGet(USER_WORKSPACE_NAME, ce).toString(), out.stdOut);
        assertEquals(0, out.exitCode);
    }

    @Test
    public void setPrimary(MockServerClient mock) throws JsonProcessingException {
        mock.when(
                request().withMethod("GET").withPath("/compute-envs/isnEDBLvHDAIteOEF44ow"), exactly(1)
        ).respond(
                response().withStatusCode(200).withBody(loadResource("compute_env_view")).withContentType(MediaType.APPLICATION_JSON)
        );

        mock.when(
                request().withMethod("POST").withPath("/compute-envs/isnEDBLvHDAIteOEF44ow/primary"), exactly(1)
        ).respond(
                response().withStatusCode(204)
        );

        ExecOut out = exec(mock, "compute-envs", "primary", "set", "-i", "isnEDBLvHDAIteOEF44ow");

        ComputeEnv ce = parseJson("{\"id\":\"isnEDBLvHDAIteOEF44ow\",\"name\":\"demo\",\"platform\":\"aws-batch\",\"status\":\"AVAILABLE\",\"message\":null,\"lastUsed\":null,\"primary\":true,\"workspaceName\":null,\"visibility\":null}", ComputeEnv.class);

        assertEquals("", out.stdErr);
        assertEquals(new ComputeEnvsPrimarySet(USER_WORKSPACE_NAME, ce).toString(), out.stdOut);
        assertEquals(0, out.exitCode);
    }
}
