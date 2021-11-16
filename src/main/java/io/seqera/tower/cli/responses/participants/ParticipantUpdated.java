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

package io.seqera.tower.cli.responses.participants;

import io.seqera.tower.cli.responses.Response;

public class ParticipantUpdated extends Response {

    final public String workspaceName;
    final public String name;
    final public String role;

    public ParticipantUpdated(String workspaceName, String name, String role) {
        this.workspaceName = workspaceName;
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return ansi(String.format("%n  @|yellow Participant '%s' has now role '%s' for workspace '%s'|@%n", name, role, workspaceName));
    }
}