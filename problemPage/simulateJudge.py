import threading

import os

import time

class threadFunc( threading. Thread):

    def __init__( self, bot1, bot2):

        threading. Thread. __init__( self)
        self. bot1 = bot1
        self. bot2 = bot2


    def run( self):

        time. sleep( 3)
        os. chdir( "/home/vinay/Desktop/python/dJango/pocketTanks/bots/")
        os. system( "java judge " + self. bot1 + " " + self. bot2)
