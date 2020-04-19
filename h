
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  165;
client_header_timeout 45;

client_body_timeout 45;

send_timeout 45;

    #gzip  on;

    server {
        listen       8090;
        server_name  39.106.212.32;
	client_max_body_size 10M;
        #charset koi8-r;

        #access_log  logs/host.access.log  main;
        location / {
  root /root/jm/dist;
  index index.html;
}


        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }


    }
server {
        listen 80;
        server_name www.yjlive.cn;

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
            root html;
        }

		location / {
            alias /root/jm/h5/;
            index index.html;
        }

        location /h5 {
            alias  /root/jm/mall/;
			try_files $uri $uri/ @router;
			index index.html;
        }
         location /cms {
                    alias  /root/jm/cms/;
        			try_files $uri $uri/ @router;
        			index index.html;
                }
                 location /pc {
                            alias  /root/jm/pc/;
                			try_files $uri $uri/ @router;
                			index index.html;
                        }
                           location /pcbak {
                                                    alias  /root/jm/pcbak/;
                                        			try_files $uri $uri/ @router;
                                        			index index.html;
                                                }
                         location /h5bak {
                                    alias  /root/jm/h5bak/;
                        			try_files $uri $uri/ @router;
                        			index index.html;
                                }

                                location /mei {
                                                                    alias  /root/jm/mei/;
                                                        			try_files $uri $uri/ @router;
                                                        			index index.html;
                                                                }
                                                                 location /mei1 {
                                                                                                                                    alias  /root/jm/mei1/;
                                                                                                                        			try_files $uri $uri/ @router;
                                                                                                                        			index index.html;
                                                                                                                                }
        location /api {
            rewrite  ^/api/(.*)$ /$1 break;
            include  uwsgi_params;
            proxy_pass   http://39.106.212.32:8081 ;
        }
        location @router {
        rewrite ^.*$ /index.html last;
        }
    }



 }
