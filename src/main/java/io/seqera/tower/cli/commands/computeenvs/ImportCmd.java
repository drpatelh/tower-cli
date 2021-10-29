package io.seqera.tower.cli.commands.computeenvs;

import io.seqera.tower.ApiException;
import io.seqera.tower.cli.responses.ComputeEnvCreated;
import io.seqera.tower.cli.responses.Response;
import io.seqera.tower.cli.utils.FilesHelper;
import io.seqera.tower.model.CreateComputeEnvRequest;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;

import static io.seqera.tower.cli.utils.JsonHelper.parseJson;

@CommandLine.Command(
        name = "import",
        description = "Create a compute environment from file content"
)
public class ImportCmd extends AbstractComputeEnvCmd {

    @CommandLine.Option(names = {"-n", "--name"}, description = "Compute environment name", required = true)
    public String name;

    @CommandLine.Option(names = {"-w", "--workspace"}, description = "Workspace ID to create new pipeline", required = true)
    public Long workspaceId = null;

    @CommandLine.Parameters(index = "0", paramLabel = "FILENAME", description = "File name to import", arity = "1")
    Path fileName = null;

    @Override
    protected Response exec() throws ApiException, IOException {
        CreateComputeEnvRequest request = parseJson(FilesHelper.readString(fileName), CreateComputeEnvRequest.class);
        request.getComputeEnv().setName(name);

        api().createComputeEnv(request, workspaceId);

        return new ComputeEnvCreated(request.getComputeEnv().getPlatform().getValue(), name, workspaceRef());
    }
}