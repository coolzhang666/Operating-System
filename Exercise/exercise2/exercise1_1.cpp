#include<stdio.h>
#include<stdlib.h>
#include<fcntl.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<sys/wait.h>
#include<string.h>

int main()
{
    int fid;
    int pid1, pid2, i;
    char str[50] = {0};

    // 打开文件
    fid = open("exercise1.txt", O_WRONLY|O_APPEND, 0777);
    if ( fid < 0)
    {
        printf("open file error/n");
        exit(1);
    }
    else
    {
        write(fid, "*********\n", 10);
        while((pid1 = fork()) == -1);  // 创建子进程1
        if(pid1 == 0)
        {
            // 子线程1写数据
            lseek(fid,0,SEEK_END);
            for(i = 0; i < 3; i++)
            {
                printf("daughter are writting %d\n", i);
                sprintf(str, "daughter are writting %d\n", i);
                write(fid, str, strlen(str));
                sleep(1);
            }
        }
        else
        {
            while((pid2 = fork()) == -1); // 创建子线程2
            if(pid2 == 0)
            {
                // 子线程2写数据
                lseek(fid,0,SEEK_END);
                for(i = 0; i < 3; i++)
                {
                    printf("son are writting %d\n", i);
                    sprintf(str, "son are writting %d\n", i);
                    write(fid, str, strlen(str));
                    sleep(1);
                }
            }
            else
            {
                // 父线程写数据
                lseek(fid,0,SEEK_END);
                for(i = 0; i < 3; i++)
                {
                    printf("parent are writting %d\n", i);
                    sprintf(str, "parent are writting %d\n", i);
                    write(fid, str, strlen(str));
                    sleep(1);
                }
                wait(NULL);
                wait(NULL);
            }
        }
    }
    close(fid);
    return 0;
}