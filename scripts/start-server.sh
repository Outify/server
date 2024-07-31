sudo chmod u+w /home/ubuntu/server

echo "============ Start Server ============"
cd /home/ubuntu/server
sudo fuser -k -n tcp 8080 || true
nohup java -jar outify.jar > ./output.log 2>&1 &
echo "=========== End Deployment ==========="