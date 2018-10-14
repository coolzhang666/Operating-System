#include<stdio.h>
#include<stdlib.h>
#include<fcntl.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<string.h>

int lock(int fid)
{
    lseek(fid, SEEK_SET, 0);
    if(lockf(fid,F_LOCK,0)==-1)
    {
        printf("lock error\n");
        exit(1);
    }
    return 0;
}

int unlock(int fid)
{
    lseek(fid, SEEK_SET, 0);
    if(lockf(fid,F_ULOCK,0)==-1)
    {
        printf("unlock error\n");
        exit(1);
    }
    return 0;
}

int main()
{
    int fid, fd[2];
    pid_t pid;
    fid = open("exercise2.txt", O_RDWR, 0777); // 打开文件
    if(fid == -1)
    {
        printf("open error\n");
        exit(1);
    }
    while(1)
    {
        if(pipe(fd) == -1) // 创建管道
        {
            printf("create pipe error\n");
            exit(1);
        }

        pid = fork(); // 创建子进程
        if(pid == -1)
        {
            printf("fork error\n");
            exit(1);
        }
        else if(pid == 0)
        {
            int index, i, len, index_f;
            char buf[100], bufout[100];

            close(fd[1]);  // 关闭管道写端口
            read(fd[0], buf, sizeof(buf));  // 读取管道中的数据
            index_f = atoi(buf);  // 讲读取到的整形字符串装换为int类型

            // 子进程进行读操作
            lock(fid); // 对文件上锁
            lseek(fid, -index_f, SEEK_END); // 移动文件指针到上一次写入末尾
            index = read(fid, bufout, 100); // 读取文件内容并记录读取的长度
            if(index != -1)
            {
                for(i = 0; i < index; i++)
                {
                    printf("%c", bufout[i]); // 输出读取到的字符
                }
                printf("\n");
            }
            else
            {
                printf("read error\n");
                exit(1);
            }
            unlock(fid); // 解锁文件
            exit(0);
        }
        else
        {
            int index;
            char bufin[100], buf[100];

            close(fd[0]); // 关闭读端口
            // 父进程进行写操作
            lock(fid); // 对文件上锁
            fflush(stdin);  // 清空键盘缓冲区
            printf("input something:");
            // scanf("%s", bufin);
            fgets(bufin, sizeof(bufin), stdin);
            
            lseek(fid, 0, SEEK_END); // 移动文件指针到文件末尾
            index = write(fid, bufin, strlen(bufin)); // 向文件中写入数据并记录写入数据的长度

            sprintf(buf, "%d", index); // 将写入数据的长度index转换为字符串以便传送给子进程
            write(fd[1], buf, strlen(buf)); //将buf写入通道
            if(index == -1)
            {
                printf("write error\n");
                unlock(fid); // 解锁文件
            }
            else
            {
                unlock(fid); // 解锁文件
                sleep(1);
            }
            wait(NULL); // 等待子进程结束
        }
    }
    close(fid); // 关闭文件
    return 0;
}