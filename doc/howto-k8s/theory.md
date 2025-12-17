Overview
====

* Control Plane and Worker Nodes
* Declarative model, desired state vs actual state
* API server and CLI apps (kubectl, kubeadm)
* Namespaces, Pods, Containers
* Deployments (scaling, rolling update, rollbacks)
* Networking: Services, DNS, Ingress, kube-proxy
* Container runtime (Docker desktop)
* Autoscaling, self-healing
* Cluster: minikube, GKE, EKS, AKS, Rancher, etc.
* Service discovery/registration (mesh)
* Storage (CSI, persistent volumes, storage classes)
* ConfigMaps and Secrets
* StatefulSets
* API security, RBAC; IAM, auditing
* Threats: spoofing, tampering, replay attacks, privilege escalation, ...

Terminology
====

* **Kubernetes** - A container orchestration system (deploy, manage, scale)
    * Greek "helmsman", person steering a seafaring ship; cybernetic
* k8s - short for Kubernetes, pronounced "kates"
* Cluster -
* Namespace - (short ns)
* Control Plane -
    * **API Server** - Part of the control plane; the HTTP server that stores the state of the cluster.
    * etcd - Part of the control plane; a distributed key-value store.
* Node - Also worker node, doing the actual work of running pods.
* Pod -
* Container - basically a VM but more lightweight (OS kernel reuse)
* Container runtime - Pulls images, starts/stops containers; e.g. Docker (heavy, slow, outdated), CRI-O, containerd
* Image - a package, a template for a container (=a running instances of an image)
* Deployment -
* Service -
* cloud-native application - designed to meet cloud-like demands (autoscaling, self-healing, rolling updates, rollbacks,
  etc.)
* containerized application - packaged as a container image
* Sets - ReplicaSet, StatefulSet
* Controller -
* **Observable** (actual current) and desired state (as configured).
    * The system is **drifting** if the differ, and k8s has to do its sync work.
* Ingress -
* Client side tools...
    * `kubectl` - Local client to send commands to a k8s API. Stores current context (cluster, creds, ns).
    * `kubeadm` - For setting up a k8s cluster.

History
====

* originally from Google
    * originally in-house software: Borg and Omega (running billions of containers)
    * logo (wheel/helm control of a ship) has 7 spokes
        * wanted to call it "Seven of Nine", copryight issues
    * donated (opensourced) in 2014 (to Cloud Native Computing Foundation; www.cncf.io)
* Kubernetes was open sourced

Evolution
----

* beginning run on physical servers (1980-2000)
    * hard requirement e.g. on locally installed Java version
    * running in (web) application servers (heavy enterprise technologies)
* then used virtual machines instead (2000-2020)
* containers are a natural evolution of how we package and run software (2020-...)
    * they are easier manageable, faster, and more lightweight

Basics
=====

* Purpose: abstract underlying infrastructure, make it easier to deploy and manage applications.
* K8S features:
    * dynamically respond to changes: scaling, rolling update (zero-downtime deployments), rollbacks, etc.
    * self-healing: restart failed containers, replace failed nodes, etc.
    * horizontal scaling: scale up/down based on demand
    * declarative: describe desired state, not actual state
    * immutable infrastructure: no downtime, no manual intervention
    * service discovery: DNS, load balancing, service mesh
    * storage: persistent volumes, storage classes
    * ingress networking
    * security: RBAC, service accounts, auditing
    * multi-tenancy: namespaces, quotas, resource limits
    * container runtime: Docker, CRI-O, containerd
    * different "workloads" supported: usually containerised apps, but also VMs and serverless functions
* K8s vs. Docker
    * Docker is a container runtime (low-level), K8s is a container orchestrator (more abstract)
    * complementary technologies, not mutually exclusive (do different things)
    * CRI: Container Runtime Interface, abstracting container runtime away
    * Attention: Docker was _deprecated_ as a runtime (k8s 1.20)
        * containerd instead; stripped down version of Docker (only what k8s needs)
* K8s vs. Docker Swarm: 2016 the orchestrator wars; Kubernetes won ;)
