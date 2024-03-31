///////////////////////////////////////////////////////////////////////
NOTE TO WRITERS:
The following sections should be customized for the technology.
This text was originally from the JAX-RS TCK.  Most references
to JAX-RS have been parameterized to serve as a simple starting
point for customization.  There are still many details that will
need to be changed or removed.  The major sections 4.1, 4.2, and
4.3 should be preserved.  If their titles are changed, the links
at the top of config.adoc will need to be changed as well as well
as toc.adoc.
///////////////////////////////////////////////////////////////////////

[[GBFVU]][[configuring-your-environment-to-run-the-tck-against-the-reference-implementation]]

4.1 Configuring Your Environment to Run the TCK Against the Reference Implementation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

After configuring your environment as described in this section,
continue with the instructions in link:using.html#GBFWO[Chapter 5, "Executing Tests."]


[NOTE]
=======================================================================

In these instructions, variables in angle brackets need to be expanded
for each platform. For example, `<JAVA_HOME>` becomes `$JAVA_HOME` on
Solaris/Linux and `%JAVA_HOME%` on Windows. In addition, the forward
slashes (`/`) used in all of the examples need to be replaced with
backslashes (`\`) for Windows. Finally, be sure to use the appropriate
separator for your operating system when specifying multiple path
entries (`;` on Windows, `:` on UNIX/Linux).

On Windows, you must escape any backslashes with an extra backslash in
path separators used in any of the following properties, or use forward
slashes as a path separator instead.

=======================================================================


1.  Set the following environment variables in your shell environment:
  a.  `JAVA_HOME` to the directory in which Java SE {SEversion} is installed
  b.  `M2_HOME` to the directory in which the Apache Maven build tool is installed.
  c.  `PATH` to include the following directories: `JAVA_HOME/bin`,
  and `M2_HOME/bin`
2.  Set the following System properties:
  a.  Set the `webServerHost` property to the name of the host on which
  Jakarta EE {JakartaEEVersion} CI (for example {TechnologyRI}) is running. +
  The default setting is `localhost`.
  b.  Set the `webServerPort` property to the port number of the host on
  which your Jakarta EE {JakartaEEVersion} CI is running. +
  The default setting is `8080`.
  c.  Set the `webServerHome` property to the installation directory of Jakarta EE
  {JakartaEEVersion} CI.
  d.  `jspservlet.classes` to the path to the Servlet and {TechnologyShortName} API classes
  that are used for building. +
  e.  `jstl.classes` to the path to the Jakarta Standard Tag Library JAR files.
  f.  When the signature tests are run, the `sigTestClasspath` property
  must be set to a list of JAR files and directories which contain the
  Jakarta EE and Java SE classes. +
  Paths must be separated by the appropriate path separator ("`;`" on
  Windows, "`:`" on Solaris/Linux).
  g. `porting.ts.url.class.1` to the class names of your porting class
  implementations. +
  By default, the property settings point to the {TechnologyShortName} TCK CI-specific
  classes.
+
3. 3. Set the below jars to the classpath 
  a. JAR file for the {TechnologyShortName} {TechnologyVersion} API. +
  eg.  `${webServerHost}/modules/jakarta.servlet.jsp-api.jar`.
+
  b. JUnit 5 jars (5.9.3+)
  Maven cordinates :
+
[source,oac_no_warn]
----
       <dependency>
          <groupId>org.junit</groupId>
          <artifactId>junit-bom</artifactId>
          <version>5.9.3</version>
          <type>pom</type>
          <scope>import</scope>
       </dependency>
----
+
 c. sigtest-maven-plugin (2.2) to run the signature tests.
  Maven cordinates :
+
[source,oac_no_warn]
----
      <dependency>
            <groupId>jakarta.tck</groupId>
            <artifactId>sigtest-maven-plugin</artifactId>
            <version>2.2</version>
        </dependency>
----
+
  d.  {TechnologyRI} CI jars +
  For eg, if you are using the {TechnologyRI} CI below jars need to be added to Classpath
+
4.  Provide your own implementation of the porting package interface
provided with the {TechnologyShortName} TCK. +
The porting package interface, `TSURLInterface.java`, obtains URL
strings for web resources in an implementation-specific manner. API
documentation for the `TSURLInterface.java` porting package interface is
available in the {TechnologyShortName} TCK documentation bundle.


[[GCLHU]][[configuring-your-environment-to-repackage-and-run-the-tck-against-the-vendor-implementation]]

4.2 Configuring Your Environment to Repackage and Run the TCK Against the Vendor Implementation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Not needed for the Server Pages TCK.

[[GHGDG]][[publishing-the-test-applications]]

4.3 Publishing the Test Applications
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Not needed for the Server Pages TCK.
