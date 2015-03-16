


---


# Prerequisite #

Before following these installation instructions, check that the Java runtime environment is installed on the machine.
Check your configuration by running `java -version` in command line terminal or check it online [here](http://www.java.com/fr/download/installed.jsp).

If the Java runtime environment is not installed on the machine download the last **Java SE JDK** from http://www.oracle.com/technetwork/java/javase/downloads/index.html.


---


# Install the Eclipse IDE #

Download and unpack the [Eclipse IDE for Java EE Developers](http://www.eclipse.org/downloads/).

After the installation, configure the Eclipse launching parameters. In the **Eclipse** root directory, edit the file `eclipse.ini`, add the `-clean` parameter and save the file.

```
-startup
plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar
-clean
--launcher.library
...
```


---


# Installing Tomcat #

Install Tomcat version 7. It can be downloaded from the [Apache Tomcat website](http://tomcat.apache.org/).

_Note: For Windows, do not select the option to start the service at start-up._

Follow this steps to install the servlet container **Tomcat** and configure it for **Eclipse**.

  1. Start Eclipse, then select **Window > Preferences**
  1. Under **Server > Runtime Environments**
  1. On the right panel, click the **Add** button
  1. Under **Apache** select the **Apache Tomcat v7.0** and click **Next**
  1. Click **Browse** and select the folder where **Tomcat** is installed on your computer
  1. Click **Finish** and **OK** to close the **Preferences** view

Follow this steps to add **Tomcat** as a server in the **Eclipse Workbench**.

  1. Start Eclipse, then select **File > New > Other...**
  1. Select **Server > Server** and click **Next**
  1. Select **Apache > Tomcat v7.0 Server** and click "Next**1. Click**Finish**to create the server**


---


# Install the GWT Plugins for Eclipse #

The Web and Administration application are developed using the GWT development toolkit. To install and configure GWT within your eclipse follow the steps described [here](http://www.gwtproject.org/usingeclipse.html).


---


# Install the Android Plugins for Eclipse and the Android SDK #

To be able to generate and deploy android applications generated using **imogene** you wil need to install the Android ADT plugin  whithin your eclipse following the steps described [here](http://developer.android.com/sdk/installing/installing-adt.html).


---


# Third party libraries #

The **imogene** Android applications need some external Android project which are used as a libraries. A library project is a standard Android project. These libraries are located here `https://code.google.com/p/imogene.android`.

Follow these steps to import the libraries in your Eclipse workbench:

  1. Clone the repository
`https://code.google.com/p/imogene.android`.
  1. Import the android projects that it contains in your eclipse.
    * medes-android\_lib
    * medes-android-maps\_lib

These libraries, and therefore the Android applications generated using **imogene** use the **ActionBarSherlock** extension which is an other Android library that need to be imported in your eclipse.

Follow the steps described [here](http://actionbarsherlock.com/) to use the **ActionBarSherlock** extension.

When you generate an Android application using **imogene** this application is supposed to use these libraries. The Android plugin often messed up with the library feature, so when a project is generated you will certainly need to reference the libraries in the newly generated project.

Follow these steps to declare the projects above as libraries in your generated Android application:

  1. In the **Package Explorer**, right-click the dependent project and select **Properties**.
  1. In the Properties window, select the **Android** properties group at left and locate the **Library** properties at right.
  1. If a library appears in the list:
    * Select it and click on **Remove** on the right side.
    * Click **Apply**.
    * The project should now be in error.
  1. Click **Add** to open the **Project Selection** dialog.
  1. From the list of available library projects, select the **actionbarsherlock**, **medes-android\_lib** and **medes-android-maps\_lib** projects and click **OK**.
  1. Click **Apply**.
  1. Click **OK** to close the Properties window.


---


# Install Other Dependency Plugins for Eclipse #

The **imogene** plugin needs some other plugins to work properly.

Follow these steps to download and install these plugins into your Eclipse development environment:

  1. Start Eclipse, select **Help > Install New software...**.
  1. In the **Work with** list, select **Indigo** (or the Eclipse version name that you are using if different)
  1. Under **Modelling** select the checkboxes next to **EMF - Eclipse Modeling Framework SDK**, **EMF Validation Framework SDK**, **MWE SDK** and **Xpand SDK**.
  1. Click **Next**
  1. In the next window, you'll see a list of the tools to be downloaded. Click **Next**.
  1. Read and accept the license agreements, then click **Finish**.


---


# Install the Imogene Plugin for Eclipse #

The last step is to install the **imogene** plugin.

Follow these steps to download and install the **imogene** plugin:

  1. Start Eclipse, then select **Help > Install New Software...**.
  1. Click **Add**, in the top-right corner.
  1. In the Add Repository dialog that appears, enter "Imogene" for the _Name_ and the following URL for the _Location_:<br />`http://update-site.i-mogene.org/`
  1. Click **OK**.
  1. In the Available Software dialog, unselect the checkbox next to **Group items by category**.
  1. Click **Select All**, then **Next**
  1. In the next window, you'll see a list of the tools to be downloaded. Click **Next**.
  1. Read and accept the license agreements, then click **Finish**.<br />Note: If you get a security warning saying that the authenticity or validity of the software can't be established, click **OK**.
  1. When the installation completes, restart Eclipse.

Done! If you haven't encountered any problems, then the installation is complete. Find out HowToUse the studio.