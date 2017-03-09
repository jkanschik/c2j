       IDENTIFICATION DIVISION.
       PROGRAM-ID.       SAMPLE.

       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       
       01  FELD-X1     PIC X.
       01  FELD-X1-S   PIC X VALUE SPACES.
       01  FELD-X1-A   PIC X VALUE "A".

       01  FELD-X2     PIC X(10).
       01  FELD-X2-S   PIC X(10)      VALUE SPACE.
       01  FELD-X2-A   PIC X(10)      VALUE "AAAAAAAAAA".
       01  FELD-X2-A-N REDEFINES FELD-X2-A PIC 9(10).

       01  FELD-N1     PIC 9 VALUE ZERO.
       01  FELD-N2     PIC 9(10) VALUE ZERO.
       
       01  GROUP-1.
	       05  FELD-1-1   PIC X.
	       05  FELD-1-2   PIC X(10).
	       05  FELD-1-2-N REDEFINES FELD-1-2 PIC 9(10).
	   
       01  GROUP-2.
	       05  FELD-1-1   PIC X.
	       05  FELD-1-2   PIC X(10).
	       05  FELD-1-3.
	           10  FELD-1-3-1 PIC X.
	           10  FELD-1-3-2 PIC X.
	       05  FELD-1-2-N REDEFINES FELD-1-2 PIC 9(10).
	   
	   
       PROCEDURE DIVISION.       
       STOP RUN.
