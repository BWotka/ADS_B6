import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Used to get data from user and share data with user.
 * Communicating using the console
 */
public class EinAusgabe<ContentType extends Vergleichbar<ContentType>> {
  boolean keepRunning = true;
  BufferedReader br;
  AVL_Tree<ContentType> avl_SortTree;
  final Class<ContentType> typeParameterClass;

  /**
   * constructor which also starts the main loop.
   */
  public EinAusgabe(Class<ContentType> typeParameterClass) {
    this.typeParameterClass = typeParameterClass;
    br = new BufferedReader(new InputStreamReader(System.in));
    avl_SortTree = new AVL_Tree<>();
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
          System.out.println("Zuerst Preorder");
          System.out.println(removeEmptyTrees(preorder(avl_SortTree, 0)));
          System.out.println("Jetzt Inorder");
          System.out.println(removeEmptyTrees(inorder(avl_SortTree, 0)));
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
    ContentType mod = leseEingabe();
    ContentType sInt = avl_SortTree.search(mod);
    avl_SortTree.remove(mod);
    System.out.println("Wie soll die Zahl jetzt lauten?");
    sInt.setInhalt(leseEingabe());
    avl_SortTree.insert(sInt,  null);
    System.out.println("Neue Zahl jetzt im Baum");
    avl_SortTree = avl_SortTree.highestParent();
  }

  private void remove() {
    System.out.println("Loeschen ausgewählt");
    System.out.println("Welche Zahl soll geloescht werden(wenn vorhanden)?");
    avl_SortTree.remove(leseEingabe());
    System.out.println("Zahl nicht (mehr) im Baum");
    avl_SortTree = avl_SortTree.highestParent();
  }

  private void create() {
    System.out.println("Erstellen ausgewählt");
    System.out.println("Welche Zahl soll eingefuegt werden?");
    avl_SortTree.insert(leseEingabe(), null);
    System.out.println("Zahl erfolgreich eingefuegt");
    avl_SortTree = avl_SortTree.highestParent();
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
  private String leseString(){
    String input;
    String neuString;
    try {
      neuString = br.readLine();
    } catch (IOException pIOException) {
      System.out.println("Einlesefehler");
      create();
      return leseString();
    }
    if(neuString.length()<4){
      System.out.println("Eingabe zu kurz");
      return leseString();
    }
    return neuString;
  }

  private ContentType leseEingabe() {
    if (typeParameterClass == SortierString.class) {
      return (ContentType) new SortierString(leseString());
    } else if (typeParameterClass == SortierInt.class) {
      return (ContentType) new SortierInt(leseInteger());
    } else {
      return null;
    }
  }

  /**
   * Used to create String of complete tree in preorder.
   * Works recursive
   *
   * @param pWurzel current tree
   * @param pDepth  depth of pWurzel in complete tree
   * @return preorder String of tree
   */
  private String preorder(AVL_Tree<ContentType> pWurzel, int pDepth) {
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
  private String inorder(AVL_Tree<ContentType> pWurzel, int pDepth) {
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
  private String wertAusgabe(AVL_Tree<ContentType> pWurzel, int pDepth) {
    if (pWurzel == null || pWurzel.isEmpty()) {
      return "null";
    }
    return pWurzel.getContent().getInhalt() + "(" + pWurzel.getHeight() + " ," + pWurzel.getBalance() + ")";
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






}

