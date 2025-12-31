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

Toolsupport
======================

For VSC:

* install "yaml" plugin
* edit extension settings
* go to schema edit in settings.json
* add entry: `"kubernetes" = "*.k8s.yaml"`
* restart VSC

Terminology
====

* **Kubernetes** - A container orchestration system (deploy, manage, scale)
    * Greek "helmsman", person steering a seafaring ship; cybernetic
* **k8s** - short for Kubernetes, pronounced "kates"
* **Cluster** - a set of nodes working together (controlled by control plane(s))
* **Namespace** - logical grouping of objects (e.g. all pods in a namespace belong to the same team); short "ns"
* **Control Plane** - manages the cluster, a.k.a. "master" (in production usually multiple nodes)
    * **API Server** - the main entry point for all k8s API calls.
    * **etcd** (etsy-dee) - a distributed key-value store (all data stored here)
    * **Scheduler** - decides which node runs which pod.
* **Node** - doing the actual work of running pods (also _worker_ node, or minion); container runtime
* **Pod** - atomar unit of deployment, running on a node; running one or more containers.
* **kubelet** - agent running on each node.
* **Container** - basically a VM but more lightweight (OS kernel reuse)
* **Container runtime** - Pulls images, starts/stops containers; e.g. Docker (heavy, slow, outdated), CRI-O, containerd
* **Image** - a package, a template for a container (=a running instances of an image)
* **Deployment** - manages pods and their replicas.
* **Controller** - manages pods based on desired state ("the brain"), containers
* **Service** - exposes pods to the outside world; short "svc"
* **cloud-native application** - designed to meet cloud-like demands (autoscaling, self-healing, rolling updates,
  rollbacks,
  etc.)
* **containerized application** - packaged as a container image
* **Sets** - groups of objects with a common characteristic (e.g. all pods in a deployment have the same label)
* **ReplicaSet** - manages pods based on a desired state (number of pods running; load balancing, auto-scaling)
    * **Replication Controller** - deprecated (use ReplicateSet instead; with rs selector field is mandatory)
* **StatefulSet** - ?
* **Observable** (actual current) and desired state (as configured).
    * The system is **drifting** if the differ, and k8s has to do its sync work.
* **Ingress** -
* Client side tools...
    * `kubectl` - Local client to send commands to a k8s API. Stores current context (cluster, creds, ns).
    * `kubeadm` - Cluster setup tool.

History
==================================================

* originally from Google
    * originally in-house software: Borg and Omega (running billions of containers)
    * logo (wheel/helm control of a ship) has 7 spokes
        * wanted to call it "Seven of Nine", copryight issues
* Kubernetes was open sourced in 2014
    * donated to CNCF (Cloud Native Computing Foundation; www.cncf.io)
* first there was docker (and rkt), and nothing else basically
    * then came k8s and orchestrated it; then rkt wanted in too, thus they developed the Container Runtime Interface
    * then came containerd, the CRI compatible stripped down part of docker
    * (docker is not only a container runtime, but many more things; CLI, build tool, registry, etc.)
    * until k8s 1.24, there was a hack (dockershim) to make (CRI-incompatible) docker work with k8s; not anymore
    * all docker images still work though (followed OCI image-spec; OCI (Open Container Initiative))

Evolution
--------------------------------------------------------

* beginning run on physical servers (1980-2000)
    * hard requirement e.g. on locally installed Java version
    * running in (web) application servers (heavy enterprise technologies)
* then used virtual machines instead (2000-2020)
* containers are a natural evolution of how we package and run software (2020-...)
    * they are easier manageable, faster, and more lightweight

Basics
==================================================

* Control Plane and Worker Node, like Server-Client/Master-Slave, due to Kube-API-server and kubelet agent
* We basically setup our own little (pathetic) cluster with docker compose (wiring it all together manually)
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
    * container runtime: Docker, CRI-O, containerd, rkt (rocket), ...
        * different "workloads" supported: usually containerised apps, but also VMs and serverless functions
