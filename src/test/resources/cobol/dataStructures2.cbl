       IDENTIFICATION DIVISION.
       PROGRAM-ID.       SAMPLE.

       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       
       01  FELD-X1     PIC X.
       01  FELD-X1-S   PIC X VALUE SPACES.
       01  FELD-X1-A   PIC X VALUE "A".

       01  FELD-X2     PIC X(10).
       01  FELD-X2-S   PIC X(10)      VALUE SPACES.
       01  FELD-X2-A   PIC X(10)      VALUE "AAAAAAAAAA".

       01  GROUP-1.
	       05  FELD-1-1   PIC X.
	       05  FELD-1-2   PIC X(10).
	   
       01  GROUP-2.
	       05  FELD-1-1   PIC X.
	       05  FELD-1-2   PIC X(10).
	   
	   
       PROCEDURE DIVISION.       
       STOP RUN.
