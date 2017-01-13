#include <bits/stdc++.h>

using namespace std;

#define ll long long
#define pb push_back
#define mp make_pair
#define pii pair< int , int >
#define vii vector< int >
#define ff first
#define ss second
#define rep(i,n) for(int i=0;i<n;i++)
#define frep(i , a , b) for(int i = a;i <= b;i++)
#define fast cin.sync_with_stdio(0);cin.tie(0);
#define CASES int t;cin >> t;while(t--)
#define FI freopen ("in.txt", "r", stdin)
#define FO freopen ("out.txt", "w", stdout)
#define inf 0x7fffffff
const int MOD = 1e9 + 7;


int main()
{
	srand (time(NULL));
	vii v;
	int val = 2;
	int m = 1000, n = 10000, l = 1, r = 10000, p, hit, it = 5;
	while (val <= n) {
		v.pb(val - 1);
		val *= 2;
	}
	int steps = 0;
	int ag = 1;
	v.pb(n);
	int ind = 0, sz = v.size();
	char pr = '-';
	p = rand() % n;
	cout << 1 << "\n";
	// cout << 1 << "\n";
	l = p + 1;
	fflush( stdout );
	while (m--) {
		if (m == 999) {
			cin >> hit;
			if (hit) {
				int xx = p;
				while (xx == p) {

					p = rand() % n;
				}
				// cout << p + 1 << "\n";
				cout << v[(ind++) % sz] << "\n";
				fflush(stdout);
			}
			else {
				if (m <= it && it) {
					cout << 1 << "\n";
					// cout << v[ind%(sz++)] << "\n";
					it--;
					fflush( stdout );
				}
				else {
					cout << "0\n";
					fflush( stdout );
				}
			}
			cout << 5000 << " " << "xFalcon" << "\n";
			fflush( stdout );
			// cin >> pr;
			continue;
		}
		cin >> hit >> pr;
		if (hit) {
			steps = 0;
			int xx = p;
			p = rand() % n;
			while (p == xx) {

				p = rand() % n;
				// p++;
			}
			// if (m & 1)
			// 	cout << n << "\n";
			// else cout << "1\n";
			cout << v[(ind++) % (sz)] << "\n";
			// cout << (p + 1) % n + 1 << "\n";
			fflush(stdout);
		}
		else {
			steps++;
			// if(steps >= 11)
			if (m <= it && it && steps >= 11) {
				cout << min( n, v[(ind - 1) % sz] + 100) << "\n";
				// cout << v[ind % (sz)] << "\n";
				// cout << p + 1 << "\n";
				// l=p+1;
				it--;
				fflush( stdout );
			}
			else {
				cout << "0\n";
				fflush( stdout );
			}
		}
		string mt = "xFalcon";
		if (m == 0 && ag > 0) {
			// if (m <= 0 && ag > 0) {
			mt = "xAgni-V";
			// else {
			// 	mt = "xSeaEagle";
			// }
		}
		if (pr == '0')
		{
			l = 1; r = n;
			cout << (l + r) / 2 << " " << mt << "\n";
		}
		else if (pr == '+') {
			int mid = (l + r) / 2;
			l = mid + 1;
			int o = (l + r) / 2;
			if (ag > 0 && abs(l - r) <= 5) {
				mt = "xAgni-V";
				ag--;
			}
			if (o > n)o = n; if (o < 1)o = 1;
			cout << o << " " << mt << "\n";
			if (l >= r) {
				l = mid; r = n;
			}
			// l = min(l + 10, n);
		}
		else {
			int mid = (l + r) / 2;
			r = mid - 1;
			int o = (l + r) / 2;
			if (o > n)o = n; if (o < 1)o = 1;
			if (ag > 0 && abs(l - r) <= 5) {
				mt = "xAgni-V";
				ag--;
			}
			cout << o << " " << mt << "\n";
			if (l >= r) {
				l = 1; r = mid;
			}
		}
		fflush( stdout );

		// cin >> pr;
	}
	return 0;
}