Basics
====

* object: { all | pods services | deployments | events | secrets | configmaps | ingress}
* `kubectl get <object> [<name>]`
* `kubectl describe <object> [<name>]`
* `kubectl delete <object> <name>`
* `kubectl options` to print possible ones
    * e.g.: `--output=wide` (or `-o wide`), `--all-namespaces`
* `kubectl get pods --help` (or any other command)
* `kubectl version`

Pod Management
====

* `kubectl get pods -o wide` (also see the node)
* `kubectl describe pod <pod-name>`
* `kubectl delete pod <pod-name>`
* `kubectl logs <pod-name>`
* `kubectl exec -it <pod-name> -- bash`

Config
====

* `kubectl config view`
* `kubectl config current-context`
* `kubectl config use-context <context-name>`
* `kubectl config set-context --current --namespace=<namespace>`

Setup Service
====

* `kubectl create deployment my-kube --image=group/artifact:version.10`
* `kubectl get deployments`
* `kubectl expose deployment my-kube --type=LoadBalancer --port=8080`
* `kubectl get services`

Descriptors
====

* `kubectl apply -f <file>`
* `kubectl apply -k <kustomize-file>`
