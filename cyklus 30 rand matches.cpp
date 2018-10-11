#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#define C 30
int main(){
	srand(time(NULL));
	int pole[C];
	int match[C];
	
	for(int i=0;i<C;i++){
		pole[i]=rand()%20+1;
		printf("\npole[%d]: %d",i,pole[i]);
	}
	for(int i=0;i<C;i++){
		match[i]=0;
	}
	
	for(int i=0;i<C;i++){
		
		for(int j=i+1;j<C;j++){
			
			if(pole[i]==pole[j]){
				match[j]=pole[j];
			}else{
				match[j]=0;
			}
		}
		printf("\n\n");
		printf("\n Pole[%d]: %d",i,pole[i]);
		int Co=0;
		do{
			if(match[Co]!=0){
				printf(" Match[%d]: %d",Co,match[Co]);
			}
			Co++;
		}while(Co!=30);
		printf("\n\n");
	}
	
}