* K8s vs. Docker
    * Docker is a container runtime (low-level), K8s is a container orchestrator (more abstract)
    * complementary technologies, not mutually exclusive (do different things)
    * CRI: Container Runtime Interface, abstracting container runtime away
    * Attention: Docker was _deprecated_ as a runtime (k8s 1.20)
        * containerd instead; stripped down version of Docker ("docker light", only what k8s needs); Docker is a whole
          platform
* K8s vs. Docker Swarm: 2016 the orchestrator wars; Kubernetes won ;)
* container runtimes (rkt, CRI-O, containerd; previously Docker) CLI tools
    * `ctr` is for interacting with the container runtime directly (very low level; debugging; specifically for
      containerD)
        * `ctr images pull image:name`
        * `ctr run image:name name`
    * `nerdctl` is a CLI wrapper around `ctr` (more useful, general purpose, higher-level, convenient); very similar to
      `docker`; specifically for containerD
        * `nerdctl run --name webserver -p 80:80 -d nginx`
    * `crictl` interact with CRI compatible container (all of them, not only containerD; mainly debugging purpose)
        * `crictl pull image; crictl images; crictl ps -a`
        * `crictl exec -i -t <container-id> ls`
    * we use those only for debugging purposes (images, containers); NOT to manage a cluster manually!
        * ideally one has never to use them at all

Labels, Selectors, Annotations
--------------------------------------------------------

* to filter/select objects based on different (custom) tagged information
    * e.g. `❯ k get pods --selector key=value` or generally: `❯ k get po --show-labels`
    * used for deployment -> pods, etc.
* annotations are even more "vague" (contact details, build tool version/timestamp, ...)

Replication
--------------------------------------------------------

* Use a **ReplicaSet** to ensure that a certain number of pods are always running (and load balanced).
    * Do not use the deprecated ReplicationController anymore.
* Declare a manifest file, and use a selector and a (container) template to define the desired state.
* Monitors pods based on selectors to filter which to monitor (e.g. via labels)

Deployment
--------------------------------------------------------

* Manages internally a ReplicaSet; adds additional functionality.
* A new rollout (image with update version) will create a new revision (roll-backable).
    * Check what's happened: `k rollout history deployment/my-deployment`
    * Live monitor progress: `k rollout status deployment/my-deployment`
* The old will be preserved, to be able to roll-back (simply scaling down/up pods).
* Deployment Strategies:
    * **RollingUpdate**: Tear down/start up one-by-one, zero downtime; the default.
    * **Recreate**: Tear all down, short application unavailability, start new ones up.

Namespaces
--------------------------------------------------------

* or short "ns"; group objects together; not accidentally configuring wrong one
    * kind-a "virtual cluster", e.g. for environments: dev-test-acc-prod
    * k8s has its own (kube-system for internal, and kube-public for shared)
* features: custom policies (RBAC; who can do what), ResourceQuotas (limit resources), constraints, ...
* to connect outside of the current namespace: `<SERVICE>.<NAMESPACE>.svc.cluster.local`
    * k8s does some internal DNS management for you
* kubectl commands:
    * get objects for all namespaces: `k get pods -A`
    * get objects for a specific ns: `k get pods --namespace=kube-system`

Configuration
==================================================

* Docker's ENTRYPOINT/CMD (mapped to command and args for pod templates)
* environment variables
* ConfigMap and Secret (passwords, certificats)
    * possible to reference whole set, single values, or as a volume
* Secrets seem at first not much safer than ConfigMaps, but under the hood they provide some more safety
    * they are only base64 encoded (implicitly via imperative style, explicitly in declarative style)
    * Definitely not enough though, add some 3rd party functionality for proper secret handling
* Need "Encryption at Rest" and/or RBAC (role-based access control) for real secure secrets
    * Encryption at Rest: https://kubernetes.io/docs/tasks/administer-cluster/encrypt-data/
        * all k8s APIs "support at-rest encryption"; "configure encryption of API data at rest"
        * basically like "linux kernel recompile": enable flag, add some manifest, restart. new secrets will be
          encrypted.

Security
==================================================

