#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <unistd.h>
#include <libgen.h>
#include <fcntl.h>

int main(int argc, char *argv[]){
    struct stat stbuf;
    char path[1024];
    char result[1024];
    if (argc < 2 || argc > 3){
        fprintf(stderr,"Usage:\nohtuv from to,\nohtuv restore from and\nohtuv init\n");
        return -1;
    }
    if (argc == 2){
        if (init(argv[1],stbuf) == -1)
            return -1;
    }
    if (strncmp(argv[1],"save",4) == 0 || strncmp(argv[1],"restore",7) == 0){
        FILE *f = fopen(argv[2], "r");
        int fd = fileno(f);
        sprintf(path, "/proc/self/fd/%d", fd);
        memset(result, 0, sizeof(result));
        readlink(path, result, sizeof(result)-1);
        fclose(f);
        if (exec(".",argv[1],argv[2],result) == -1)
            return -1;
    } else {
        fprintf(stderr,"Usage:\nohtuv from to,\nohtuv restore from and\nohtuv init\n");
        return -1;
    }
    return 0;
}

int init(char *command,struct stat stbuf) {
    if (strncmp(command,"init",4) != 0){
        fprintf(stderr,"did you mean: init?\n");
        return -1;
    }
    if (stat(".ohtuv",&stbuf) == -1)
        mkdir(".ohtuv",0700);
    else {
        fprintf(stderr,"This project has already been initialized\n");
        return -1;
    }
    return 1;
}

#define MAX_PATH 2048

int exec(char *dir,char *command, char *file,char *result){
    char buff[MAX_PATH];
    char path[MAX_PATH];
    memset(buff, 0, sizeof(buff));
    struct dirent *dp;
    DIR *dfd;
    int root = 1;

    if ((dfd = opendir(dir)) == NULL) {
        fprintf(stderr,"can´t open %s\n",dir);
        return -1;
    }
    sprintf(path,"/proc/self/fd/%d",dirfd(dfd));
    if(readlink(&path,&buff,sizeof(buff)-1) ==-1 ){
        fprintf(stderr,"readlink error\n");
        closedir(dfd);
        return -1;
    }
    while ((dp = readdir(dfd)) != NULL){
        if (strncmp(dp->d_name,"..",2) ==0)
            root = 0;

        if (strncmp(dp->d_name,".ohtuv",6) == 0){
            if (strncmp(command,"save",4) == 0){
                sprintf(path,"%s/.ohtuv/%d-%s",buff,(unsigned)time(NULL),file);
                save(path,result);
                return 1;
            } else {
                sprintf(path,"%s/.ohtuv/",buff);
                restore(path,result);
                return 1;
            }
            closedir(dfd);
            return 1;
        }
    }
    if (root == 1){
        fprintf(stderr,"could not find .ohtuv\n");
        closedir(dfd);
        return -1;
    }
    return exec(dirname(buff),command,file,result);
}

#define PERMS 0666
int save(char *name,char *result){
    int f1, f2, n;
    char buf[1024];
    if ((f1 = open(result,O_RDONLY)) == -1)
        fprintf(stderr, "can´t open %s",result);
    if ((f2 = creat(name,PERMS)) == -1)
        fprintf(stderr,"can´t open %s",name);

    while ((n = read(f1,buf,1024)) > 0) {
        if (write(f2,buf,n) !=n){
            fprintf(stderr,"write error on file %s",name);
            return -1;
        }
    }
    return 0;
}

int restore(char * from,char *to){
    char *name[1024];
    sprintf(name,"%s/%s",from,to);

    int f1, f2, n;
    char buf[1024];
    if ((f1 = open(name,O_RDONLY)) == -1)
        fprintf(stderr, "can´t open %s",name);
    if ((f2 = creat(to,PERMS)) == -1)
        fprintf(stderr,"can´t open %s",to);

    while ((n = read(f1,buf,1024)) > 0) {
        if (write(f2,buf,n) !=n){
            fprintf(stderr,"write error on file %s",name);
            return -1;
        }
    }
    return 0;
}
