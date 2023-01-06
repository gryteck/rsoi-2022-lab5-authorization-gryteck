COMMAND=$1
SERVICE=$2
echo "Deploying $SERVICE..."
helm $COMMAND $SERVICE ./helm-service -f helm-service/values/$SERVICE-values.yml