* linux (host/container)  namespaces; process isolation, ... (shared OS kernel, opposed to with VMs)
* container user is by default root, but with limited capabilities; or change user to run with
    * capabilities can only be set on containers (not pods)
* regarding Secrets: see "Configuration" section
* in order to allow applications talk like we do via kubect, a service account is require:
  `kubectl create serviceaccount my-user`
    * and: `kubectl create token my-user` (before k8s version 1.24, this was done implicitly with account creation)
    * the token (auth-bearer) is stored in the associated secret object
        * decode the token via https://www.jwt.io or:
          `jq -R 'split(".) | select(length > 0) | .[0],.[1] | @base64d | fromjson' <<< eyJhb...`
    * preferably mount volume for the secret: use `serviceAccountName` on a pod's spec to do so
    * CAVE: no expiry date set for the token! (need to do some more logic in yaml files)

Multi-Container Pods
==================================================

* Pods run usually a single container (image), a 1-to-1 relationship
    * In rare cases, sharing resources tightly is necessary (storage/volume, network/services), thus 2+ containers
    * a "helper container" is deployed next to the main app ("sidecar"); think of webservers, access to logs,
      monitoring, etc.
    * Easier communication / resource sharing; easier setup (share same lifecycle)
* Patterns:
    * Co-located: simply 2 containers in a pod (no synchronization between them)
    * (Regular) Init Containers: initialization step upfront, before other container, then stops
        * e.g. wait for DB or other API to run; using busybox image and a simple shell-script (with wait loop)
        * instead of `spec.containers` use `spec.initContainers`
        * see: https://kubernetes.io/docs/concepts/workloads/pods/init-containers/
    * Sidecar Container: Like init, but continues to run (e.g. log-shipper)
        * use `spec.initContainers[x].restartPolicy = Always`
* If want to see logs, we have to operate on a container, not on the pod: `k logs my-pod my-container-1`

Scheduling / Pod Placement
==================================================

Taints and Tolerations
--------------------------------------------------------

* scheduling relationship node and pod: like a repellent spray (taint) on a person (node), and the mosquitos (pods)
  tolerance
    * nodes can provide special resources, thus "protect" with a taint; by default, all pods are intolerant (excluded by
      default)
* how to do it:
    * `k taint nodes node-name key=value:<taint-effect>`
        * taint-effects: { NoSchedule, PreferNoSchedule, NoExecute (evict pre-existing non-fitting) }
        * e.g.: `k taint nodes my-node app=backend:NoSchedule` (add a minus at the end `NoSchedule-` to "untaint" it)
    * on pods, add `spec.tolerations` (key, operator (In, Exists, ...), value, effect)
* BUT: it restricts certain pods on a node, but does NOT guarantee a pod will run on a certain node => Node Affinity

Node Selectors
--------------------------------------------------------

* an easy way to assign; but not sufficient (too simple) for complex requirements (no advanced expressions)
* declare a `spec.nodeSelector` with a list of labels (key-values) to request a specific node type
    * for the node: `k label nodes <node-name> <label-key>=<label-value>`

Node Affinity / Anti-Affinity
--------------------------------------------------------

* the "big brother" of node selectors: more powerful, more complex
* declare under `spec.affinity` ... terms/match expressions

More:

* what if no node found for the pod? what if matching label is removed of node afterwards?
* affinity types: required/preferred scheduling, (required?) ignored execution
* usage:
    * if want to "block off" other pods from your nodes, use taints&tolerations (defense, passive)
    * if want to limit your own pods to your nodes, use node selectors (offense, active)
    * use a combination for clean assignment and isolation

Pod Lifecycle
==================================================

Readiness Probes
--------------------------------------------------------

* pod has status and conditions
    * status represents lifecycle: Pending (scheduler tries to find a node), ContainerCreating (image pulled), Running
    * conditions complement status (more detailed), like flags: PodScheduled, Initialized, ContainersReady, Ready
