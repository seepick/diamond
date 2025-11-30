deployment.yaml
=========
apiVersion: apps/v1
kind: Deployment
metadata:
name: ktor-microservice
spec:
replicas: 3
selector:
matchLabels:
app: ktor-microservice
template:
metadata:
labels:
app: ktor-microservice
spec:
containers:

- name: ktor-microservice
  image: ktor-microservice:latest
  ports:
- containerPort: 8080

service.yaml
=========
apiVersion: v1
kind: Service
metadata:
name: ktor-microservice
spec:
type: LoadBalancer
ports:

- port: 80
  targetPort: 8080
  selector:
  app: ktor-microservice

kubectl apply -f deployment.yaml
kubectl apply -f service.yaml

MWP dockerfile

FROM --platform=linux/amd64 tomcat:10-jdk17-corretto as builder

COPY build/XXXXXX/diamond.jar /opt/wars/
RUN mv "$(ls -1 /opt/wars/*.war 2>/dev/null | head -n 1)" /opt/wars/ROOT.war

FROM --platform=linux/amd64 tomcat:10-jdk17-corretto

COPY --from=builder /opt/wars/ROOT.war ${CATALINA_HOME}/webapps/ROOT.war

