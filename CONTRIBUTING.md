# Contributing to UhcCore

Thank you for your interest in contributing to this plugin!
If you have any questions, don't hestitate to ask in the `#dev` channel
on [the Discord server][discord-invite].

[discord-invite]: https://discord.gg/fDCaKMX

## Submitting feature requests and bug reports

If you have an idea for a new feature, or found a bug that needs to be
fixed, you can [create an issue][issue-tracker] at the GitLab repository.
Please be as descriptive as possible and include all relevant information,
such as error messages and/or server logs when submitting a bug report.
You are also welcome to discuss the matter on
[the Discord server][discord-invite], in the `#bugs` or `#suggestions` channel.

[issue-tracker]: https://gitlab.com/uhccore/uhccore/-/issues

## Contributing features and bug fixes

Before you start coding, consider discussing the matter on the Discord server,
or submitting a feature request or bug report (see above) describing what
you want to contribute. You may receive tips about where to start or how to
structure your code, which increases the likelihood of your contribution being
accepted. For simple contributions, like fixing a typo or editing a few lines,
you can skip this step.

### Required software

- Git, to clone the repository and to submit your contribution.
- A Java Development Kit (JDK), to run the Gradle build tool.
- A code editor with support for Java projects using the Gradle build tool.
    - You should also make sure that your editor has support for
    [EditorConfig](https://editorconfig.org), perhaps by downloading a plugin.
    This will ensure that your code is formatted consistently with the rest
    of the code in the project.

### Recommended steps

1. [Fork this repository][forking] and clone the fork to your local machine.
2. Open the root directory as a Gradle project in your code editor.
3. Wait for the projects to be imported, and then start coding!
4. When you think you are done, make sure to [test your changes][testing].
5. Commit and push your changes **on a new Git branch** in your forked repository.
6. [Create a merge request][merging] for your contribution.

*Note 1: A GitLab "merge request" is analogous to a GitHub "pull request".*

*Note 2: Please avoid creating a merge request from your main branch.
Instead, create a new branch for the merge request, based on your main branch.*

Another recommendation is to [disable GitLab CI/CD][disable-cicd] in your forked repository.
CI/CD is used in the main UhcCore repository to automate releases and deployments,
but is not needed in your fork. If you leave it enabled, you might see a few errors
stating that the CI/CD pipelines can't run on your merge requests, unless you have
gained access to GitLab's shared runners by verifying with your credit card.

[forking]: https://docs.gitlab.com/ee/user/project/repository/forking_workflow.html
[testing]: #testing-your-changes
[merging]: https://docs.gitlab.com/ee/user/project/repository/forking_workflow.html#merging-upstream
[disable-cicd]: https://docs.gitlab.com/ee/ci/enable_or_disable_ci.html

### Testing your changes

You can execute the `runServer` Gradle task to automatically build the plugin
and run it on a Paper server, for quick and easy testing. This task comes from
the [run-paper][run-paper] Gradle plugin.

[run-paper]: https://github.com/jpenilla/run-task

**Build and run on Windows**

```
gradlew runServer
```

**Build and run on Linux or macOS**

```
./gradlew runServer
```

You can also specify a Minecraft version for the server. Any Minecraft version
for which there exists a build of Paper will work, from 1.8.8 and up.

**Build and run for Minecraft 1.8.8 on Windows**

```
gradlew runServer -PmcVersion=1.8.8
```

**Build and run for Minecraft 1.8.8 on Linux or macOS**

```
./gradlew runServer -PmcVersion=1.8.8
```

The server files will be stored under the `run/<version>/` folder (relative to
the base of this repository), such as `run/1.19.3/` or `run/1.8.8/`. Note that
you will need to edit the `run/<version>/eula.txt` file manually the first time
you run a server, in order to agree to the Minecraft EULA.

If you need to edit server and/or plugin configuration files or install additional
plugins for testing your code, you can do so in the `run/<version>/` folder.
This folder will be reused on subsequent executions of `runServer`.

### Debugging

After starting the server using `runServer`, you can also attach a debugger
via JDWP, which allows you to set breakpoints, inspect variables and more
while the plugin is running. Consult the documentation of your code editor
for more information. Below are documentation links for a few popular editors:

- [IntelliJ IDEA](https://www.jetbrains.com/help/idea/attaching-to-local-process.html#attach-to-local)
- [Eclipse](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.user/tasks/task-remotejava_launch_config.htm)
- [VS Code](https://github.com/microsoft/vscode-java-debug/blob/main/Configuration.md)

You should attach the debugger to the `localhost` address, at port `5005`.
