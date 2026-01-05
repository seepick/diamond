Overview
====

Links:

* See: https://notes.kodekloud.com/docs/Certified-Kubernetes-Application-Developer-CKAD/First-Section/Introduction
* Do: https://learn.kodekloud.com/user/courses/kubernetes-challenges
* Watch: https://www.youtube.com/watch?v=X48VuDVv0do

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
==================================================

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
* **Workload** - a running thing in k8s like a webservice, database; or temporary things like batch processing,
  analytics, reporting
* **cloud-native application** - designed to meet cloud-like demands (scaling, healing, rolling updates, rollbacks,
  etc.)
* **containerized application** - packaged as a container image
* **Sets** - groups of objects with a common characteristic (e.g. all pods in a deployment have the same label)
* **ReplicaSet** - manages pods based on a desired state (number of pods running; load balancing, auto-scaling)
    * **Replication Controller** - deprecated (use ReplicateSet instead; with rs selector field is mandatory)
* **StatefulSet** - Like deployments but for persistence/DBs, as provides ordered startup and stable hostnames
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
    * List revisions: `❯ k rollout history deployment/my-deployment`
        * Get details of a revision: `❯ k rollout history deployment my-deployment --revision=1`
    * Live monitor progress: `❯ k rollout status deployment/my-deployment`
* The old will be preserved, to be able to rollback (simply scaling down/up pods).
    * Rollback via: `❯ k rollout undo deployment/my-deployment` and check via: `❯ k get replicasets`
        * Or to a specific revisions with the `--to-revision` option
* Deployment Strategies:
    * **RollingUpdate**: Tear down/start up one-by-one, zero downtime (the default)
    * **Recreate**: Tear all down, short application unavailability, start new ones up.
* Deployment Approaches (not options which can be choosen from, but implemented customly):
    * **Blue/Green**: 100% of old (blue) running and receive traffic, while new (green) is running but not trafficed (
      run tests first), then switch all at once
        * Steps: v1 deployment and service; new v2 deploy; change selector in service; done
    * **Canary**: a progressive blue/green deployment: only route a bit of traffic and observe before going 100%
        * Naive k8s implementation: v1 deployment and service; new v2 deploy with 1 pod and a shared label as v1;
          service picks up both now; done.
        * like the birds in mines, smelling the gas
    * Use a service mesh like istio, for more sophisticated implementation of those

Namespaces
--------------------------------------------------------

* to group objects together, to isolate them (not accidentally configuring wrong one); short: "ns"
    * kind-a "virtual cluster", e.g. for environments: dev-test-acc-prod
    * k8s has its own (kube-system for internal, and kube-public for shared)
* features: custom policies (RBAC; who can do what), ResourceQuotas (limit resources), constraints, ...
* to connect outside of the current namespace: `<SERVICE>.<NAMESPACE>.svc.cluster.local`
    * k8s does some internal DNS management for you
* kubectl commands:
    * get objects for all namespaces: `k get pods -A`
    * get objects for a specific ns: `k get pods --namespace=kube-system`
* some objects are _not_ namespaced, e.g.: nodes, PersistentVolumes, cluster roles (bindings), ..., namespaces
  themselves
    * see a complete list: `k api-resources --namespaced=true` (or: false)

API Groups
--------------------------------------------------------

* as defined in the Yaml `apiVersion: group.foobar/version`
* k8s objects are grouped, e.g.: /apps, /extensions, /networking.k8s.io
* they have versions:
    * once a group is in "/v1" it is in a generally available (GA) stable version
    * or /v1beta1 or /v1alpha1
    * preferred vs. storage versions (as stored in etcd, not easy to access though)
* can enable APIs with API server config param `--runtime-config=batch/v2alpha1,...`
* define your own resources with a CRD (CustomResourceDefinition)
    * could then also define your own controllers

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

```yaml
# for a container (in a Deployment):
readinessProbe:
  tcpSocket:
    port: 8080
  initialDelaySeconds: 15
  periodSeconds: 10
livenessProbe:
  # exec:
  #   command:
  #   - cat
  #   - /tmp/healthy
  httpGet:
    path: /healthz
    port: 8080
    httpHeaders:
      - name: Custom-Header
        value: Awesome
  initialDelaySeconds: 3
  periodSeconds: 60
  # failureThreshold: 1
  # successThreshold: 1
```

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

