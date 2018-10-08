#include <stdio.h>

int main(){
	
	int q1,q2,q3,count;
	char answer;
	
	printf("\n Which is the lastest HTML version?");
	scanf("%d", &q1);
	if(q1 == 5){
		count += 1;
		printf("\n Right answer");
	}else{
		printf("\n Wrong answer");
	}
	printf("\n 1B = ?b");
	scanf("%d", &q2);
	if(q2 == 8){
		count += 1;
		printf("\n Right answer");
	}else{
		printf("\n Wrong answer");
	}
	printf("\n How many mm is 1 cm?");
	scanf("%d", &q3);
	if(q3 == 10){
		count += 1;
		printf("\n Right answer");
	}else{
		printf("\n Wrong answer");
	}
	printf("\n Java == JavaScript?");
	scanf(" %c", &answer);
	if(answer ==  'n' ){
		count += 1;
		printf("\n Right answer");
	}else{
		printf("\n Wrong answer");
	}
	printf("\n Your score is %d points", count);
	
	
	
}
