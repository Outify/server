#!/bin/bash

echo "============ Start Server ============"
docker stop outify-server || true
docker rm outify-server || true
docker pull 058264467328.dkr.ecr.ap-northeast-2.amazonaws.com/outify-server:latest
docker run -d --name outify-server -p 8080:8080 058264467328.dkr.ecr.ap-northeast-2.amazonaws.com/outify-server:latest
echo "=========== End Deployment ==========="