## Spring Boot + Docker + AWS 배포


아래 같이 프로젝트 하위에 Dockerfile 이라는 파일을 생성<br>

![docker1](https://github.com/adbackend/rrs/assets/94349690/2ef1228f-aa7f-4796-86c6-49e1dfea6120)



    FROM amazoncorretto:17
    WORKDIR /app
    COPY build/libs/review-0.0.1-SNAPSHOT.jar /app/review.jar
    COPY entrypoint.sh /app/entrypoint.sh
    RUN chmod +x /app/entrypoint.sh
    ENTRYPOINT ["/app/entrypoint.sh"]


<b>인스트럭션</b>

 * FROM : 모든 이미지는 다른 이미지로부터 출발한다. 베이스 이미지를 지정. <BR> amazoncorretto(JDK) 이미지를 사용
 * WORKDIR : 컨테이너 이미지 파일 시스템에 디렉터리를 만들고 해당 디렉터리를 작업 디렉터리로 지정하는 인스트럭션
 * COPY : 로컬 파일 시스템 파일 혹은 디렉터리를 컨테이너 이미지 파일 시스템에 복사하는 인스트럭션<BR> `COPY {원본경로} {복사경로}` 형식으로 사용
 * RUN : 컨테이너에서 command(명령어)를 실행(run)하여 새 이미지에 포함시키는 역할
 * ENTRYPOINT : 컨테이너 시작시 실행될 command(명령어)를 지정

<br>

`$ docker login` : 도커 로그인

`$ docker build -t review-api ./` : -t(또는 --tag) 옵션은 새로 생성할 이미지 이름을 지정.  마지막 (./)은 Dockerfile 위치 경로를 지정

`$ docker images` : 사용가능한 이미지 목록을 확인

 아래와 같이 `review-api`가 생성된 것을 볼 수 있다

![docker2](https://github.com/adbackend/rrs/assets/94349690/187dcf9e-e012-4e6f-b2c9-488f129c8449)

`$ docker run -it -p8080:8080 review-api`


<br>
<b># 도커 컴포즈</b><br>

`도커 컴포즈`는 단일 서버에서 여러개의 컨테이너를 하나의 서비스로 정의해 컨테이너의 묶음으로 관리할 수 있는 작업 환경을 제공하는 관리 도구이다.<br>
여러개의 컨테이너가 하나의 어플리케이션으로 동작할때 도커 컴포즈를 사용하지 않는다면, 이를 테스트하라면 각 컨테이너를 하나씩 생성해야 한다.<br>
예를 들면, 웹 어플리케이션을 테스트 하려면 웹 서버 컨테이너, 데이터베이스 컨테이너 두개의 컨테이너를 생성해야 한다.


![docker3](https://github.com/adbackend/rrs/assets/94349690/3a07af53-9b42-4847-a875-7e4178c51366)

```
version: "3.8"

services:
  review-api:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - 8080:8080
```

`$ docker-compose -v` : 도커 컴포즈 버전 확인 <br>
`$ docker-compose up --build` : 도커 이미지 빌드후 compose up


