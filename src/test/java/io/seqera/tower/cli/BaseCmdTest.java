/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.seqera.tower.cli;

import io.seqera.tower.cli.utils.ErrorReporting;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import picocli.CommandLine;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@ExtendWith(MockServerExtension.class)
public abstract class BaseCmdTest {

    public static class ExecOut {
        public Tower app;
        public String stdOut;
        public String stdErr;
        public int exitCode;

        public ExecOut stdOut(String value) {
            this.stdOut = value;
            return this;
        }

        public ExecOut stdErr(String value) {
            this.stdErr = value;
            return this;
        }

        public ExecOut exitCode(int value) {
            this.exitCode = value;
            return this;
        }

        public ExecOut app(Tower app) {
            this.app = app;
            return this;
        }
    }

    protected byte[] loadResponse(String name) {
        try {
            return this.getClass().getResourceAsStream("/runcmd/" + name + ".json").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String url(MockServerClient mock) {
        return String.format("http://localhost:%d", mock.getPort());
    }

    protected String token() {
        return "fake_auth_token";
    }

    protected ExecOut exec(MockServerClient mock, String... args) {
        Tower app = new Tower();
        StringWriter stdOut = new StringWriter();
        StringWriter stdErr = new StringWriter();
        CommandLine cmd = new CommandLine(app);
        cmd.setOut(new PrintWriter(stdOut));
        cmd.setErr(new PrintWriter(stdErr));

        int exitCode = cmd.execute(ArrayUtils.insert(0, args, String.format("--url=%s", url(mock)), String.format("--access-token=%s", token())));

        return new ExecOut()
                .app(app)
                .stdOut(StringUtils.chop(stdOut.toString()))
                .stdErr(StringUtils.chop(stdErr.toString()))
                .exitCode(exitCode);
    }

    protected String errorMessage(Tower app, Exception e) {
        StringWriter out = new StringWriter();
        ErrorReporting.errorMessage(app, new PrintWriter(out), e);
        return StringUtils.chop(out.toString());
    }

}
