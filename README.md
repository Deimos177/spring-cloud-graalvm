# spring-cloud-graalvm
This is a POC for using graalvm with spring cloud to deploy AOT spring apps on kubernetes cluster
More infos with be added in future.

# prerequisites
Java jdk21
mvn 3.9.6 or above
docker
minikube or another k8s local engine

# TO run this project build for locally
1° Install grallvm following this link: https://www.graalvm.org/latest/docs/getting-started/linux/
2° Enter the project folder
3° Run this command: JAVA_HOME=/path/to/graalvm mvn -Pnative native:compile
4° Run command: docker build . -f Dockerfile -t your-tage-name:your-version
5° Push image to your container registry ex. hub.docker
6° Change deployment image tag in manifests folder to your image
7° Apply manifests in this order: cm.yaml, deploy.yaml and finally svc.yaml

# Short version with my image
1° Enter the project folder
2° Apply manifests in this order: cm.yaml, deploy.yaml and finally svc.yaml
