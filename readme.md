
# Problem definition
In this project, we are expected to develop a pseudo-Netflix platform. Netflix is ​​a platform for watching feature films, documentaries, series, programs, anime.

**Purpose**

The purpose of the project, is for the students to understand the structure of database management systems and provide solutions.

# Requirements
The developed application should have two different interfaces:
- User login page.
- User registration page.
- Interface where the user can search and watch content.

Note: Here we are not expected to add a video. in the database

## Interface Requirements

**User login page**

- The user will be able to log in using his e-mail address and password while logging into the system.

**User registration page**

- If the user is not registered in the system (this will be checked by e-mail address), it will be registered.
- During the recording, the user will be asked to select 3 different program types and the 2 highest rated movies will be listed according to the genres.

**User management page**

After logging into the system, the user:
- Will be able to search for content (Search by Movie Title and Genre)
- Content will be able to watch. (Reminder: In this section, we only need to add a watch button for control purposes. The important thing is to keep the information that the user watches the content in the database.)
- If the user is watching the series, even if the application is closed, the user should be able to continue from where he left off if the application is reopened.

## Database Requirements
### Tables
It is expected that there will be 5 tables in the database. We are expected to find Primary Key and Foreign Key fields.

**Program Table**
- Each program must have a unique identifier
- Each program must have a name
- There must be Program Type (TV Series or Movie).
- It should be noted that a Program can belong to more than one genre (comedy, horror and romantic etc.) and a Genre can belong to more than one program.
- There should be an Episode attribute to show how many episodes the program has. If it is a movie then "1" can be written in the field.
- Must have Program Length attribute to specify the length of the program.

**Type Table**

- Must have a unique identifier for each species
- The genre must be the name. (Action, Documentary, Science Fiction etc.)

**User Table**

- Each user must have a unique identifier.
- Each user must have name
- Each user must have email address
- Each user must have password
- Each user must have date of birth

**UserProgram Table**

This table has a many-to-many relationship between User and Program.
For this reason, direct connection cannot be established, we are expected to find the key attributes in this table.

Besides key attributes, this table must have:
- Watch date
- Watch time (length)
- There should be a value showing which episode the user last watched
- The point value given by the user between 1-10

**There is one more table we need to add. This table is the link between Program and Genre of the relationship. We are expected to find the table and the attributes in the table ourselves.**

# Ease of Use 

- When you enter the program, you will see a login page.
- Press the register button for new registration
- Here you will be asked for your username, password, e-mail, and date of birth. It is mandatory to fill in these fields. Otherwise, you will get an error message.
- If you want, you can choose 3 types and see the most liked ones in that type. These fields are optional.
- After successful registration, you will be redirected to the login page again.
- If you typed your username and password correctly, you will go to the administration page, if you type it incorrectly, you will get an error message.
- You can search by genre and name on this page.
- When searching by name, pay attention to uppercase and lowercase letters.
- To watch a content, simply select it from the table and press the watch button. The counter starts working.
- The counter counts the seconds and keeps it in minutes.
- If you have watched and given a score, the episode you have left and the score you have given for the minute will be seen on the interface.
- If you press the Stop button, select other content, or exit the program, the episode and minute you left are automatically recorded.
- You cannot rate content you have not watched. Otherwise, you will get an error message.
- To score points, you must enter an integer from 1-10 in the scoring area and press the button to score. If your score does not meet the criteria, you will receive an error message.
