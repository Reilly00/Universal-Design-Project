import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from dotenv import load_dotenv
load_dotenv()

app = Flask(__name__)

# Use environment variables for sensitive data
username = os.environ.get('DB_USERNAME', 'default_username')
password = os.environ.get('DB_PASSWORD', 'default_password')
endpoint = os.environ.get('DB_ENDPOINT', 'default_endpoint')
dbname = os.environ.get('DB_NAME', 'default_dbname')

# Configure the SQLAlchemy part of the app, with the MySQL URI
app.config['SQLALCHEMY_DATABASE_URI'] = f'mysql+pymysql://{username}:{password}@{endpoint}/{dbname}'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

@app.route('/')
def index():
    return "Connected to the database!"

if __name__ == '__main__':
    app.run(debug=True)