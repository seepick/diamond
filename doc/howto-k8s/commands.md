minikube
====

* Install `❯ homebrew install minikube`
* might need to check whether your OS supports virtualization (maybe also need a hypervisor like virtualbox?)
* Running `❯ minikube start` you might receive an error:
    * "_❌ Exiting due to DRV_DOCKER_NOT_RUNNING: Found docker, but the docker service isn't running. Try restarting the
      docker
      service._"
    * Start [Docker Desktop](https://www.docker.com/products/docker-desktop/) and try again (this will take a while).
    * "_🏄 Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default_"

Assuming we have deployed the demo-app image `demo-app:latest`.

* Create a deployment: `kubectl create deployment demo-minikube --image=demo-app:latest`
* Expose internal network: `kubectl expose deployment demo-minikube --type=NodePort --port=8080`
* Verify existing: `kubectl get services demo-minikube`
* Access via managed-browser: `minikube service demo-minikube`
    * Or portforward: `kubectl port-forward service/demo-minikube 8080:8080`
* If pod has `ErrImagePull`:
    * Redirect docker to minikube: `eval $(minikube docker-env)`
    * Now build (and push) your image: `docker build -t demo-app:latest .`
    * List all images: `minikube image ls --format table` (should see `docker.io/library/demo-app`)
    * https://minikube.sigs.k8s.io/docs/handbook/pushing/
    * https://www.baeldung.com/ops/docker-local-images-minikube
    * might not work directly; simply use kubernetes manifest files instead

* `minikube delete` to delete the cluster
* `minikube addons list` to list all available addons (plugins like; ingress, istio, ...)
    * enable addon during startup `minikube start --addons <name1> --addons <name2>` or later
      `minikube addons enable <name>` (https://minikube.sigs.k8s.io/docs/handbook/deploying/)

* get external URL: `minikube service my-demo-app --url`

kubectl
============================

* API reference: https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.24/
* Install `❯ brew install kubernetes-cli`
* Configure your shell first: `alias k=kubectl`
    * Enable ZSH plugin for autocompletion and many more aliases:
    * https://github.com/ohmyzsh/ohmyzsh/blob/master/plugins/kubectl/kubectl.plugin.zsh

General Info
----------------------------------------------------

* Get help: `❯ k set image --help`
* Commands follow a common pattern: `k <ACTION> <OBJECT> [IDENTIFIER] [OPTIONS]`
    * ACTION:
        * `get` - get a list of objects of the same type, or a single object by its ID
        * `describe` - get more details about a single object
        * `apply` - tell k8s to apply changes to the cluster (based on a given file); changing the desired state
        * `logs` - show stdout output
        * `exec` - execute a single command (possible logging into a remote shell)
        * `port-forward` - to debug a pod, expose its port, make it locally available
        * `explain` - print info for a specific object type
    * imperative style ACTIONs (don't do them! always use declarative YAML approach)
        * `delete` - remove an object from the cluster
        * `edit` - be presented a generated yaml file and edited to be applied immediately
        * `create` - create a new object (or `run` a single pod to create one)
        * `scale` - change number of pods for a replica set
    * OBJECT: `pod[s]`, `nodes`, `deployments`, `services`, ...
    * IDENTIFIER: pod-name/deployment-name/service-name, ... also multiple possible
    * OPTIONS: `-n namespace`, `-f filename`, `--dry-run=client`, ...
* Tip: use shorter names such as: "pod" over "pods", "deploy" over "deployment", "svc" over "service", "ns" over "
  namespace"
* `apply -f .` for all files in the cwd
* `apply` vs `create`
    * apply: declarative, repeatable updates
        * Creates or updates resources, Applies only the differences
        * Compares: last applied config, desired config, live state
        * stores its state in "last applied config" (thus able to compute 3-way merge: desired, last applied, live)
    * create: one-time creation; Fails if the resource already exists; Does not track changes over time
        * For: Bootstrapping, One-off objects (namespaces, secrets), Manual workflows, Quick experiments
        * Is: Imperative, Not idempotent, No state tracking
        * PS: Could do a `❯ k create -f app.yaml --save-config` but still apply is preferred

Basic Orientation
----------------------------------------------------

* List everything: `❯ k get all` (maybe filter for `--namespace`)
* List several with comma: `❯ k get deployments,svc`
* `❯ k cluster-info`
* List all available nodes in the cluster:

```shell
❯ k get nodes
NAME       STATUS   ROLES           AGE     VERSION
minikube   Ready    control-plane   3m12s   v1.34.0
```

* List all pods in all namespaces (`-A` for all):

```shell
❯ k get pods -A
NAMESPACE     NAME                               READY   STATUS    RESTARTS        AGE
kube-system   coredns-66bc5c9577-ffj2d           1/1     Running   0               4m28s
kube-system   etcd-minikube                      1/1     Running   0               4m34s
kube-system   kube-apiserver-minikube            1/1     Running   0               4m35s
kube-system   kube-controller-manager-minikube   1/1     Running   0               4m34s
kube-system   kube-proxy-b2snn                   1/1     Running   0               4m28s
kube-system   kube-scheduler-minikube            1/1     Running   0               4m35s
kube-system   storage-provisioner                1/1     Running   1 (3m58s ago)   4m33s
```

* Get details of a specific pod:

```shell
❯ k describe pod mypodname
```

Setup
----------------------------------------

* Create a static pod (usually we don't do that!):

```shell
❯ k run mypodname --image=nginx
pod/mypodname created
```

* Create a Pod manifest `pod.yaml` (the kind value is case-sensitive, not pod but Pod!):

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  containers:
    - name: my-container
      image: nginx
```

(or let it be generated: `❯ k run nginx --image=nginx --dry-run=client -o yaml > nginx.yaml` and
`k create -f nginx.yaml`)

and run it:

```shell
❯ k create -f pod.yaml
pod/my-pod created
```

* Imperative, generic edit (and apply on save): `❯ k edit deployment/nginx-deployment`

Deployments
----------------------------------------

* Relevant commands:
    * `❯ k rollout undo deployment/my-deployment`
    * `❯ k rollout status deployment/my-deployment`
    * `❯ k rollout history deployment/my-deployment`
* Create a Deployment manifest `deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
        - name: nginx
          image: nginx:1.14.2
          ports:
            - containerPort: 80
```

* And apply it: `❯ k apply -f deployment.yaml`
    * Wait and watch until all pods are running
    * Verify result: `❯ k get deployments --all-namespaces`
* Downscale easily again by changing Yaml, and apply again.
* Don't do it imperatively!
    * Edit image: `❯ k set image deployment/my-deployment container-name=group/image:version`

Service
----------------------------------------

* Types: ClusterIP, NodePort, LoadBalance
* Get node's external-IP: `❯ k get nodes -o wide`
* Get service's external-IP: `❯ k get svc`

Misc
----------------------------------------

* Let k8s generate a yaml for you: `❯ k run nginx --image=nginx --dry-run=client -o yaml`
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

k9s
========================================

* Install `❯ brew install derailed/k9s/k9s`
* Run `❯ k9s`

kustomize
========================================

* Install `❯ brew install kustomize`
* Yamls...

oc
========================================

* Install `❯ brew install openshift-cli`
* ... do basically the same as with kubectl ...
* Simply: `❯ oc get pods`
