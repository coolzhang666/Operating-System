#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<wait.h>
int main()
{
    pid_t pid1;
    pid_t pid2;
    system("ps -f");
    pid1 = fork();
    if (pid1 == -1)
    {
	    printf("fork fail!\n");
    }
    else if(pid1 == 0)
	{
	    printf("I am child: PID: %d PPID: %d\n", getpid(), getppid());
        system("ps -f");
        exit(0);
	}
    else
    {
	    printf("I am parent: PID: %d PPID: %d\n", getpid(), getppid());
        pid2 = fork();
        if(pid2 < 0)
        {
                printf("fork fail!\n");
        }
        if(pid2 == 0)
        {
            printf("I am child: PID: %d PPID: %d\n", getpid(), getppid());
            system("ps -f");
            exit(0);
        }
        wait(0);
        wait(0);
    }
	return 0;
}
