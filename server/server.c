#include <sys/types.h>        
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdio.h>

#define BACKLOG_SIZE 5
#define BUFFER_SIZE 4096
#define PORT 8888

void parse_command(const char* raw);

int main()
{
   int listenfd = 0, connfd = 0, ret;
   struct sockaddr_in serv_addr;
   
   char buf[BUFFER_SIZE];
   
   memset(&buf, '0', sizeof(buf));
   listenfd = socket(AF_INET, SOCK_STREAM, 0);
   
   serv_addr.sin_family = AF_INET;
   serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
   serv_addr.sin_port = htons(PORT);
   
   bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr));
  
   listen(listenfd, BACKLOG_SIZE);
   printf("Listening on port %d\n", PORT);
   connfd = accept(listenfd, (struct sockaddr*)NULL, NULL);
   
   printf("Reading from client\n");
   
   while ((ret = read(connfd, buf, BUFFER_SIZE)) > 0 )
   {
      if(ret > 0 && ret < BUFFER_SIZE)  
	 buf[ret] = 0;
      else
	 buf[0] = 0;
      parse_command(buf);
   }
   return 0;
}

void parse_command(const char* raw)
{
   printf("%s\n", raw);
}
