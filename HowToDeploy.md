


---


# Create the imogene central database #

The creation of the imogene central database depends on the chosen type of database. As previously mentioned 2 databases have been successfully tested for imogene, MySQL and PostgreSQL. Other databases should be supported but have not been tested until now.

Using the chosen database administration tool, create the database that will be queried by the imogene applications

This phase aims to create an empty database only. The imogene deployment process will automatically create the imogene application tables.


---


# Initialize the central database #

Before being able to login to a imogene application, the central database has to be initialized.

The first deployment of a Web or a Synchronization application will create most of the database tables. This deployment has to be done **before** being able to initialize the database.

Running the **imogene Initializer** application will enable to initialize the database.
  1. Right click on the **imogene Initializer** project.
  1. Select **Run As > Java Application**.
  1. Select the **ImogeneInit** java application.
  1. Click **OK**.
The application is launched and inserts default user and profiles instances in the database.

The application default user login is **admin**. Its password is **epipassword** (or the one specified in the **Imogene Properties Page** of the model). Now that the default user has been inserted in the database, the administrator can login to the applications.


---


# Deploy the Synchronisation application #

In order to deploy the imogene Synchronisation application:
  1. Right click on the synchronization application project
  1. To run the application from Eclipse (for developpment process only):
    1. Select **Run As > Run on Server**.
  1. To export the application (recommanded for production):
    1. Select **Export**.
    1. Select **Web > War File**.
The exported War file can then be deployed to a Tomcat server.

During this process, if it is the first time that a imogene application is connecting itself to the database, the deployment process will create the tables of the imogene central database.

If the model has been updated and that a new application is generated and deployed, the deployment process will update the database. This process will add tables and fields depending on the model modifications. But no field nor table, nor constraint will be deleted. For that reason, when relations are involved (put as constraints in the database), it is better to delete the tables so that the deployment process re-creates them.

Before being able to login to the imogene application, the database has to be initialized with default user and profiles.


---


# Deploy the Web (or Admin) application #

The Web and Administration applications have to be compiled before being deployed by following the steps below.
  1. Right click on the Web application project.
  1. Select **Google > GWT Compile**.
  1. Click **Finish**
