# walkTogether
9/9 jenkins 설정 webhook 설정, //todo docker hub 로 빌드 테스트

# Jenkins 의 clean 이 안될 때
jenkins 에서 gradle 로 빌드 할 때, clean 이 jenkins 의 빌드 파일을 삭제하기 때문에 /var/jenkins_home/workspace/walkTogether/build 의 권한을 필요로 한다.
docker exec -u 0 -it jenkins /bin/bash 로 Docker 컨테이너의 루트 권한 Bash 로 접근 한 뒤 
chown -R jenkins:jenkins /var/jenkins_home/workspace/walkTogether/build jenkins 에게 삭제 권한 부여
해당 컨테이너 내의 폴더와 host 서버의 폴더를 마운트를 해놓으면 삭제가 안되니 이점 유의!