* the ready condition is usually not properly tight to reality (actually readiness), it might get traffic too early (
  service is too eager routing users)
    * use: for web apps, a "ready" endpoint, for DB establish a 3306 TCP socket connection, some other shell script...
    * pod is only Ready when readiness probe succeeds, thus no service disruption occurs
* see: https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/

Liveness Probes
--------------------------------------------------------

* periodically checking whether pod is still healthy (maybe app has a bug; container up and running though)
    * if unhealthy, pod is destroyed and recreated

Resources
==================================================

Computation
--------------------------------------------------------

* Specified as resources, with either request (guaranteed) or limit (max)
    * by default, nothing is set, thus a pod can go rogue!!! (consume e.g. all the CPU and suffocate other containers)
    * ideal is to set only request; use as much CPU as available (no-limit), but guarantee others to also have some if
      needed
    * if limit is set, but not request, then k8s will set request to limit
* Memory and CPU
    * Memory: in K/M/G (1000 bytes), power of then or Ki/Mi/Gi (kibibytes, 1024 bytes, binary)
        * k8s needs to be precise (scheduling) with resource allocation, etc.
    * CPU in integers, or even (minimum) 0.1 == 100m (milli)
        * 1 CPU == 1 vCPU/core/hyperthread (depending on cloud provider)
* Overdoing it:
    * CPU will be simply throttled
    * Memory can go above limit for some time; if above constantly, pod will be terminated with Out-of-memory error (
      OOMKilled terminated state)
        * also if no limits are set, one pod holds a lot, other wants more, only solution: kill one pod (no throttling
          with memory!)
* use `LimitRange` object to set default (apply to namespace):
    * https://kubernetes.io/docs/concepts/policy/limit-range/
    * CAVE: these are applied during container/pod-creation (not enforced during runtime)
* use `ResourceQuota` to limit resources for all pods in the cluster (on a namespace level again)
    * https://kubernetes.io/docs/concepts/policy/resource-quotas/

Persistence
--------------------------------------------------------

* StatefulSets (not! Deployments!) for persistent storage (plus: Persistent Storage Claim)

Networking
==================================================

Services
--------------------------------------------------------

* enables connectivity; lose coupling between parts of the cluster
* to access (group of) pods within and outside the cluster (for users or other services)
* service types:
    * ClusterIP: (default); PS: not "ClusterIp" (uppercase!)
        * only communication within cluster; group of pods together, providing single access interface
        * creates a virtual IP inside the cluster (communication among pods / tiers like FE and BE and DB)
    * NodePort:
        * enable access from outside the cluster (access via the cluster's IP)
            * make internal (container) port accessible on a port on the node
        * NodePort (30000-32767) forwarded to an internal pod's (Taget)Port
        * if pods distributed accross nodes, service will be spanned automatically (different node IPs!)
        * like a virtual server inside the node (has its own cluster IP)
        * PS: nodes (IPs) can be accessed from outside the cluster, via: `minikube service my-service --url` (and use
          the `nodePort` property)
        * implicit load balancing capabilities (random node selection by labels)
    * LoadBalancer:
        * create user facing, stable URL; e.g. to balance load within a tier
        * requires provision, e.g. HAProxy, nginx; or on supported cloud provider, native load balancer available
        * uses NodePort under the hood (as any pod/container runs on a node, whose IP we need)
* the selector will result in "attached Endpoints" (how many pods fit the selection; runtime evaluation of selector)

IP Addresses
--------------------------------------------------------

* You must manually install cluster networking; k8s requires all communication without NAT
    * E.g. calico, canal, flannel, romana, weave net...

Monitoring
==================================================

* k8s has nothing built-in, instead use opensource solutions: metrics server, prometheus, elastic stack, (proprietary:
  datadog, dynatrace)
    * cAdvisor (container advisor, part of kubelet), retrieves performance metrics from pods
* enable metrics server:
    * simple: `minikube addons enable metrics-server`
    * advanced: `git clone https://github.com/kubernetes-sigs/metrics-server.git`, and apply manifest files
        * or: `k apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml`
    * allowing for new command: `k top node` or `k top pod` (acts like a `ps`)
