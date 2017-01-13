import mysql.connector
from mysql.connector import FieldType

def connectDB():
    conn = mysql.connector.connect( user='root', password = "better think about it", database='pocketTanksDjango')
    c = conn. cursor()

    return c, conn
