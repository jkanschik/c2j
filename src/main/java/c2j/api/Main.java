package c2j.api;

/**
 * Created by kanschje on 09.03.2017.
 */
public class Main {

  public static void main(String... args) {

    AlphaNumericCobolRecord feld1 = new AlphaNumericCobolRecord();
    AlphaNumericCobolRecord feld2 = new AlphaNumericCobolRecord();

    CobolStatements cobol = new CobolStatements();
    cobol.move("Hallo Welt", feld1);
    cobol.display(feld1);

    // MOVE "Hallo" TO FELD1
    cobol.move("Hallo", feld1);
    cobol.display(feld1);

    // MOVE "Hallo" TO FELD1(3:)
    cobol.move("Hallo", feld1.getRefMod(3));
    cobol.display(feld1);

    // MOVE FELD1(5:2) TO FELD2
    cobol.move(feld1.getRefMod(5, 2), feld2);
    cobol.display(feld1);

    cobol.move(feld1, feld2);
    cobol.display(feld2);
  }
}
