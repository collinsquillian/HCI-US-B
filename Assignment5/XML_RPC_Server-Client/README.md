# XML_RPC_SERVER-CLIENT
University of Bamberg Ubiquitus Assignment #2

# SERVER

1. cd into XML_RPC_SERVER Folder

2. compile JavaServer.java via the following command with classpath referencing xmlrpc-1.2-b1.jar:

        javac -cp lib/xmlrpc-1.2-b1.jar -sourcepath src -d bin/classes src/de/cmlab/ubicomp/JavaServer.java
 
   or simply

        ant compile
        
3. run JavaServer by executing the following commands:

        jar cfm bin/jar/JavaServer.jar myManifest -C bin/classes .
        java -cp lib/xmlrpc-1.2-b1.jar:bin/jar/JavaServer.jar de.cmlab.ubicomp.JavaServer
        
   or simply

        ant run
        
   clean & 2 & 3 using ant:

        ant main
        
   or simply

        ant
        
4. check your console output


# CLIENT

1. cd into XML_RPC_CLIENT Folder

2. compile JavaClient.java via the following command with classpath referencing xmlrpc-1.2-b1.jar:

        javac -cp lib/xmlrpc-1.2-b1.jar -sourcepath src -d bin/classes src/de/cmlab/ubicomp/JavaClient.java
 
   or simply

        ant compile
        
3. run JavaClient by executing the following commands:

        jar cfm bin/jar/JavaClient.jar myManifest -C bin/classes .
        java -cp lib/xmlrpc-1.2-b1.jar:bin/jar/JavaClient.jar de.cmlab.ubicomp.JavaClient
        
   or simply

        ant run
        
   clean & 2 & 3 using ant:

        ant main
        
   or simply

        ant
        
4. check your console output