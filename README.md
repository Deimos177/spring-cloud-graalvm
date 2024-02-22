# Pre requisits: <br>
Docker. <br>
minikube. <br>
kubernetes-cli also now as kubectl. <br>
Helm <br>
<br>

# Steps to run this project: <br>
1° Clone this repo <br>
2° Run `minikube start` <br>
3° Run: <br>
```
kubectl create ns study
kubectl create ns mysql
kubectl apply -f manifests/mysql.yaml -n mysql
helm upgrade --install -f .\chart\values.yaml toolkit-sample .\chart\ -n study --atomic --debug --wait --timeout 5m
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
