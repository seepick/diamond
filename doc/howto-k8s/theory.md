Overview
====

* Control Plane and Worker Nodes
* Declarative model, desired state vs actual state
* API server and CLI apps (kubectl)
* Namespaces, Pods, Containers
* Deployments (scaling, rolling update, rollbacks)
* Networking: Services, DNS, Ingress, kube-proxy
* Container runtime (Docker desktop)
* Autoscaling, self-healing
* Cluster: GKE (Google), EKS (Amazon), AKS (MS), Rancher, etc.
    * For local dev: minikube, Docker Desktop, `kubeadm` - For setting up a k8s cluster.
    * ad minikube: bundles server & client parts into 1 image; single node cluster via a virtual machine.
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
* Cluster - a set of nodes working together (controlled by control plane(s))
* Namespace - logical grouping of objects (e.g. all pods in a namespace belong to the same team); short ns
* Control Plane - manages the cluster (in production usually multiple nodes)
    * **API Server** - the main entry point for all k8s API calls.
    * etcd (etsy-dee) - a distributed key-value store (all data stored here)
    * Scheduler - decides which node runs which pod.
* Node - doing the actual work of running pods (also _worker_ node, or minion); container runtime
* Pod - atomar unit of deployment, running on a node; running one or more containers.
* kubelet - agent running on each node.
* Container - basically a VM but more lightweight (OS kernel reuse)
* Container runtime - Pulls images, starts/stops containers; e.g. Docker (heavy, slow, outdated), CRI-O, containerd
* Image - a package, a template for a container (=a running instances of an image)
* Deployment - manages pods and their replicas.
* Controller - manages pods based on desired state ("the brain"), containers
* Service - exposes pods to the outside world.
* cloud-native application - designed to meet cloud-like demands (autoscaling, self-healing, rolling updates, rollbacks,
  etc.)
* containerized application - packaged as a container image
* Sets - groups of objects with a common characteristic (e.g. all pods in a deployment have the same label); ReplicaSet,
  StatefulSet
* **Observable** (actual current) and desired state (as configured).
    * The system is **drifting** if the differ, and k8s has to do its sync work.
* Ingress -
* Client side tools...
    * `kubectl` - Local client to send commands to a k8s API. Stores current context (cluster, creds, ns).

History
====

* originally from Google
    * originally in-house software: Borg and Omega (running billions of containers)
    * logo (wheel/helm control of a ship) has 7 spokes
        * wanted to call it "Seven of Nine", copryight issues
    * donated (opensourced) in 2014 (to Cloud Native Computing Foundation; www.cncf.io)
* Kubernetes was open sourced
* first there was docker (and rkt), and nothing else basically
    * then came k8s and orchestrated it; then rkt wanted in too, thus they developed the Container Runtime Interface
    * then came containerd, the CRI compatible stripped down part of docker
    * (docker is not only a container runtime, but many more things; CLI, build tool, registry, etc.)
    * until k8s 1.24, there was a hack (dockershim) to make (CRI-incompatible) docker work with k8s; not anymore
    * all docker images still work though (followed OCI spec; OCI, Open Container Initiative)

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
* container runtimes (rkt, CRI-O, containerd; previously Docker)
    * `ctr` is for interacting with the container runtime directly
        * `ctr images pull image:name`
        * `ctr run image:name name`
    * `nerdctl` is a CLI wrapper around `ctr` (more useful, higher-level, convenient); very similar to `docker`
        * `nerdctl run --name webserver -p 80:80 -d nginx`
    * `crictl` interact with CRI compatible container (what k8s does)
        * `crictl pull image; crictl images; crictl ps -a`
        * `crictl exec -i -t <container-id> ls`
    * we use those only for debugging purposes (images, containers); NOT to manage a cluster manually!

kubectl
=====

* the `~/.kube/config` file, also visible via `kubectl config view`

```yaml
- context:
    cluster: minikube
    extensions:
      - extension:
          last-update: Wed, 17 Dec 2025 19:22:44 CET
          provider: minikube.sigs.k8s.io
          version: v1.37.0
        name: context_info
    namespace: default
    user: minikube
  name: minikube
```

Basic Concepts
====

* We basically setup our own little (pathetic) cluster with docker compose (wiring it all together manually)
* Pods run usually a single container (image)
    * In rare cases, sharing resources tightly is necessary (storage, network), then a "helper container" is deployed
      next to
      the main app ("sidecar"); think of access to logs, monitoring, etc. 
