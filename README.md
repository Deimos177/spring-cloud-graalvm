# spring-cloud-graalvm
This is a POC for using graalvm with spring cloud to deploy AOT spring apps on kubernetes cluster
More infos with be added in future. <br>

# prerequisites
Java jdk21 or above <br>
mvn 3.9.6 or above <br>
docker <br>
minikube or another k8s local engine <br>

# TO run this project build for locally
1° Install grallvm following this link: https://www.graalvm.org/latest/docs/getting-started/linux/ <br>
2° Enter the project folder <br>
3° Run this command: JAVA_HOME=/path/to/graalvm mvn -Pnative native:compile <br>
4° Run command: docker build . -f Dockerfile -t your-tage-name:your-version <br>
5° Push image to your container registry ex. hub.docker <br>
6° Change deployment image tag in manifests folder to your image <br>
7° Apply manifests in this order: cm.yaml, deploy.yaml and finally svc.yaml <br>

# Short version with my image
1° Enter the project folder <br>
2° Apply manifests in this order: cm.yaml, deploy.yaml and finally svc.yaml <br>
