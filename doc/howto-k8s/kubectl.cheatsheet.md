Basics
====

* object: { all | pods services | deployments | events | secrets | configmaps | ingress}
* `kubectl get <object> [<name>]`
* `kubectl describe <object> [<name>]`
* `kubectl delete <object> <name>`
* options: `--output=wide` (or `-o wide`), `--all-namespaces`
* `kubectl version`

Pod Management
====

* `kubectl get pods`
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
