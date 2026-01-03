minikube
============================

* Install `❯ homebrew install minikube`
* might need to check whether your OS supports virtualization (maybe also need a hypervisor like virtualbox?)
* Running `❯ minikube start` you might receive an error:
    * "_❌ Exiting due to DRV_DOCKER_NOT_RUNNING: Found docker, but the docker service isn't running. Try restarting the
      docker
      service._"
    * Start [Docker Desktop](https://www.docker.com/products/docker-desktop/) and try again (this will take a while).
    * "_🏄 Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default_"

Assuming we have deployed the demo-app image `demo-app:latest`.

* Create a deployment: `❯ kubectl create deployment demo-minikube --image=demo-app:latest`
* Expose internal network: `❯ kubectl expose deployment demo-minikube --type=NodePort --port=8080`
* Verify existing: `❯ kubectl get services demo-minikube`
* Access via managed-browser: `❯ minikube service demo-minikube`
    * Or portforward: `❯ kubectl port-forward service/demo-minikube 8080:8080`


* `❯ minikube delete` to delete the cluster
* `❯ minikube addons list` to list all available addons (plugins like; ingress, istio, ...)
    * enable addon during startup `minikube start --addons <name1> --addons <name2>` or later
      `❯ minikube addons enable <name>` (https://minikube.sigs.k8s.io/docs/handbook/deploying/)

* after exposed via NodePort service, get external URL: `❯ minikube service my-service --url`

* also nice: `❯ minikube dashboard` to start a neat web UI

ErrImagePull Registry Issue
---------------------------------------------

* If pod has `ErrImagePull`...
    * Redirect docker to minikube: `eval $(minikube docker-env)`
    * Now build (and push) your image: `docker build -t demo-app:latest .`
    * List all images: `minikube image ls --format table` (should see `docker.io/library/demo-app`)
    * https://minikube.sigs.k8s.io/docs/handbook/pushing/
    * https://www.baeldung.com/ops/docker-local-images-minikube
    * might not work directly; simply use kubernetes manifest files instead
* Wire Docker registry with minikube: https://minikube.sigs.k8s.io/docs/handbook/registry/

kubectl
============================

* kubectl docs: https://kubernetes.io/docs/reference/kubectl/
* API reference: https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.24/
* Installation:
    * macOS: `❯ brew install kubernetes-cli`
    * Configure your shell first: `alias k=kubectl`
        * Enable ZSH plugin for autocompletion and many more aliases:
        * https://github.com/ohmyzsh/ohmyzsh/blob/master/plugins/kubectl/kubectl.plugin.zsh
        * Or whatever: https://kubernetes.io/docs/reference/kubectl/quick-reference/#kubectl-autocomplete

Commands
----------------------------------------------------

* Get command overview: `❯ k `
* Get help: `❯ k run --help` or `❯ k set image --help` (or short: `-h`)
* `❯ k api-resources` - names (casing!), versions (v1 or apps/v1?)
* Commands follow a common pattern: `❯ k <ACTION> <OBJECT> [IDENTIFIER] [OPTIONS]`
    * or: `❯ k [command] [TYPE] [NAME] -o <output_format>`
    * ACTION:
        * `get` - get a list of objects of the same type, or a single object by its ID
        * `describe` - get more details about a single object
        * `apply` - tell k8s to apply changes to the cluster (based on a given file); changing the desired state
        * `logs` - show stdout output; `k logs -f my-pod` (`-f` to follow stream live)
            * `exec` - execute a single command (possible logging into a remote shell)
                * Log in with a shell: `❯ k exec -it <pod-name> -- /bin/bash`
                * Or simply use `k9s` to enable port forwarding and conve niently work on your local machine :)
            * `port-forward` - to debug a pod, expose its port, make it locally available
        * `explain <type>` - print info for a specific object type (get types via `k api-resources` command)
            * drill down sub-elements: `❯ k explain deployment.spec` (gives Yaml structure info)
            * get them all: `❯ k explain deployment --recursive`
    * imperative style ACTIONs (don't do them! always use declarative YAML approach)
        * `delete` - remove an object from the cluster
        * `edit` - be presented a generated yaml file and edited to be applied immediately
        * `create` - create a new object (or `run` a single pod to create one)
        * `scale` - change number of pods for a replica set
            * `❯ k scale --replicas=3 rs/foo`
    * OBJECT: `pod[s]`, `nodes`, `deployments`, `services`, ...
    * IDENTIFIER: pod-name/deployment-name/service-name, ... also multiple possible
    * OPTIONS:
        * `-n namespace` - execute the command in a specific namespace
        * `-f filename` - pass a single (or multiple) file path; used in combination with `apply`
        * `-o <format>` - change the output format, e.g. to `yaml`, `wide`, `name`, `json`, `jsonpath`, `go-template`
        * `--dry-run=client` - this will not create the resource; instead it will tell you whether the resource can be
          created and if your command is right (use in combination with `-o yaml`)
* Tip: use shortnames such as (list them all via `❯ k api-resources`):
    * pods -> po
    * deployment -> deploy
    * service -> svc
    * namespace -> ns
    * configmap -> cm
    * networkpolicies -> netpol
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

Context Configuration
----------------------------------------------------

* the `~/.kube/config` Yaml contains: clusters (dev/prod), users (creds), contexts (cluster+user, optional namespace)
* list all contexts `❯ k config view`
* get current context name: `❯ k config current-context`
* change ns for current context permanently: `❯ k config set-context --current --namespace=dev`
    * `❯ k config set-context $(k config current-context) --namespace=dev`
    * to override in the config file: `❯ k config use-context foo@bar`
* change current cluster/ns:

```shell
kubectl config set-context dev --namespace=development \
  --cluster=some_cluster \
  --user=some_user
```

* change by specific config file location: `❯ k config --kubeconfig=/folder/my-config use-context my-context`
    * or change for your shell via: `export KUBECONFIG=/folder/my-config`

Basic Orientation
----------------------------------------------------

* List everything: `❯ k get all` (maybe filter for `--namespace=dev` or short `-n=dev`)
* List several with comma: `❯ k get deployments,svc`
* `❯ k cluster-info`
* List all available nodes in the cluster:

```shell
❯ k get nodes
NAME       STATUS   ROLES           AGE     VERSION
minikube   Ready    control-plane   3m12s   v1.34.0
```

* List all pods in all namespaces (`-A` for `--all-namespaces`):

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

* Get several object types at the same time: `❯ k get deploy,rs,po`
* Get several objects of the same time: `❯ k get po pod1 pod2` (or `describe` it)
* List labels of all pods (no need to describe): `❯ k get po --show-labels` (simply add a column, nice)
* Filter objects based on selectors: `❯ k get pods --selector foo=bar`
    * And properly count them: `❯ k get pods --selector foo=bar --no-headers | wc -l`
    * Multiple for all object type: `❯ k get all --selector foo=bar,baz=foo `
* Watch state with the `-w` suffix: `❯ k get pods -w`
* Get a bit more info (e.g. reveals node):

```shell
❯ k get pods -o wide
NAME                                       READY   STATUS    RESTARTS   AGE   IP            NODE       NOMINATED NODE   READINESS GATES
kaml-backend-deployment-7849768597-5r8wq   1/1     Running   0          43m   10.244.0.21   minikube   <none>           <none>
kaml-backend-deployment-7849768597-qthtd   1/1     Running   0          43m   10.244.0.20   minikube   <none>           <none>
```

* Get details of a specific pod:

```shell
❯ k describe pod mypodname
```

* Am I even able (allowed) to do so? `❯ k auth can-i create pods` => "yes"

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
    * don't just get another pod's template via `describe -o yaml` as it would be too "polluted"
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

Imperatively Modify the Cluster
============================

General options:

* Specify labels: `--labels="app=foo,env=prod"`
* Instead of directly create an artifact, create the yaml instead:
  `❯ k run nginx --image=nginx --dry-run=client -o yaml > nginx.yaml`
* Reverse engineer an existing one: `❯ k get pod <pod-name> -o yaml > pod.yaml`
* Imperative, generic edit (apply on save): `❯ k edit deployment/nginx-deployment`

Pod
----------------------------------------

* Create a static pod (usually we don't do that!):

```shell
❯ k run mypodname --image=nginx --port=80
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

* And run it:

```shell
❯ k create -f pod.yaml
pod/my-pod created
```

* Create a pod and immediately expose it: `❯ k run my-pod --image=httpd:alpine --port=80 --expose=true`
* Nuke them all: `❯ k delete pod --all`

Pod Hard Change
--------------------------------------------

* you CANNOT edit specifications of an existing POD other than:
    * spec.containers[*].image
    * spec.initContainers[*].image
    * spec.activeDeadlineSeconds
    * spec.tolerations
* either do via deployment; he will destroy&recreate accordingly
    * `❯ k edit deployment my-deployment`
* try with `k edit pod new-pod`, it deny and save tmp file
    * `❯ k delete pod old-pod`
    * `❯ k create -f /tmp/kubectl-edit-ccvrq.yaml`
* extract existing: `❯ k get pod webapp -o yaml > my-new-pod.yaml`
    * make changes to yaml file
    * `❯ k delete pod old-pod`
    * `❯ k create -f my-new-pod.yaml`
* or simply override it forcefully: `❯ k replace --force -f new-pod.yaml` (delete and recreate)

Deployments
----------------------------------------

* Create a static deployment: `❯ k create deployment my-deploy --image=nginx --replicas=4`
* Change its replica count: `❯ k scale deployment my-deploy --replicas=6`

Service
----------------------------------------

* Create a ClusterIP service (preferred):
  `❯ k expose pod redis --port=6379 --name redis-service --dry-run=client -o yaml`
    * Or: `❯ k create service clusterip redis --tcp=6379:6379 --dry-run=client -o yaml`
* Create a NodePort service (preferred):
  `❯ k expose pod nginx --port=80 --name nginx-service --type=NodePort --dry-run=client -o yaml`
    * Or (if need/want specify node port):
      `❯ k create service nodeport nginx --tcp=80:80 --node-port=30080 --dry-run=client -o yaml`
* Watch out with these commands and their preconfigured selector labels!
* Help yourself: `❯ k create service clusterip --help`

ConfigMap & Secret
----------------------------------------

* Create a ConfigMap: `❯ k create cm my-configmap --from-literal=FOO=bar`
* Create a Secret: `❯ k create secret generic my-secret --from-literal=PASS=secr3t`
    * usually we want to create a "generic" one, not "tls" or "docker-registry" (see `❯ k create secret --help`)
    * generate a (hashed) secret: `❯ echo -n "my_secret" | base64`
    * and decode again: `❯ echo -n "bxlzcWw=' | base64 --decode`
* or use `--from-file=data.properties` or `--from-file=ssh-privatekey=path/to/id_rsa`

Misc
----------------------------------------

* Add a label to a node: `❯ k label nodes my-noad key=val`

Other
============================

k9s
----------------------------------------

* CLI based management tool
* Install `❯ brew install derailed/k9s/k9s`
* Run `❯ k9s`

kubectx
----------------------------------------

* fast switching of contexts (clusters) and namespace
* https://github.com/ahmetb/kubectx
* Install `❯ brew install kubectx`
* Run `❯ k9s`

Docker
========================================

General:

* List all running containers: `❯ docker ps`
* List all images: `❯ docker image ls`

Custom Images:

* Build a tagged image (`Dockerfile` in the CWD): `❯ docker build -t group/id:version .`
* Push it: `❯ docker push group/id:version`
* Analyze: `❯ docker history group/id:version` (see MB size for each layer)
* Commands
    * `CMD ["exe", "arg1"]` is command + args
    * `ENTRYPOINT["exe"]` only the command; args when running this option
    * or both: `ENTRYPOINT` and `CMD` (for default arguments)
    * ultimately can also override `--entrypoint exe2`

Run a container:

* https://docs.docker.com/reference/cli/docker/container/run/
    * generally: `docker run [OPTIONS] IMAGE [COMMAND] [ARG...]`
* run: `docker run --name my-container my-image`
    * add `-d` for detached running
* stop: `docker stop my-container`
* remove: `docker rm my-container`
* port mapping: `-p 8282:8080` (specified as "host:container")

Misc:

* Get the base OS for an image: `❯ docker run <image-name> cat /etc/*release*`

Docker and k8s
----------

* how to set docker's CMD/ENTRYPOINT from a pod container?
* watch out: k8s calls the binary to execute "command" and the arguments passed "args"
    * in docker the execute is "ENTRYPOINT" and the args are "CMD" (confusing, right?!)

```yaml
spec.containers:
  - image: nginx
    name: my-nginx-container
    # overrides Dockerfile CMD
    args: [ "arg1" ]
    # overrides Dockerfile ENTRYPOINT
    command: [ "exe2" ]
    or non-inline list yaml syntax:
    command:
      - "foo"
      - "42"
    # => entries MUST be in double-quotes (even numbers!)
```

* or pass it through when running it:
    * `❯ k run nginx --image=nginx -- arg1 arg2`
    * `❯ k run nginx --image=nginx --command -- cmd arg1 arg2`

kustomize
========================================

* Install `❯ brew install kustomize`
* Yamls...

oc
========================================

* Install `❯ brew install openshift-cli`
* ... do basically the same as with kubectl ...
* Simply: `❯ oc get pods`
