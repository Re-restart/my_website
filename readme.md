## 更详细的教程可以看我写的csdn博客：https://blog.csdn.net/weixin_53318879/article/details/147977004?spm=1001.2014.3001.5502
## 欢迎访问我的个人博客网站：http://www.turnin-blog.online/


## 个人博客网站的部署
本博客网站在本地实验完，需要部署到服务器，外面才能访问的到。我这里使用了腾讯云的服务器，买个最便宜的就可以，系统我这里安装的是ubuntu 24.04。

### 部署方式一：docker容器化部署
需要把demo文件夹用ftp方式传输到云服务器上，为此本机需要下载filezilla。然后转到服务器平台上，在服务器平台安装docker后

./mvnw clean package 
sudo docker build -t my_website .  
docker run -d -p 8080:8080 --name java_web my_website

### 部署方式二：直接部署
在demo文件夹下

./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar

## 输入域名/公网ip即可访问网站

### 方式一：直接改端口

server.port=80
spring.jpa.properties.hibernate.retry_attempts=3
spring.jpa.properties.hibernate.retry_interval=1000

记得要maven重新构建并部署网站

### 方式二：安装nginx并改配置文件
安装nginx并改/etc/nginx/sites-available/default文件

```
server {
    listen 80;
    server_name yourdomain.com;
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
    location ~* \.(css|js|jpg|jpeg|png|gif|ico|woff|ttf|svg)$ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

检查配置并重启nginx

sudo nginx -t   
sudo systemctl restart nginx

