#include<stdio.h>                                                                                                                  
#include<unistd.h>
#include<stdlib.h>
#include<wait.h>

int main()
{
    while(1)
    {
        char buf[100] = {0};
        printf("输入命令：");
        fflush(stdin);
        scanf("%s",buf);
        pid_t pid = fork();
        if (pid == -1)
        {
            printf("子进程创建失败");
        }
        else if (pid == 0)
        {
            system(buf);
            exit(0);
        }
        else
        {
            wait(NULL);
        }
    }
    return 0;
}