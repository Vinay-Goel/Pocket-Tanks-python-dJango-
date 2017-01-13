from django.shortcuts import render
from django.http import HttpResponse

from django import forms

from passlib.hash import pbkdf2_sha256

from MySQLdb import escape_string as thwart


from . import connector



class makeForm( forms. Form):

    username = forms. CharField(
        label = 'Enter new username',
        min_length = 3,
        max_length = 100,
    )
    password = forms. CharField(
        label = 'Enter new password',
        min_length = 3,
        max_length = 100,
        widget = forms. PasswordInput()
    )
    confirm = forms. CharField(
        label = 'Confirm password',
        min_length = 3,
        max_length = 100,
        widget = forms. PasswordInput()
    )
    tnc = forms. BooleanField(
        label = 'I accept Terms & Conditions'
    )


class fileForm( forms.Form):
    fileUploader = forms.FileField(
        label = 'Select a file'
    )



def register( request):

    if 'logged_in' in request. session:
        form = fileForm( request. POST, request. FILES)
        return render( request, "dashboard/dashboard.html", {
                'form' : form,
                'user' : request. session[ 'username'],
                'messages' : ["Please logout first"]
            }
        )

    form = makeForm( request. POST)

    if request. method == 'POST':

        if form. is_valid():

            username = str( thwart( request. POST[ 'username'] ) )
            username = username[ 2: len( username) - 1]

            c, conn = connector. connectDB()

            c. execute( "select * from users where username = (%s)", (username, ) )

            for row in c:

                return render( request, "register/register.html", {'form' : form, 'messages' : ["Username taken!!"]} )


            password = pbkdf2_sha256. encrypt( str( thwart( request. POST[ 'password'] ) ), rounds = 12000, salt_size = 32 )
            confirm = str( thwart( request. POST[ 'confirm'] ) )

            if not pbkdf2_sha256. verify( confirm, password):
                return render( request, "register/register.html", {'form': form, 'messages' : ["Passwords must match"]})

            c. execute( "insert into users (username, password) values (%s, %s)", (username, password, ) )
            conn. commit()
            request. session[ 'logged_in'] = True
            request. session[ 'username'] = username

            form = fileForm( request. POST, request. FILES)

            return render( request, "dashboard/dashboard.html",{
                    'form' : form,
                    'messages' : ["Successfully registered"],
                    'user' : username
                }
            )

    return render( request, "register/register.html", {'form' : form} )
