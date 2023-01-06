kubectl apply -f secrets/docker-hub.yml -f secrets/postgresql-1-secret.yml -f postgres.yml
kubectl apply -f ingress.yml
kubectl apply -f main-db-configmap.yml
./deployService.sh "upgrade --install" config-service
./deployService.sh "upgrade --install" gateway-service
./deployService.sh "upgrade --install" bonus-service
./deployService.sh "upgrade --install" flight-service
./deployService.sh "upgrade --install" ticket-service
./deployService.sh "upgrade --install" user-service

kubectl get services -o wide
