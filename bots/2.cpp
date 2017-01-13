#include <bits/stdc++.h>
using namespace std;

int n = 10000,m = 1000;
int p,hit;
int target;
int l,r;
char prev;
int agni = 1,seagle = 10;
int it = 5;

int lim;

int predict_attack_count(int l,int r,int x){
	if (l>r) return 11;
	int mid = (l+r)>>1;
	if (mid==x) return 1;
	else if (mid>x) return (1+predict_attack_count(l,mid-1,x));
	else return (1+predict_attack_count(mid+1,r,x));
}

int consecutive_miss;

int get_position(){
	p = rand()%n+1;
	lim = predict_attack_count(1,n,p)-2;
	if (lim<0) lim = 1;
	return 0;
}

int in_agni_range(){
	if (r-l+1<=5) return 1;
	return 0;
}

int in_seagle_range(){
	if (r-l+1<=200) return 1;
	return 0;
}

int first_move(){
	l = 1,r = 10000;
	get_position();
	cout << p << endl;
	cin >> hit;
	if (hit){
		get_position();
		cout << p << endl;
	}
	else cout << 0 << endl;
	target = (l+r)>>1;
	cout << target << " xFalcon" << endl;
	fflush(stdout);
	return 0;
}

int main(){
	first_move();m--;
	while (m--){
		cin >> hit >> prev;
		//defence
		if (hit){
			consecutive_miss = 0;
			get_position();
			cout << p << endl;
		}
		else{
			if (consecutive_miss==lim && it){
				if (p>5000) p-=100;
				else p+=100;
				cout << p << endl;
				it--;
			}
			else cout << 0 << endl;
			consecutive_miss++;
		}

		//attack
		if (prev=='0'){
			l = 1;r = 10000;
			target = 5000;
		}
		else if (prev=='+'){
			l = target+1;
			target = min(10000,(l+r)>>1);
		}
		else{
			r = target-1;
			target = max(1,(l+r)>>1);
		}
		cout << target << " ";
		if (agni && in_agni_range()){
			agni--;
			cout << "xAgni-V" << endl;
		}
		else cout << "xFalcon" << endl;
		fflush(stdout);
	}
	return 0;
}

