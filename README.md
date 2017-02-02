How to set it up:

1. install mysql.
2. open terminal and do the following:

sudo su

mysql -p

create databese pocketTanksDjango;

use pocketTanksDjango;

create table users ( uid int( 64) primary key auto_increment, username varchar( 200), password varchar( 3000) );

create table bots ( botID int( 64) primary key auto_increment, uid int( 64), usersLastSubmission int( 1), extn varchar( 20) );

3. install mysql.connector using -> pip install --user mysql-connector.
4. if above doesn't work I recommend installing pycharn for installing python modules.
5. open judge.java in bots folder and on 8th line edit the path to 'directory/bots/' where directory is the location where you cloned this repository.
6. compile judge using -> javac judge.java
6. open simulateJudge.py in problemPage and on 19th line edit the path same as in above step. 
7. open settings.py in pocketTanks and on 91st line change the password to your mysql password.
done...!

Run using -> python3 manage.py runserver

The mysubmissions page needs some work. Currently it just gives the botID of your bot stored in db and in bots folder.

ABOUT GAME:

It's a two player bot game. Read the problem statement in the dashboard.
