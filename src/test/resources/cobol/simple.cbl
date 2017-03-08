      ******************************************************************
      * Author:
      * Date:
      * Purpose:
      * Tectonics: cobc
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. YOUR-PROGRAM-NAME.
       DATA DIVISION.
       FILE SECTION.
       WORKING-STORAGE SECTION.
       01  FELD1 PIC X(10) value "WERT".
       01  FELD2 PIC X(10) value SPACES.
       01  UNUSED PIC X(10).
       PROCEDURE DIVISION.
       MAIN-PROCEDURE.
           DISPLAY "Start".
           MOVE "Neu" TO FELD1.
           DISPLAY FELD1.
           DISPLAY FELD2.
           MOVE "Neu2" TO FELD1.
           DISPLAY FELD1.
           DISPLAY FELD2.
           MOVE FELD1 TO FELD2.
           DISPLAY FELD1.
           DISPLAY FELD2.
           MOVE 1234 TO FELD2.
           DISPLAY FELD1.
           DISPLAY FELD2.
           STOP RUN.
       END PROGRAM YOUR-PROGRAM-NAME.
