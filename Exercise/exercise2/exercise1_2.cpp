#include<stdio.h>
#include<stdlib.h>
#include<fcntl.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<sys/wait.h>
#include<string.h>

int lock(int fid)
{
    lseek(fid, 0,SEEK_SET);
    if(lockf(fid,F_LOCK,0)==-1)
    {
        printf("lock error\n");
        exit(1);
    }
    return 0;
}

int unlock(int fid)
{
    lseek(fid, 0,SEEK_SET);
    if(lockf(fid,F_ULOCK,0)==-1)
    {
        printf("unlock error\n");
        exit(1);
    }
    return 0;
}

int main()
{
    int fid;
    int pid1, pid2, i;
    char str[50] = {0};

    // 打开文件
    fid = open("exercise1.txt", O_RDWR|O_APPEND, 0777);
    if (fid < 0)
    {
        printf("open file error/n");
        exit(1);
    }
    else
    {
        write(fid, "*********\n", 10);
        pid1 = fork(); // 创建子进程1
        if(pid1 == -1)
        {
            printf("子线程1创建失败！\n");
            exit(1);
        }
        else if(pid1 == 0)
        {
            // 子进程1写数据
            lock(fid); // 加锁
            for(i = 0; i < 3; i++)
            {
                printf("daughter are writting %d\n", i);
                sprintf(str, "daughter are writting %d\n", i);
                write(fid, str, strlen(str));
                sleep(1);
            }
            unlock(fid); // 解锁
            exit(0);
        }
        else
        {
            pid2 = fork(); // 创建子进程2
            if(pid2 == -1)
            {
                printf("子进程2创建失败!\n");
                exit(1);
            }
            else if(pid2 == 0)
            {
                // 子进程2写数据
                lock(fid);
                for(i = 0; i < 3; i++)
                {
                    printf("son are writting %d\n", i);
                    sprintf(str, "son are writting %d\n", i);
                    write(fid, str, strlen(str));
                    sleep(1);
                }
                unlock(fid);
                exit(0);
            }else
            {
                // 父进程写数据
                lock(fid);
                for(i = 0; i < 3; i++)
                {
                    printf("parent are writting %d\n", i);
                    sprintf(str, "parent are writting %d\n", i);
                    write(fid, str, strlen(str));
                    sleep(1);
                }
                unlock(fid);
                wait(NULL);
                wait(NULL);
            }
        }
    }
    close(fid);
    return 0;
}