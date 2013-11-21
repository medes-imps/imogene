------- Dependencies to add manually to make p2 works fine ----------------------
org.eclipse.equinox.ds
org.eclipse.equinox.p2.artifact.repository
org.eclipse.equinox.p2.touchpoint.eclipse
org.eclipse.equinox.p2.touchpoint.natives
org.eclipse.equinox.p2.transport.ecf

------- Start order for ---------------------------------------------------------
   <configurations>
      <plugin id="org.eclipse.core.runtime" autoStart="false" startLevel="3" />
      <plugin id="org.eclipse.equinox.ds" autoStart="true" startLevel="2" />
      <plugin id="org.imogene.rcp.derby" autoStart="true" startLevel="4" />
      <plugin id="org.imogene.rcp.initializer" autoStart="true" startLevel="3" />
      <plugin id="org.imogene.rcp.jetty" autoStart="true" startLevel="5" />
      <plugin id="org.imogene.sync.client.<client>" autoStart="true" startLevel="5" />
   </configurations>

------- Jetty enable jsp --------------------------------------------------------
-Dorg.apache.jasper.compiler.disablejsr199=true

------- Jetty properties --------------------------------------------------------
-Djetty.port=8888

------- Derby configuration -----------------------------------------------------
-Dderby.drda.portNumber=1493
-Dderby.drda.host=127.0.0.1
-Dderby.drda.logConnections=true
-Dderby.stream.error.logSeverityLevel=0

------- Workspace ---------------------------------------------------------------
-Dosgi.instance.area=@user.home/.imogene

------- VM linux argument -------------------------------------------------------
-Dosgi.instance.area=/opt/imogene.d/data

------- Imogene properties ------------------------------------------------------
-Dimogene.debug=false
   
------- Remote Debugging options ------------------------------------------------
-Xdebug
-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8998

------- Bad network parameters --------------------------------------------------
-Dsun.net.client.defaultConnectTimeout=60000
-Dsun.net.client.defaultReadTimeout=60000
