Basics
====

* object: { all | pods services | deployments | events | secrets | configmaps | ingress}
* `kubectl [get|describe|delete] <object> [<name>]`
* `kubectl options` to print possible ones
    * e.g.: `--output=wide` (or `-o wide`), `--all-namespaces`
* `kubectl get pods --help` (or any other command)
* `kubectl version`

Pod Management
====

* `kubectl get pods -o wide` (also see the node)
* `kubectl [describe|delete|logs] pod <pod-name>`
* `kubectl exec -it <pod-name> -- bash`

Config
====

* `kubectl config view`
* `kubectl config current-context`
* `kubectl config use-context <context-name>`
* `kubectl config set-context --current --namespace=<namespace>`

Descriptors
====

* `kubectl apply -f <file>`
* `kubectl apply -k <kustomize-file>`

Advanced Queries
====

* `kubectl get pod <pod-id> -o jsonpath='{.spec.containers[0].imagePullPolicy}'`

Local
====

* ErrImagePull: `eval $(minikube docker-env)`; docker build...; verify: `minikube image ls`

Debug
====

* If pod can't reach a service:
    * `kubectl exec -it my-pod -- sh`
    * `nc -v -z -w 2 my-service 80`
