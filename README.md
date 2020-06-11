# Spotify
Project – 633.2
Introduction 
The goal of the project is to create VSFy, a peer to peer audio streaming application with a synchronization server 
Applications description: 
The server and client applications should be coded in java based on the libraries discovered during the module lessons. Keep it simple. The system will stream audio from one client to another and the audio file that can be streamed are synchronized on the server. 

 
Figure1: Description of the system
Work to do: 

Minimal specifications of the server: 
•	The server must be able to: 
o	Register new clients 
o	Maintain a list of registered clients (and the files they can stream) 
o	Give back a client IP and its list of audio file to another client
o	Accept multiple clients simultaneously (use threads). 
•	The server must be able to write logs:
o	On a file 
o	The history must be kept (one file per month)
o	3 levels of log (info, warning, severe) should be handled 
	Info for all the useful operations
	Warning for all the possible network errors
	Severe for the exceptions
•	You can use command words to discuss between the client and the server or use different ports on the server for the client communications 


Minimal specifications of the client: 
•	The client will be able to connect to the server through socket connections 
•	
•	The client should be able to give its list of file to the server 
•	
•	The client should be able to give its IP address 
•	
•	The client should be able to get a list of clients with their available audio files
•	
•	The client should be able to ask for another client IP address
•	
•	The client should be able to connect to another client and ask to stream one file 
•	
•	The client should be able to accept a network connection from another client and stream the selected file
•	
•	The client should be able to play the audio stream  
