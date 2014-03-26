#include <stdio.h>
//#include <conio.h>

int main(void){

	int num1;
	int num2;
	int sum;
	
	printf("Enter your numbers to add:\n");
	
	//take input from the user and store value at address of num1
	scanf("%d", &num1);
	scanf("%d", &num2);

	sum = num1 + num2;

	printf("The sum of your numbers is %d\n", sum);
	
	//pause console so that it doesn't just exit
	getchar();
}