Jobs
--------------------------------------------------------

* Use native k8s object `Job`, using the regular pod template inside of it
    * Use `spec.completions` to specify a sequence of pods (one starts after previous completed successfully)
    * Use `spec.parallelism` for them to be started at the same time
* PS: We could simply change the restart policy to never, to do the same naively (by default, pods are restarted once
  finished)

CronJobs
--------------------------------------------------------

* just like linux crontab; or like a delayed k8s job, but scheduled periodically
* the 1st spec for the cronjob, the 2nd for the job, the 3rd for the pod
    * the 2nd spec for job is an "embedded part", just like pods to deployments

```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: my-job
spec:
  schedule: "30 21 * * *" # min hour month_day month week_day
  jobTemplate:
    spec:
      completions: 3
      parallelism: 3
      backoffLimit: 25 # This is so the job does not quit before it succeeds.
      template:
        spec:
          containers:
            - name: my-container
              image: my-image
          restartPolicy: Never
```

Persistence
==================================================

* StatefulSets (not! Deployments!) for persistent storage (plus: Persistent Storage Claim)

Docker Basics
--------------------------------------------------------

* layered architecture: each line in Dockerfile a new layer (reuse from bottom to top, or actually top to bottom)
* the image layers are read-only; when running an image, we have a writable container layer (gone when container dead)
    * writing in image still possible via an implicit "copy-on-write"
* mounting:
    * volume mounting: create a volume (docker), then mount it to the container
    * bind mounting: map a local folder (absolute path) to a container folder
* storage and volume drivers...
    * "storage drivers" do the hard lifting (aufs, zfs, btrfs, device mapper, overlay); docker will choose one for your
      OS (or you can be picky based on specific needs)
    * "volume drivers" managed via volume driver plugins (local, azure, convoy, rex ray, flocker ...); to provision on
      AWS, EBS, S3, ...

Volumes and Claims
--------------------------------------------------------

* specify volumes with a storage solution (NFS, GlusterFS, ...)
    * attach a volume to a pod (actually `container.volumeMounts`) to persist data
    * the order is: PV -> PVC -> Pod (mounted volume)
* persistent volumes: to not have to reconfigure each pod with the same volume configuration, but centralize it
    * the cluster (admin) provides volumes
    * the pods (users/devs) request a volume with a "PVC" = PersistentVolumeClaim
    * the volume and the claim will be bound in a 1-to-1 relationship
        * request properties: capacity, access mode, volume mode, storage class; or use labels&selectors to be more
          specific
        * if nothing found to be bound, PVC stays in "pending" state
* if pod is done, reclaim policy: Retain (keep), Delete
    * Recycle policy is deprecated, data is scrubbed before reuse, but not sophisticated enough for portability/security
      gaps
    * Use storage class / CSI drivers instead...

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: my-pv
spec:
  storageClassName: manual
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce # or: ReadOnlyMany, ReadWriteMany
  hostPath:
    path: "/mnt/data"
```

And the claim (https://kubernetes.io/docs/concepts/storage/persistent-volumes/#persistentvolumeclaims):

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-claim
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi # capacity request less than provided is ok
```

And in the pod (deployment template) refer to the PVC:

```yaml
volumes:
  - name: mypd
    persistentVolumeClaim:
      claimName: myclaim
```

Storage Class
--------------------------------------------------------

* without it, would need to first manually create a volume in cloud provider, and then create the PV
* with it, volumes are "dynamically provisioned"
* no more PV to be created manually (done under the hood for you), instead: SC -> PVC -> Pod
    * check via: `k get sc,pvc,po`
    * recape: storage classes create the PV for you, so your PVC refers to SC (not PV)
* define the details such as: disk/ssd, replication, ... group those, give them names like "normal, gold, platinum"
* if VolumeBindingMode set to WaitForFirstConsumer, then provisioning delayed until first pod actually consumes
* more details are very specific to the cloud provider...

StatefulSet
--------------------------------------------------------

* Similar to deployment (ReplicaSet): manages pods (scaling, updates, ...)
    * The Yaml manifest is identical to deployment (different kind of course), with additional headless service
      reference
    * Goal: to differentiate master/slave for replicas
