


---


# Prerequisite #

These indications only concern those who are using the imogene studio from the source code.

Only install **imogene** from the sources if you want to inspect the code or adapt it for your own use.
  * If you are not familiar with the Eclipse development environment.
  * If you are not a developer.
  * If you don't know what **imogene** is.
  * If you don't know why you are here.
Then you should not go further.

...


---


# Prepare your development environment #

You stayed ! We now assume that you are a developer familiar with Eclipse.

To prepare your development environment follow the HowToInstall guide but **DO NOT** follow the last step (don't install the **imogene** plugin from the update site). Then you can come back and follow the steps descibed bellow.

## Install EGit ##

If you don't have [EGit](http://www.eclipse.org/egit) installed in your Eclipse follow the instructions to install it.

## Install Maven ##

The libraries and the templates of the **imogene** plugin are using the project mangament tool [Apache Maven](http://maven.apache.org). This need to be installed on your computer in order to build these projetcs.

If you are using a Linux distribution that provides maven within its dependency management system, we recommand **NOT** to use it and install **Maven** manually on your system. The version provided is usually outdated.

We recommend **NOT** to use the **m2eclipse** plugin as it messes up the `pom.xml` configurations files.

## Configure Maven ##

Once **Maven** has been installed and launched, it must create directory called `.m2` in your home directory. Open the `settings.xml` file inside and add the following lines to enable the **imogene** maven repositories to be able to access the libraries provided by us.

```
<repositories>
    <repository>
        <id>imogene</id>
        <url>http://kamkam.medes.fr:9081/nexus/content/groups/public</url>
    </repository>
</repositories>
```


---


# Getting the source #

Clone the source code using the distributed revsion control and source code management system [git](http://git-scm.com/).

Get a local copy of the imogene repository with this command:
```
git clone https://code.google.com/p/imogene/
```

Get a copy of the imogene tools repository with this command:
```
git clone https://code.google.com/p/imogene.tools/
```


---


# Code Overview #

## Sightseeing ##

The packages called `org.imogene.<name>.contrib` contain the source code of the plugin extension for the given generator.

The packages called `org.imogene.<name>.oaw.generator` contain the source code of the generator for the given application.

The packages called `org.imogene.<name>.template` contains the base source code of the future generated application (the administration application doesn't have one because it is using the one from the web application). This code is common for all target generated applications.

The `dev-libs` remote folder contains source code of some libraries used by the **imogene** applications.
```
org.imogene.lib.common 
org.imogene.lib.encryption
org.imogene.lib.push 
org.imogene.lib.sync.client 
org.imogene.lib.sync.common 
org.imogene.lib.sync.server
```

The `gen-common` remote folder contains methods for generation that are common with at least 2 generators.
```
org.imogene.oaw.generator.common
```

The `gen-admin` remote folder contains the generator for the Administration application.
```
org.imogene.admin.contrib 
org.imogene.admin.oaw.generator 
```

The `gen-droid` remote folder contains the generator for the Android application.
```
org.imogene.android.contrib 
org.imogene.android.oaw.generator 
org.imogene.android.template 
```

The `gen-init` remote folder contains the generator for the Initialiser application.
```
org.imogene.initializer.contrib 
org.imogene.initializer.oaw.generator 
org.imogene.initializer.template 
```

The `gen-model` remote folder contains plugin source code to initialise a new Imogene project in a launched studio.
```
org.imogene.model.contrib 
org.imogene.model.template 
```

The `gen-dao` remote folder contains plugin source code to generate the Data Access Objects needed by almost all generated applications (web, sync, client, notifier)
```
org.imogene.dao.contrib
org.imogene.dao.oaw.generator
org.imogene.dao.template
```

The `gen-notif` remote folder contains the generator for the Notification application.
```
org.imogene.notifier.contrib 
org.imogene.notifier.oaw.generator 
org.imogene.notifier.template 
```

The `gen-client` remote folder contains the generator for the Desktop RCP application.
```
org.imogene.client.contrib 
org.imogene.client.oaw.generator 
org.imogene.client.template 
```

The `gen-sync` remote folder contains the generator for the Synchronisation server application.
```
org.imogene.sync.contrib 
org.imogene.sync.oaw.generator 
org.imogene.sync.template 
```

The `gen-web` remote folder contains the generator for the Web application.
```
org.imogene.web.contrib 
org.imogene.web.oaw.generator 
org.imogene.web.template 
```

The `gen-ws` remote folder contains the generator for the Restful Web services application.
```
org.imogene.webservice.contrib 
org.imogene.webservice.oaw.generator 
org.imogene.webservice.template 
```

The `library` remote folder contains libraries shared by the templates of the generators.
```
org.imogene.library 
org.imogene.library.contrib 
```

The `model` remote folder contains the model definition source code. Defines the elements of the model, the specific source code for editing the model and the validation rules.
```
org.imogene.model.core 
org.imogene.model.core.editor 
org.imogene.model.validation 
```

The `studio` remote folder contains the **imogene** plugin source code that is launched in a new eclipse.
```
org.imogene.studio.contrib
```

## Projects architecture ##

The generators are divided in 3 differents parts:
  1. a static part called the **templates**,
  1. a variable part called the **generators**,
  1. an Eclipse plugin **contribution** to the imogene studio graphical interface.
These projects can be identified respectively by their names:
  1. the templates are called **org.imogene.{target}.template**,
  1. the generators are called **org.imogene.{target}.oaw.generator**,
  1. and the contributions are called **org.imogene.{target}.contrib**
where the path `{target}` may have one of the following possible value : `android`, `web`, `sync`, `dao`, `initializer` and `notifier`.

## Templates ##

The templates are basics projects in which the modelized applications are generated. These templates are compressed within contribution plugins and then uncompressed before the generation, the generated files are placed into the newly uncompressed projects. They contain basic code shared by all the generated application based upon a specific target (Android, Web, Synchronization...). This code is generic and does not depend on the modelization.

To compress the tempates into the contribution plugin follow those steps:
  1. Under contribution plugin, find `template-site/build.xml` file
  1. If you are compressing the template for the first time:
    1. Right click on the file `build.xml`.
    1. Select **Run as > Ant Build...** (be aware of the `...`).
    1. Under the JRE tabulation.
    1. Select **Run in the same JRE as the workspace**.
    1. Click **Apply**.
    1. Click **Run**.
  1. otherwise:
    1. Right click on the file `build.xml`.
    1. Select **Run as > Ant Build**.
The `org.imogene.{target}.template` project must have been compressed to the `org.imogene.{target}.contrib/template-site/templates.zip` file.

Some files are exluded during the extraction of templates, check the `org.imogene.{target}.contrib/template-site/templates.xml` what are the inclusion/exclusion rules for a given template.

The templates are eclipse projects, thus they contain eclipse specific configuration files (`.project`, `.settings/*`, `.classpath`, etc...). These configurations are correct for a given project in a given workspace, yet when extracted the newly created project can't use the same configuration files as the templates. These configurations files need to be modified to make them fit the new eclipse project settings. Accordingly, the template configuration files are replaced during the genreation process by generated specific ones. These new configuration files are located in the `org.imogene.{target}.template/eclipseProjectFiles` folder. These files are using **%propertyName%** which are replaced during the generation process by generation specific names.

## Generators ##

The generators allow the imogene studio to generate specific source code based upon the modelization elements. Genrators are built using the Eclipse plugin  **openArchitectureWare**. The generators are written using **Xpand** and **Xtend** languages. For more information about these technologies please refer to the [documentation](http://www.openarchitectureware.org/).

Generators can be modified without having to restart the imogene studio each time, changes are applied at the next generation unless you modify **Xtend** files (ie: .ext files) or a java extension class mapped by one of these Xtend files (ex: org.imogene.android.oaw.generator.DatesGenHelper.class). For more details please refer to the [Xtend documentation page](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#Xtend_language).


---


## Dependency management ##

Most of the templates are using maven and the dependency management feature. In order to use a new library with a template, the dependency must be added in its `pom.xml` file. To configure `pom.xml` files please refer to the [Maven project documentation](http://maven.apache.org/).

Although the imogene studio templates source code is built using maven, the generated projects are **NOT** maven projects (they can be _mavenised_ but this is left to user responsibility).

As the generated projects are not using maven the necessary dependencies are exposed to generated projects by providing classpath container for each generated project type (synchronizer, web, admin, etc...). The libraries needed by the templates are gathered in the project `org.imogene.library` under the folder `libs/jars'. The classpath containers libraries are described in the `.xml` files contained in the `libs` folder. These configuration files are read and the libraries are exposed through the `org.imogene.library.contrib`.

The libraries and configuration files are not handled manually, we are using an homemade plugin to copy the libraries to the `libs/jar` folder and update the `.xml` files. See our [guide](HowToUseImogeneMavenPlugin.md) to install and use the maven tools for imogene.


---


# Usage of Maven #

All the templates and the libraries of **imogene** are built using maven. In order to build the eclipse project you must run the following command from the imogene directory:
```
mvn eclipse:eclipse -Plibs,templates
```
This will create all the configuration files needed by Eclipse to recognize the project as Eclipse projects (.project, .classpath, etc...).


---


# Import project into your Ecipse workspace #

To add your cloned repository to **Egit** follow these steps:
  1. Go to the **EGit** perspective
  1. Click on the button **Add an existing local Git Repository to this view**
  1. Fill the parent directory containing your imogene cloned repository
  1. Select the repository that appeared in the bottom list

To import the project in your workspace follow these steps:
  1. Go to the the **Egit** repository view
  1. Right click on the imogene repository
  1. Select **Import Projects...**
  1. Let the option **import existing projects**
  1. Click **Next**
  1. Select all the projects you wan't to import in your workspace. **Unselect all the projets called %projectName% and %javaProjectName%** (The plugin is browsing all the directories and subdirectories and encountered the `eclipseProjectFiles` directory within each templates which contain a `.project` file used for generation).


---


# Run Imogene From Source Code #

Follow these steps to run **imogene** from the source code.
  1. From within Eclipse, select **Run > Run Configurations...**.
  1. Double-click **Eclipse Application**.
  1. Enter **Imogene Studio** for the _Name_.
  1. Under the **Plug-ins** tabulation make sure the **Launch with** option is configured with **all workspace and enabled target plug-ins**.
  1. Then click **Run**.

Done ! **imogene** is now running from the source code.


---


# Tips #

  * Each time you modify a `java` file outside the templates you have to rerun the **Imogene Studio**. This is not necessary if you modified an `Xpand` file or any files from the templates.

  * The templates are compressed into `zip` files and placed into the contribution `(org.imogene.<generator>.contrib)` plug-ins. Follow these steps to update a template once you have made a modification:
    1. Right-click on the `build.xml` file in the corresponding `contrib` project of the template you just modified.
    1. Select **Run As > Ant build...**.
    1. Under the **JRE** tabulation, select the checkbox near to **Run in the same JRE as the workspace**.<br />Note: These steps have to be made for each contribution plug-ins at least once. Then you can just select **Run As > Ant build** this will run over the configuration page of step 3.


---


# Is there an IRC channel I can idle on? #

Yes. You can get your questions answered real-time and hang out with us in #imogene on [Freenode](http://freenode.net/).


---


# What are the project mailing lists? #

The project forum is available [here](https://groups.google.com/forum/#!forum/imogene).