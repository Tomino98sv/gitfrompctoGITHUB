#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define C 30
int main(){
	
	srand(time(NULL));
	int sum=0;
	int pole[C];
	double avr=0;
	int digone=0;
	int digtwo=0;
	
	
	for(int i=0;i<C;i++){
		pole[i]=rand()%90+10;
		printf("\npole[%d]: %d",i,pole[i]);
		sum+=pole[i];
	}
	
	int max=pole[0];
	int min=pole[0];
	
	avr=(double)sum/C;
	printf("\nAverage of all numbers: %lf", avr);
	
	printf("\n\n");
	for(int i=0;i<C;i++){
		if(pole[i]%2==0){
			printf("\npole[%d]: %d   Even number",i,pole[i]);
		}
	}
	
	printf("\n\n");
	for(int i=0;i<C;i++){
		if(pole[i]%2!=0){
			printf("\npole[%d]: %d   Odd number",i,pole[i]);
		}
	}
	printf("\n\n");
	for(int i=0;i<C;i++){
		if(max<pole[i]){
			max=pole[i];
		}
	}
	printf("\nMax number is %d", max);
	
	printf("\n\n");
	for(int i=0;i<C;i++){
		if(min>pole[i]){
			min=pole[i];
		}
	}
	printf("\nMin number is %d", min);
	
	printf("\n\n");
	for(int i=0;i<C;i++){
		digone=pole[i]%10;
		digtwo=pole[i]/10;
		if(digone>digtwo){
			printf("\n This number has digit on place of units bigger than on tenths: %d", pole[i]);
		}
	}
	
	printf("\n\n");
	for(int i=0;i<C;i++){
		if(pole[i]%5==0 && pole[i]%3==0){
			printf("This number is divisibel by 5 and 3 on the same time: %d", pole[i]);
		}
	}
}
