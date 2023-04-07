package com.iscweb.service.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitMeta {

    @Getter
    @Value("${git.branch:#{null}}")
    private String gitBranch = "n/a";

    @Getter
    @Value("${git.commit.id:#{null}}")
    private String gitCommitId = "n/a";

    @Getter
    @Value("${git.commit.id.abbrev:#{null}}")
    private String gitCommitIdAbbrev = "n/a";

    @Getter
    @Value("${git.commit.message.full:#{null}}")
    private String gitCommitMessageFull = "n/a";

    @Getter
    @Value("${git.commit.message.short:#{null}}")
    private String gitCommitMessageShort = "n/a";

    @Getter
    @Value("${git.commit.time:#{null}}")
    private String gitCommitTime = "n/a";

    @Getter
    @Value("${git.commit.user.email:#{null}}")
    private String gitCommitUserEmail = "n/a";

    @Getter
    @Value("${git.commit.user.name:#{null}}")
    private String gitCommitUserName = "n/a";
}
