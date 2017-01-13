from django.shortcuts import render
from django.http import HttpResponse

from django import forms

from passlib.hash import pbkdf2_sha256

from MySQLdb import escape_string as thwart


from . import connector


class fileForm( forms.Form):
    fileUploader = forms.FileField(
        label = 'Select a file'
    )


def login( request):

    if 'logged_in' in request. session:

        form = fileForm( request. POST, request. FILES)
        return render( request, "dashboard/dashboard.html", {
                'form' : form,
                'messages' : ["Please logout first!"],
                'user' : request. session[ 'username']
            }
        )

    if request. method == 'POST':

        username = str( thwart( request. POST[ 'username'] ) )
        username = username[ 2 : len( username ) - 1 ]

        c, conn = connector. connectDB()

        c. execute( "select password from users where username = (%s)", (username, ) )

        for row in c:

            if pbkdf2_sha256. verify( str( thwart( request. POST[ 'password'] ) ), row[ 0] ):

                request. session[ 'logged_in'] = True
                request. session[ 'username'] = username
                form = fileForm( request. POST, request. FILES)
                return render( request, "dashboard/dashboard.html", {
                        'form' : form,
                        'messages' : ["Successfully logged in"],
                        'user' : username
                    }
                )

            return render( request, "pocketTanks/login.html", { 'messages' : ["Invalid Credentials"] } )

        return render( request, "pocketTanks/login.html", { 'messages' : ["Username doesnot exist"] } )

    return render( request, "pocketTanks/login.html")
