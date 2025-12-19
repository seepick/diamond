k8s handson demo

see: https://www.youtube.com/watch?v=r2zuL9MW6wc


Upfront Preparation
=====

* containerized helloworld ktor
    * single page, displaying host, env-vars, ...
    * health endpoint
    * graceful shutdown endpoint
    * fail endpoint (makes the whole application crash, to demo self-healing)
* multiple terminals in one, like a cool hack0r ;)

Roadmap
============

Basic K8s
----

* containerization
    * simple webapp, dockerfile, (podman?), create image (standalone and lightweight)
* start minikube cluster
    * run `❯ minikube status`
    * reuse minikube tutorial: https://kubernetes.io/docs/tutorials/hello-minikube/
* create deployment (explain: node, container, pod)
    * login into container, curl localhost
    * expose service; curl pod-host
* scale up, increase replica
    * monitor status
* call /fail endpoint
    * show self-healing
* deploy new version
    * zero-downtime via rolling update
    * rollback possible
* show k9s: navigation, logs, port forwarding
* show lens UI: https://lenshq.io
* destroy cluster

OC
----
... TODO ...
