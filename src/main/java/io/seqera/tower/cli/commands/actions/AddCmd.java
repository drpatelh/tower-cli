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

package io.seqera.tower.cli.commands.actions;

import io.seqera.tower.ApiException;
import io.seqera.tower.cli.commands.actions.add.AddGitHubCmd;
import io.seqera.tower.cli.commands.actions.add.AddTowerCmd;
import io.seqera.tower.cli.exceptions.ShowUsageException;
import io.seqera.tower.cli.responses.Response;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(
        name = "add",
        description = "Add a new Pipeline Action.",
        subcommands = {
                AddGitHubCmd.class,
                AddTowerCmd.class
        }
)
public class AddCmd extends AbstractActionsCmd {
    @Override
    protected Response exec() throws ApiException, IOException {
        throw new ShowUsageException(getSpec(), "Missing Required Subcommand");
    }
}