Once this process is finished, the application can be deployed within Eclipse or in another Tomcat server. Please refer to the [deployment of a synchronization application](HowToDeploy#Deploy_the_Synchronisation_application.md).


---


# Deploy the Android application #

## Installing from Eclipse ##

Eclipse enables to directly deploy the Android application on an Android terminal. To proceed, connect the Android terminal to the computer via its USB cable.

On computers that run the Windows operating system, the terminal driver has to be installed on the computer beforehand.

To install the android application on a device from Eclipse follow those steps:
  1. Right click on the generated Android project.
  1. Select **Run As > Android Application**.
The application should now be installed to your device and must be running.

## Publish the application ##

To publish an Android application please refer to the [documentation](http://developer.android.com/tools/publishing/publishing_overview.html) on how to export and publish Android applications From eclipse.


---


# Deploy the desktop application #

## The synchronization client ##

The desktop application is a container allowing us to embed a web application and providing a set of tools to provide a web server, a database, etc... The container is providing:
  * a database powered by [Apache Derby](http://db.apache.org/derby/),
  * a servlet engine and http server powered by the [Jetty Server](http://www.eclipse.org/jetty/),
  * a synchronization client which is able to send and receive data to the synchronization application,
  * an automatic update system using the p2 provisioning tools.
The illustration here below presents the global architecture of the desktop application.

![http://wiki.imogene.googlecode.com/git/images/desktop-architecture.png](http://wiki.imogene.googlecode.com/git/images/desktop-architecture.png)

## Generate an embeddable web application ##

To generate an embeddable web application follow the following steps:
  1. Right click on your model project.
  1. Select **Imogene -> Web application generation**.
  1. Enter the name of your embedded web application project.
  1. Click **Next**.
  1. Select the browsers your aplication will be able to run on.
  1. Select **Embedded** under **Embedding web application**.
  1. Click **Finish**.
This will generate a web application designed to fit in the container described above.

The main differences are:
  * the application is configured to use the Apache derby database rather than the default postgresql database,
  * the file system that the application can use is defined by the workspace of the container,
  * the timestamps are obtained with the offset between the local time of the machine and the real time obtained from a NTP server periodically.

## Prepare the web application to be embedded ##

Once generated the application is almost ready to use the only thing is to replace the [JDBC driver](http://en.wikipedia.org/wiki/JDBC_driver) which is by default the one that fit a [PostgreSQL database](http://www.postgresql.org/) by the one for the Apache derby database:
  1. Go to the folder `src/main/webapp/WEB-INF/lib` from web embedded application project.
  1. Delete the **postgresql-x.y.z.jdbcn.jar** file.
  1. Copy the **derbyclient-x.y.z.n.jar** file from the synchronization client application `lib/` folder to the `src/main/webapp/WEB-INF/lib`.
  1. Then you can compile your GWT application, if not already done.
  1. Then you are ready to export the application to a `war` file. Please refer to the [web application deployment guide](HowToDeploy#Deploy_the_Web_(or_Admin)_application.md).

## Embed the web application ##

The exported web application must then be embedded within the generated synchronization client and the Jetty server as be aware about this new application.
  1. Create a folder **WebContent** at the root of the **synchronization client project**.
  1. Place the `war` file exported previously into this folder.
  1. Open the **plugin.xml**.
  1. If a dialog box appears saying _Unsupported Content Type_, nothing to worry about, the **Android Common XML Editor** is very intrusive and try to open every `.xml` even when they are not for him. If this is your case:
    1. Right click on the **plugin.xml** file.
    1. Select **Open With > Plug-in Manifest Editor**.
    1. Once this procedure has been done once for a given `.xml` you can avoid this procedure.
  1. Go to the **Extensions** tab.
  1. Click **Add...** to add a new extension.
  1. Select the **org.imogene.client.core.jetty.webapps** extension.
  1. Click **Finish** to add the extension.
  1. Select the **webapp** element under the **org.imogene.client.core.jetty.webapps** extension newly created.
  1. If the **webapp** element do not appear you can add one manually:
    1. Right click on the **org.imogene.client.core.jetty.webapps** created.
    1. Select **New > webapp**.
  1. On the right panel indicates the **context** you want to give to your application (the context will appear in the local address http://localhost:8888/{context}/).
  1. Select the **path** to the location of the `war` file you copied before.
  1. Make sure the application is **enabled** by selecting **false** on the disable element otherwise your application won't be started.

## Export the desktop application ##

The desktop application is now ready to be exported.
  1. Open the **container.product** file in the synchronization client project.
  1. Go to the **Dependencies** tab.
  1. Click **Add Required Plug-ins**.
  1. Go to the **Overview** tab.
  1. Click on the **Eclipse Product export wizard** link.
  1. Indicates your destination repository and the application name.
    1. Make sure that the **Generate metadata repository** option is selected.
  1. Click **Finish**.
Once exported you can run your application from the destination repository specified.

## Self-Update an RCP application ##

The generated RCP application is using the p2 self-updating support. When exporting with the option **Generate metadata repository** selected, in the destination folder next to the exported application a folder called `repository` is created. This folder contains everything necessary to update an application. This folder has to be available on a server for the application to be updated.

The update server URL is defined in the `p2.inf` file and must point to the location of a valid exported repository.

Before publishing an update the version number of the new version of the application in the `container.product` has to be updated. Once updated, the application can be exorted and the repository folder deploy at the update URL. The deployed application will now self-update by checking for updates at launch time or manually if a connection to the server is available.

For more information about the self-update support please check the following links:

[Equinox/p2/Adding Self-Update to an RCP Application](https://wiki.eclipse.org/Equinox/p2/Adding_Self-Update_to_an_RCP_Application)

[Eclipse p2 updates for RCP applications](http://www.vogella.com/tutorials/EclipseP2Update/article.html)
