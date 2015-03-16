


---


# Prerequisite #

Make sure you read our documentation on how the [dependency management](HowToContribute#Dependency_management.md) works within imogene.

# Install the maven tools for imogene #

Get a local copy of the imogene `tools` repository with this command:
```
git clone https://code.google.com/p/imogene.tools/
```
Import the project to your eclipse.

Follow these steps to install the imogene maven tools eclipse plugin into onto eclipse:
  1. Right click on the `org.imogene.tools.maven` project.
  1. Click **Export...**.
  1. Select **Plug-in Development > Deployable plug-ins and fragments**.
  1. Check **Install into host. Repository:**.
  1. Keep the repository given by default.
  1. Click **Finish**.
Your eclipse should be restarted for the updates apply.


---


# Update the classpath containers libraries for a template #

To updape the libraries that are needed by a template and that will be provided to the generated applications follow the next steps:
  1. Right click on the èorg.imogene.library' project.
  1. Click **Imogene tools > Add/update library from pom.xml**.
  1. Select the `pom.xml` file from the template you want to update the libraries.
  1. Toggle **Update an existing library**.
  1. Select the library corresponding to the template you want to update (`org.imogene.library.sync.server` for the synchronization server, `org.imogene.library.web.requestfactory` for the web and administration applications, etc...).
  1. _Optionally_ you can toggle **Delete unused jars** to remove unseless libraries after the update.
  1. Click **Finish**.

Once finished the libraries are now up to date, you must restart the studio for the changes to be applied.