* Offers more sophisticated logic
    * **sequential startup** of pods: previous needs to run&ready before next pod starts ("ordered, graceful
      deployment")
    * stable **host names**: 0-base indexed names (instead hash-suffixed); name retains even after crash&restart (sticky
      network ID by DNS)
    * Adjust by e.g. `podManagementPolicy: Parallel`
* all the pods need a service (also load balancing) in front of them
* When referring to a PVC, then all the pods in the SS will use that single one (not all storage types support that
  though)
    * Or: for each pod a PVC, each a PV, created by a single SC
    * Need a VolumeClaimTemplate (basically a regular PersistentVolumeClaim but templateized)
        * do so in the SS under `spec.volumeClaimTemplates` with the content of a regular PVC

Headless Service
--------------------------------------------------------

* provides a DNS entry to reach a pod (as IPs and hostnames (which are based on IPs) are not stable)
    * doesn't have an own (cluster) IP address; simply creates DNS entries; also doesn't do load balancing
    * results in something like: `podname.headless-servicename.namespace.svc.cluster.local`
* the master is the only one receiving write-operations, and replicates it to the slaves
    * reads can be load balanced across all pods; thus: the service must not load balance writes!
* Yaml is identical to one of a regular Service (name, port, selector), but set `clusterIP: None`
* With Deployment, you have to wire the DNS stuff yourself
    * (Refer to the service: `spec.subdomain: service-name`)
    * (And set `spec.hostname: pod-name` to create a DNS record with the podname)
    * with with StatefulSet it does that automatically for you
        * BUT: still need to refer to the headless service via `spec.serviceName: my-service`

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

Network Policies
--------------------------------------------------------

* https://kubernetes.io/docs/concepts/services-networking/network-policies/
* A `NetworkPolicy` is an object similar to a service (use label selectors to be applied to pods)
    * Once configured all other traffic will be blocked
* Ingress = Incoming, Outgoing/External = Egress (port, protocol, ...)
    * A list of (disjuncted) pass-through rules, each having selectors by: pod label, namespace label, IP range
* By default, all pods can see each other; "All Allow" rule
    * Security wise, block e.g. communication channel from web-server to DB (only from API pods)
    * Once a network policy is applied, everything is blocked, except what's explicitly allowed
* Not all network policies are supported by all "network solutions" (kube-router, calico are ok; flannel limited)

Ingress
--------------------------------------------------------

* `NodePort` to expose, or use a "cloud native load balancer"; reverse proxy
    * And don't forget the DNS entry and proxy for port mapping
* routing incoming requests to specific services
    * based on: URL, domain name, or hostname
    * using a default backend if no rules matched
* Have SSL security (certificates) in a single place (low maintenance)
* You need an "Ingress Control": nginx (not only a webserver!), istio, HAProxy, traefik (k8s doesn't provide one by
  default)
    * The configuration rules (Yaml files) you will make are "Ingress Resources"
* e.g. for nginx ingress controller...
    * a Deployment with the right image
    * a ConfigMap with all kinds of settings
    * a Service to expose 80/443 ports
    * a ServiceAccount with the proper (cluster-)role(-binding), etc.
* watch out for:
    * disabling SSL redirects via annotation: `http://nginx.ingress.kubernetes.io/ssl-redirect: "false"`
    * URL rewrite via annotation: `nginx.ingress.kubernetes.io/rewrite-target: /`
        * see https://kubernetes.github.io/ingress-nginx/examples/rewrite/

Security
==================================================

* linux (host/container) namespaces; process isolation, ... (shared OS kernel, opposed to with VMs)
* container user is by default root, but with limited capabilities; or change user to run with
    * capabilities can only be set on containers (not pods)
* regarding Secrets: see "Configuration" section
* usually passwords disabled, but SSH keys instead
* kubernetes internal guys (api-server, etcd, scheduler, controller, kubelet, kube-proxy) talk via TLS encryption (
  certificates)
* by default all pods can see each other; except if network policies were defined
* instead of a reference to a cert-file, you can also add it directly embedded (base 64 encoded) with the `*-data` Yaml
  entry

Service Accounts
--------------------------------------------------------

* in order to allow applications talk like we (humans) do via kubectl, a service account is require:
  `kubectl create serviceaccount my-user`
    * and: `kubectl create token my-user` (before k8s version 1.24, this was done implicitly with account creation)
    * the token (auth-bearer) is stored in the associated secret object
        * decode the token via https://www.jwt.io or:
          `jq -R 'split(".) | select(length > 0) | .[0],.[1] | @base64d | fromjson' <<< eyJhb...`
    * preferably mount volume for the secret: use `serviceAccountName` on a pod's spec to do so
    * CAVE: no expiry date set for the token! (need to do some more logic in yaml files)

User Management, Roles (Bindings)
--------------------------------------------------------

* k8s doesn't do it itself, use auth mechanisms for that
    * { password/token file, certs, identity service } or third party like { LDAP, kerberos }
* different user types: admins, devs, end-users, bots (service accounts for 3rd party integrations)
* authorization modes (mechanism): RBAC (role-based), ABAC (attribute-based), Node, Webhook mode, ...
    * multiple modes can be configued and are executed in sequence until a permit is created
    * execute `kubectl describe pod kube-apiserver-controlplane -n kube-system` and look for `--authorization-mode`
* specific verbs (operations) are permitted for specific resources (pods) (and groups) for a user (ABAC) or role (RBAC)
* RBAC:
    * first create a Role, then a RoleBinding to link a user (subjects) to that Role (roleRef)
    * Role and RoleBinding are namespaced
* check your permissions: `kubectl auth can-i create deployments`
* view them via: `k get roles` and `k get rolebindings`
    * they have resources (resource names) and verbs (get, create, update, delete, ...)

E.g.:

```yaml
kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: developer
rules:
  - apiGroups: [ "" ]
    resources: [ "pods" ]
    verbs: [ "list", "create","delete" ]
---
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: dev-user-binding
subjects:
  - kind: User
    name: dev-user-007
    apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: developer
  apiGroup: rbac.authorization.k8s.io
```

* use cluster roles (bindings) to:
    * manage cluster based resources (see "namespaces" section), such as nodes
    * manage resources across all namespaces

Certificates
--------------------------------------------------------

* the triple: client-key (admin.key), client-cert (admin.crt), certificate authority (ca.crt)
* for authentication (of users and k8s components between them)

* TLS creation: `kubectl -n my-namespace create secret tls my-tls --cert "/keys/my-tls.crt" --key "/keys/my-tls.key"`

K8S API Access
--------------------------------------------------------

* old `/api` (set of core resources) and new `/apis` (more organized: in groups and resources underneath)
    * verbs (list, get, create, ...) applied to each resource
* when directly interacting (curl/wget) with the API server, reuse certs specification via `kubectl proxy` (!=
  kube-proxy)

Admission Controllers
--------------------------------------------------------

* somehow similar like RBAC auth, but more fine-grained; security measures, enforce certain cluster usage
    * e.g.: enforce specific image registry, disallow latest version, disallow root, certain capabilities, ...
* many of these controllers pre-built available: AlwaysPullImages, DefaultStorageClass, EventRateLimit, ...
* procedure is: user -> kubectl -> API server -> authentication -> authorization -> admission controller -> operation
* they can be enabled via the run options of the API server (`enable/disable-admission-plugins`)
    * do so in the `/etc/kubernetes/manifests/kube-apiserver.yaml` file
    * check what's configured: `ps -ef | grep kube-apiserver | grep admission-plugins`
    * or: `k exec -it kube-apiserver-controlplane -n kube-system -- kube-apiserver -h | grep 'admission-plugins'`
* there are two controller types:
    * mutating: change request, e.g. add default!
    * validating: allow/deny after validate e.g. exists?
    * (they can also be custom implemented via webhooks)

Advanced
==================================================

Monitoring
--------------------------------------------------------

* k8s has nothing built-in, instead use opensource solutions: metrics server, prometheus, elastic stack, (proprietary:
  datadog, dynatrace)
    * cAdvisor (container advisor, part of kubelet), retrieves performance metrics from pods
* enable metrics server:
    * simple: `minikube addons enable metrics-server`
    * advanced: `git clone https://github.com/kubernetes-sigs/metrics-server.git`, and apply manifest files
        * or: `k apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml`
    * allowing for new command: `k top node` or `k top pod` (acts like a `ps`)

Customization
--------------------------------------------------------

* CRDs: Custom Resource Definitions: a k8s object itself, using OpenAPI spec for attributes
* custom controllers (otherwise CRDs are useless), running in a loop in a pod/deploy, written in Go
* Operator framework: CRDs + custom controllers (deployed as a single entity)
    * e.g. the etcd-operator, which manages, maintains, fixes, installs, backsup, restores, etc. etcd.
    * see: http://operatorhub.io

Helm
--------------------------------------------------------

* like a package manager for k8s; looking at single pieces as a whole application
    * choose an application (http://artifacthub.io) and do a `helm install my-app`, or upgrade/rollback/uninstall
    * no more need to define, configure, and create all single parts (and remember them to delete them)
* creating charts: templates + values
    * convert yaml files to template-yaml files, introducing Go templates `{{ .Values.foobar }}`
    * provide a `values.yaml` file for all the parameters required
    * more features than kustomize: conditionals, loops, functions, hooks...

Kustomize
==================================================

* Homepage: https://github.com/kubernetes-sigs/kustomize
* Simple sample: https://github.com/kubernetes-sigs/kustomize/blob/master/examples/helloWorld/README.md

Basics
--------------------------------------------------------

* `brew install kustomize` (or for linux:
  `curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack/install_kustomize.sh" | bash`)
* built-in support in kubectl for kustomize! (but it is sometimes outdated, so better install kustomize CLI)
* to support variations for different environments (development, test, staging, production)
    * without kustomize, usually have folders for each env, and duplicate all the Yamls across with slight adaptions
    * BUT this doesn't scale; copying; change; ... mismatch in configs
    * solution: reuse k8s configs, only modify what needs to be changed (per env)
* super easy to learn (compared with templating magic from helm); all just yaml (but also not as powerful)
* core concepts are: **base** (basic shared config, default values) and **overlay** (specific overrides per env)
    * these are reflected in the file system:

```
k8s/
  base/   <= shared/default configs
    kustomization.yaml.  <= entry point
    backend.deploy.yaml
  overlays/   <= per environment sub-folder for overrides
    dev/
      kustomization.yaml
      config-map.yaml
    prod/
      kustomization.yaml
      config-map.yaml
```

* run it by executing: `kustomize build <kdirectory>` (or without the native binary: `kubectl kustomize <kdirectory>`)
    * this will only display the generated, "raw" k8s yaml files (you still need to apply them to the cluster)
* generate and apply with native support: `kubectl apply -k <kdirectory>` (instead of the common `-f` option)
    * otherwise could trick around with shell tools and chain them: `kustomize build <kdirectory> | kubectl apply -f -`
    * or `delete` instead of `apply` to tear it all down again

Transformers
--------------------------------------------------------

* several built-in, or create custom one
* common transformations: commonLabel/commonAnnotations, namePrefix/Suffix, Namespace

```yaml

labels:
  - pairs:
      labelKey: labelValueToAdd
  includeSelectors: true # otherwise only in the top level manifest objects, not down to e.g. deployment.spec.template.metadata (for pods)
# commonLabels: # NO: commonLabels is deprecated!
#   labelKey: labelValueToAdd
commonAnnotations:
  some.foobar.io: config-value

namespace: my-namespace
namePrefix: dev-
nameSuffix: "-001"

# image transformer:
images:
  - name: nginx # matches containers.image (not containers.name!)
    newName: haproxy # replaces containers.image (not containers.name!)
    newTag: "1.2.3" # the version (double quote as others int not string!), thus "haproxy:1.2.3"
```

* when having subdirectories, the `kustomization.yaml` of each directory will only apply from this level recursively
  downward.
    * if multiple same transformers applied, then "most lower" will be applied first.
* WATCH OUT: when first creating all, then modifying kustomize files (e.g. namespace), and then try to delete won't work
  anymore ;)

Patches
--------------------------------------------------------

* A more "surgical" (targeting specific sections) approach to customize k8s configs
* Requires 3 attributes:
    * Operation types: add | replace | remove
    * Target: name, namespace, labelSelector, ...
    * And the actual value (for add/replace only of course, not remove)
* Two patch types are available: Inline and strategic.

An **inline** patch (or officially Json RFC-6902 patch; traget + patch details):

```yaml
patches:
  - target: # kind of a selector
      kind: Deployment
      name: web-deployment
    patch: |-
      - op: replace
        path: /metadata/name
        value: api-deployment
```

A **strategic** merge patch:

```yaml
patches:
  - patch: |-
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: api-deployment # selector
      spec:
        replicas: 5 # replace
```

* Patches can be defined inline or in a **separate file**:

```yaml
# kustomization.yaml
patches:
  - path: replica-patch.yaml
    target:
      kind: Deployment
      name: nginx-deployment
---
# replica-patch.yaml
- op: replace
  path: /spec/replicas
  value: 5
```

* Replace a dictionary Json6902 patch:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api-deployment
spec:
  template:
    metadata:
      labels:
        component: api
    #...
---
patches:
  - #...
    patch: |-
      - op: replace
        path: /spec/template/metadata/labels/component
        value: web
```

* Or the same with a strategic merge patch (basically a copy'n'paste with an updated value):

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api-deployment
spec:
  template:
    metadata:
      labels:
        component: web
```

* Adding a new property (for strategic identical as with replace; the yamls are being merged):

```yaml
patch: |-
  - op: add
    path: /spec/template/metadata//labels/addedLabelKey
    value: addedValue
```

* Removing a property for strategic patches (for 6902 you simply use the `remove` operator):

```yaml
spec:
  template:
    metadata:
      labels:
        oldKey: null
```

* Identify elements of a **listed value** by index: `path: /spec/template/spec/containers/0` (meh, seems very fragile to
  me); value is then name & image map
    * or via strategic patch, by providing the search name, and change the image (seems no way to change the container
      name as it is used for selection?!)
* To add an item: `path: /spec/template/spec/containers/-` (`-` add as last, or index where before to add, e.g. 0 for
  first)
    * via strategic patch, simply declare it (hopefully name is not yet used, otherwise no explicit statement that this
      is an add, not a replace)
* To delete just use `op: remove` and an index for the path
    * For strategic:

```yaml
spec:
  containers:
    - $path: delete
      name: my-container
```

Overlays
--------------------------------------------------------

* This is kustomize's main usecase! Reuse config per environment basis.
* in each overlay (`kustomization.yaml`), refer to the base folder `bases`
    * Also add patches (see previous chapter), and additional resources (deployments, services, config maps, etc.)
* The structure folder for base and overlays is independent; they don't need to match up.
* To apply an overlay, simply do a: `k apply -k /path/to/overlays/dev`

Components
--------------------------------------------------------

* Reusable parts of config logic (patches, resources); can be included in several overlays (thus, not possible to put in
  base).
* When the app supports optional features; enable only in certain "subset" overlays.
    * Avoid to having copy'n'paste the same config in several overlays if they are identical (scalability again; avoid "
      config drift").
* E.g. caching with redis only for premium overlays (not dev); or different DBMS

How to:

* Create a folder `components/` next to base and overlays, quiet similar to overlays themselves.
    * Create a `kustomization.yaml` file, BUT the kind must be `Component` (and not `Kustomization`).
    * Also watch out to not re-define base (not necessary if invoked from overlay; actually will result in an error if).

```yaml
apiVersion: kustomize.config.k8s.io/v1alpha1
kind: Component
resources:
  - db-deployment.yaml
  - db-service.yaml
secretGenerator:
  - name: db-creds
    literals:
      - password=password1
patches:
  - path: api-patch.yaml
```

* In your overlay config, simply import the component by adding:

```yaml
components:
  - ../../components/db
```

Open Questions
--------------------------------------------------------

* if try to remove something which doesnt exist, will it fail as expected? (seems error-prone this yaml operation
  approach)
* how to do relative changes, e.g. "increment replicate by 1" (instead of using an absolute value)?
* best practice assumption: prod is the default (in base), as better fuck-up dev with prod-values, then prod with
  dev-values!
* when modifying artifacts (name-prefix/suffix), and i use patches to select certain items, will it filter before or
  after the modification?
    * e.g. name: "service"; suffix: "-app"; select traget name: "service" or "service-app"?
* regarding commands: when to use
    * `command: [ "someBin", "someArg" ]` (definitely NOT: `command: [ "someBin someArg" ]`)
    * `command: [ "sh", "-c", "someBin someArg" ]`?
    * or command + args property?
