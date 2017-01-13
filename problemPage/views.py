from django.shortcuts import render
from django.http import HttpResponse

from django import forms

from passlib.hash import sha256_crypt

from MySQLdb import escape_string as thwart

from django.core.files.storage import FileSystemStorage

import os



from . import connector

from . import simulateJudge





class fileForm( forms.Form):
    fileUploader = forms.FileField(
        label = 'Select a file'
    )


def getSubmissions( uid):

    submissions = []

    c, conn = connector. connectDB()

    c. execute( "select botID from bots where uid = (%s)", (uid, ))

    for row in c:
        submissions. append( row[ 0] )

    return submissions



def dashboard( request):

    if not 'logged_in' in request. session:

        return render( request, "pocketTanks/login.html", {'messages' : ["Please login"]} )

    c, conn = connector. connectDB()

    username = request. session[ 'username']

    c. execute( "select uid from users where username = (%s)", (username, ) )

    uid = c. fetchone()[ 0]

    form = fileForm( request.POST, request.FILES)


    if request. method == "POST" and form.is_valid():

        c. execute( "update bots set usersLastSubmission = 0 where uid = (%s)", (uid, ) )

        botID = 1

        c. execute( "select max( botID) from bots")
        st = str( c. fetchone()[ 0] )

        if st != 'None':
            botID = int( st) + 1

        fObj = request.FILES[ 'fileUploader']


        fileName = fObj. name

        fileExt = 'none'

        if fileName. endswith( '.cpp'):
            fileExt = '.cpp'

        if fileName. endswith( '.c'):
            fileExt = '.c'

        if fileName. endswith( '.java'):
            fileExt = '.java'


        if fileExt == 'none':

            return render( request, dashboard, {
                'form' : form,
                'submissions' : getSubmissions( uid),
                'user' : username,
                'error' : "Invalid file type!"
            })



        fss = FileSystemStorage()
        filename = fss. save( str( botID ) + fileExt, fObj )


        c. execute( "select botID, extn from bots where usersLastSubmission = 1")

        for row in c:
            if row[ 0] == "None":
                break

            opponent = str( row[0] ) + row[1]
            thread1 = simulateJudge. threadFunc( str( botID ) + fileExt, opponent)
            thread1. start()




        c. execute( "insert into bots (botID, uid, usersLastSubmission, extn) values (%s, %s, 1, %s)",
                            (botID, uid, fileExt, )
        )
        conn. commit()

    return render( request, "dashboard/dashboard.html", {
        'form' : form,
        'submissions' : getSubmissions( uid),
        'user': username
    })
