FROM --platform=linux/amd64 tomcat:10-jdk17-corretto as builder

COPY build/XXXXXX/diamond.jar /opt/wars/
RUN mv "$(ls -1 /opt/wars/*.war 2>/dev/null | head -n 1)" /opt/wars/ROOT.war

FROM --platform=linux/amd64 tomcat:10-jdk17-corretto

COPY --from=builder /opt/wars/ROOT.war ${CATALINA_HOME}/webapps/ROOT.war
