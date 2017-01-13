from django.shortcuts import render

def logout( request):

    messages = []

    if 'logged_in' in request. session:

        request. session. clear()

        messages = ["Successfully logged out"]

    else:

        messages = ["Please login first"]

    return render( request, "pocketTanks/login.html", {'messages' : messages} )
