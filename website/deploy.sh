( cp dist/java/tavolaWebsite.jar /usr/share/tomcat5.5/webapps/tavolaWebsite && cd /usr/share/tomcat5.5/webapps/tavolaWebsite && jar xf tavolaWebsite.jar )
( cp -r dist/html/* /usr/local/nginx/html/tavolaWebsite/ ) 
