#include <stdio.h>
#include <time.h>
#include <stdlib.h>

int main(){
	srand(time(NULL));
	int i=rand()%9+1;
	while(i!=10){
	i=rand()%10+1;
	switch(i){
		case 1:printf("\nThis time it is 1"); // POZNAMKY A INE SRACKY
			   break;
		case 2:printf("\nThis time it is 2");
			   break;
		case 3:printf("\nThis time it is 3");
			   break;
		case 4:printf("\nThis time it is 4");
			   break;
		case 5:printf("\nThis time it is 5");
			   break;
		case 6:printf("\nThis time it is 6");
			   break;
		case 7:printf("\nThis time it is 7");
			   break;
		case 8:printf("\nThis time it is 8");
			   break;
		case 9:printf("\nThis time it is 9");
			   break;
		case 10:printf("\nThis time it is 10");
			   break;
		default:printf("\nWrong");
	}	
	
	
		
	}
	
}
