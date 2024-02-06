# Pre requisits: <br>
Docker. <br>
minikube. <br>
kubernetes-cli also now as kubectl. <br>
<br>

# Steps to run this project: <br>
1° Clone this repo <br>
2° Run `minikube start` <br>
3° Run: <br>
```
kubectl create ns study
kubectl apply -f manifests/service.yaml -n study
kubectl apply -f manifests/serviceaccount.yaml -n study
kubectl apply -f manifests/rolebinding.yaml -n study
kubectl apply -f manifests/role.yaml -n study
kubectl apply -f manifests/cm.yaml -n study
kubectl apply -f manifests/deployment.yaml -n study
```

# If you want to modify the project
1° Modify the desired files <br>
2° Run: <br>
```
docker build . -t <image-name>:<image-version>
docker push <image-name>:<image-version>
```
3° Modify the deployment.yaml in the line 22 to your image <br>
4° Run the steps in previous section <br>
