Basics
============================

What is it?

* Red Hat's opensource container application platform
    * Not IaaS (infra), but PaaS (platform), not SaaS (software-as-a-service)
* Combination of:
    * Containerization, images (CRI/Docker)
    * Kubernetes (Google): orchestration of deployments/services/etc. (scaling, healing, rollouts, ...)
        * alternatives: Docker Swarm (overly simple), or Mesos (Apache; complex, difficult)
    * OpenShift add-ons/tooling: abstracting k8s, Git(Hub) integration, build pipelines, image registry, networking,
      access mgmt (projects/teams/users)
* Why containers in the first place?
    * To avoid "The Matrix from Hell" (dependency hell); compatibility
    * easy local setup (development)
    * env-support (dev/test/prod)

Basic concepts:

* secure environment with RBAC
* projects, users, groups
* builds, deployments (push applications: GIT / manifest)
* networking, services, routes
* storage, templates (dynamic provisioning, persistent volumes)
* security

Installation
-----------------------------

* Either install a local setup (followed here), or use the 30-day limited free sandbox from redhat
* Register a free redhat account first
* Setup a local single node environment with CRC (like Docker desktop or minikube)
    * http://console.redhat.com/openshift
    * "Cluster List" navigation item / "Create cluster" button / "Local" tab
* The following is a summary of the help instructions: https://crc.dev/docs/using/
* WATCH OUT: the openshift trial is NOT FOR FREE!

Execute code-ready-containers commands (using hyperv in its background; takes time to download/install):

```shell
$ crc delete # Remove previous cluster (if present)
$ crc config set preset openshift # or "okd" (community) or "microshift" (minimal version; without UI)
$ crc setup # Initialize environment; see  ~/.crc
$ crc start # will print web-console URL and credentials for kubeadmin and developer
...
$ crc stop
```

* Use OpoenShift CLI `oc` (mimics `kubectl`o) or use it from the web-console (click icon in the upper right corner)
    * Or execute `crc console` to open a browser with the correct URL for the web-console
    * Users: kubeadmin (for admin) or developer (for project setup)
    * password printed in the output of the crc start command; or run `crc console --credentials`
* Configure `oc`: `eval $(crc oc-env)`
    * Then: `oc login -u developer https://api.crc.testing:6443`
* internal container image registry available
    * login: `oc registry login --insecure=true`
* create a new project: `oc new-project demo`
    * mirror an iamge:
      `oc image mirror registry.access.redhat.com/ubi8/ubi:latest=default-route-openshift-image-registry.apps-crc.testing/demo/ubi8:latest --insecure=true --filter-by-os=linux/amd64`
    * verify: `oc get is`
    * enable lookup: `oc set image-lookup ubi8` (avoid full URL)
    * use the image for a new pod: `oc run demo --image=ubi8 --command -- sleep 600s`

Setup a project (a project is openshift's way of saying "kubernetes namespace") with odo:

* login: `odo login -u developer -p developer`
* create it: `odo project create sample-app`
* `git clone https://github.com/openshift/nodejs-ex` and cd into it
* create component `odo create nodejs` expose it `odo url create --port 8080` and `odo push`
* view the URL: `odo url list`

Orientation
-----------------------------

* logging in as admin gives different sections than a regular developer
* feed it with standard k8s yaml (or even json); make use of the informative sidebar

Setup
-----------------------------

* download `oc` from: https://console.redhat.com/openshift/downloads
* login instructions:
    * in web-ui, click on your user in the top right
    * click "Copy login command"
    * select the desired environment
    * click display token, copy and execute the login command for oc (token, server)
* basic setup:
    * create a new project: `oc new-project my-project`
    * create a new application: `oc new-app my-app` (added to the current/new project)
    * or use good old native k8s:
      `kubectl create deployment my-deployment --image=registry.k8s.io/e2e-test-images/agnhost:2.43 -- /agnhost serve-hostname`
