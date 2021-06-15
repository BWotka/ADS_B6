import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Used to get data from user and share data with user.
 * Communicating using the Console
 */
public class EinAusgabe {
  boolean keepRunning = true;
  BufferedReader br;
  BinarySearchTree<SortierInt> intTree;
  BinarySearchTree<SortierString> stringTree;

  /**
   * constructor which also starts the main loop.
   */
  public EinAusgabe() {
    br = new BufferedReader(new InputStreamReader(System.in));
    intTree = new BinarySearchTree<SortierInt>();
    programLoop();
  }

  private void programLoop() {
    System.out.println("Willkommen zum binären Suchbaum");
    while (keepRunning) {
      getInput();
    }
    System.out.println("Programm wird beendet");
  }

  private void getInput() {
    System.out.println("Es gibt folgende Optionen:");
    System.out.println("Einfuegen 'e'");
    System.out.println("Loeschen 'l'");
    System.out.println("Modifizieren 'm'");
    System.out.println("Baumausgabe 'a'");
    System.out.println("Stopp 's'");
    System.out.println("Was moechtest du tun?");
    try {
      char eingabe = br.readLine().charAt(0);
      switch (eingabe) {
        case 'e':
          create();
          break;
        case 'l':
          remove();
          break;
        case 'm':
          modify();
          break;
        case 'a':
          System.out.println("Zuerst Preoder");
          System.out.println(removeEmptyTrees(preorder(intTree, 0)));
          System.out.println("Jetzte Inorder");
          System.out.println(removeEmptyTrees(inorder(intTree, 0)));
          break;
        case 's':
          keepRunning = false;
          break;
        default:
          System.out.println("Eingabe nicht erkannt");
      }
    } catch (IOException pE) {
      System.out.println("Problem mit Eingabe");
    }
  }

  private void modify() {
    System.out.println("Modifizieren ausgewählt");
    System.out.println("Welche Zahl soll bearbeitet werden?");
    int mod = leseInteger();
    SortierInt sInt = intTree.search(new SortierInt(mod));
    intTree.remove(new SortierInt(mod));
    System.out.println("Wie soll die Zahl jetzt lauten?");
    sInt.setInhalt(leseInteger());
    intTree.insert(sInt);
    System.out.println("Neue Zahl jetzt im Baum");
  }

  private void remove() {
    System.out.println("Loeschen ausgewählt");
    System.out.println("Welche Zahl soll geloescht werden(wenn vorhanden)?");
    intTree.remove(new SortierInt(leseInteger()));
    System.out.println("Zahl nicht (mehr) im Baum");
  }

  private void create() {
    System.out.println("Erstellen ausgewählt");
    System.out.println("Welche Zahl soll eingefuegt werden?");
    intTree.insert(new SortierInt(leseInteger()));
    System.out.println("Zahl erfolgreich eingefuegt");


  }

  private int leseInteger() {
    String input;
    try {
      input = br.readLine();
    } catch (IOException pIOException) {
      System.out.println("Einlesefehler");
      create();
      return leseInteger();
    }
    int neuInt;
    try {
      neuInt = Integer.parseInt(input);
    } catch (NumberFormatException pNumberFormatException) {
      System.out.println("Deine Eingabe konnte nicht als Zahl erkannt werden");
      return leseInteger();
    }
    return neuInt;
  }

  /**
   * Used to create String of complete tree in preorder.
   * Works recursive
   *
   * @param pWurzel current tree
   * @param pDepth  depth of pWurzel in complete tree
   * @return preorder String of tree
   */
  private String preorder(BinarySearchTree<SortierInt> pWurzel, int pDepth) {
    if (pWurzel == null) {
      return "null\n";
    }
    String ausgabe = "";
    ausgabe += wertAusgabe(pWurzel, pDepth);
    ausgabe += " : ";
    ausgabe += wertAusgabe(pWurzel.getLeft(), pDepth + 1);
    ausgabe += " , ";
    ausgabe += wertAusgabe(pWurzel.getRight(), pDepth + 1);
    ausgabe += "\n";
    return ausgabe + preorder(pWurzel.getLeft(), pDepth + 1) + preorder(pWurzel.getRight(), pDepth + 1);
  }

  /**
   * Used to create String of complete tree in inorder.
   * Works recursive
   * Sorted for BinarySearchTree
   *
   * @param pWurzel current tree
   * @param pDepth  depth of pWurzel in complete tree
   * @return inorder String of tree
   */
  private String inorder(BinarySearchTree<SortierInt> pWurzel, int pDepth) {
    if (pWurzel == null) {
      return "null\n";
    }
    String ausgabe = "";
    ausgabe += wertAusgabe(pWurzel, pDepth);
    ausgabe += " : ";
    ausgabe += wertAusgabe(pWurzel.getLeft(), pDepth + 1);
    ausgabe += " , ";
    ausgabe += wertAusgabe(pWurzel.getRight(), pDepth + 1);
    ausgabe += "\n";
    return inorder(pWurzel.getLeft(), pDepth + 1) + ausgabe + inorder(pWurzel.getRight(), pDepth + 1);
  }

  /**
   * Used to create string containing data from a single element in tree.
   *
   * @param pWurzel tree of which the return will be created
   * @param pDepth  depth of pWurzel in complete tree
   * @return String like "a(ha,ba)"
   */
  private String wertAusgabe(BinarySearchTree<SortierInt> pWurzel, int pDepth) {
    if (pWurzel == null) {
      return "null";
    }
    if (pWurzel.isEmpty()) {
      return "null";
    }
    return pWurzel.getContent().getInhalt() + "(" + maxDepth(pWurzel) + " ," + balance(pWurzel) + ")";
  }

  private String removeEmptyTrees(String pausgabe) {
    String[] lines = pausgabe.split("\\r?\\n");
    String neuausgabe = "";
    for (int i = 0; i < lines.length; i++) {
      if (lines[i].equals("null") | lines[i].equals("null\n") | lines[i].equals("null : null , null")) {
        continue;
      } else {
        neuausgabe += lines[i] + "\n";
      }
    }
    return neuausgabe;
  }

  /**
   * Calculates the max depth/high of pWurzel
   * uses recursion
   *
   * @param pWurzel of which the max depth will be calcualted
   * @return max depth of parameter tree
   */
  private int maxDepth(BinarySearchTree<SortierInt> pWurzel) {
    if (pWurzel == null) {
      return 0;
    }
    if (pWurzel.isEmpty()) {
      return 0;
    }
    int hightl;
    if (pWurzel.getLeft() == null) {
      hightl = 0;
    } else {
      hightl = maxDepth(pWurzel.getLeft());
    }
    int hightr;
    if (pWurzel.getLeft() == null) {
      hightr = 0;
    } else {
      hightr = maxDepth(pWurzel.getRight());
    }
    if (hightr > hightl) {
      return 1 + hightr;
    } else {
      return 1 + hightl;
    }
  }

  /**
   * Calculates balance of pWurzel.
   *
   * @param pWurzel tree of which the balance will be calculated
   * @return balanace of parameter tree
   */
  private int balance(BinarySearchTree<SortierInt> pWurzel) {
    return maxDepth(pWurzel.getLeft()) - maxDepth(pWurzel.getRight());
  }
}

