sudo chmod u+w /home/ubuntu/server

sudo chmod +x /home/ubuntu/server/scripts/start-server.sh

vim /home/ubuntu/server/scripts/start-server.sh

echo "============ Start Server ============"
cd /home/ubuntu/server
sudo fuser -k -n tcp 8080 || true
nohup java -jar outify.jar > ./output.log 2>&1 &
echo "=========== End Deployment ==========="

sudo chown ubuntu:ubuntu /home/ubuntu/server/output.log