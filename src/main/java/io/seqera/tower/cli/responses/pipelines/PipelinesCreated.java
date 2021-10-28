package io.seqera.tower.cli.responses.pipelines;

import io.seqera.tower.cli.responses.Response;

public class PipelinesCreated extends Response {

    public final String workspaceRef;
    public final String pipelineName;

    public PipelinesCreated(String workspaceRef, String pipelineName) {
        this.workspaceRef = workspaceRef;
        this.pipelineName = pipelineName;
    }

    @Override
    public String toString() {
        return ansi(String.format("%n  @|yellow New pipeline '%s' added at %s workspace|@%n", pipelineName, workspaceRef));
    }
}