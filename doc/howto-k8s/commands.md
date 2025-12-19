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
====

* Install `❯ brew install kubernetes-cli`
* Configure your shell first: `alias k=kubectl`
    * Enable ZSH plugin for autocompletion and many more aliases:
    * https://github.com/ohmyzsh/ohmyzsh/blob/master/plugins/kubectl/kubectl.plugin.zsh
* Commands follow a common pattern: `k <ACTION> <OBJECT> [IDENTIFIER] [OPTIONS]`
    * ACTION: get, describe, delete, apply, edit, logs, exec, port-forward, ...
    * OBJECT: pods, nodes, deployments, services, ...
    * IDENTIFIER: pod/deployment/service name, ...
    * OPTIONS: -n namespace, -f filename, --dry-run, ...

Basic Orientation
----

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
----

* Create a static pod (usually we don't do that!):

```shell
❯ k run mypodname --image=nginx
pod/mypodname created
```

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

k9s
====

* Install `❯ brew install derailed/k9s/k9s`
* Run `❯ k9s`

kustomize
====

* Install `❯ brew install kustomize`
* Yamls...

oc
====

* Install `❯ brew install openshift-cli`
* ... do basically the same as with kubectl ...
* Simply: `❯ oc get pods`
