import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BinaryTreeTest {

  /*
  left(bin(x, b, y)) = x
   */
  @Test
  public void left() {
    AVL_Tree<SortierInt> intAVL_tree = new AVL_Tree<>(new SortierInt(5));
    intAVL_tree.insert(new SortierInt(2), intAVL_tree);
    assertEquals(2, intAVL_tree.getLeft().getContent().getInhalt());
  }

  /*
  right(bin(x, b, y)) = y
   */
  @Test
  public void right() {
    AVL_Tree<SortierInt> intAVL_tree = new AVL_Tree<>(new SortierInt(5));
    intAVL_tree.insert(new SortierInt(8), intAVL_tree);
    assertEquals(8, intAVL_tree.getRight().getContent().getInhalt());
  }

  /*
  value(bin(x, b, y)) = b
   */
  @Test
  public void value() {
    AVL_Tree<SortierInt> intAVL_tree = new AVL_Tree<>(new SortierInt(12));
    assertEquals(12, intAVL_tree.getContent().getInhalt());
  }

  /*
  is_empty(empty) = true
   */
  @Test
  public void leer_isEmpty() {
    assertTrue(new AVL_Tree<SortierInt>().isEmpty());
  }

  /*
  is_empty(bin(x, b, y)) = false
   */
  @Test
  public void voll_isEmpty() {
    assertFalse(new AVL_Tree<SortierInt>(new SortierInt(3)).isEmpty());
  }

  /*
  search method working
   */
  @Test
  public void search_isInTree() {
    AVL_Tree<SortierInt> wurzel = new AVL_Tree<>(new SortierInt(6));
    wurzel.insert(new SortierInt(2), wurzel);
    wurzel.insert(new SortierInt(12), wurzel);
    wurzel.insert(new SortierInt(8), wurzel);
    assertEquals(wurzel.search(new SortierInt(8)).inhalt, 8);
  }

  /*
  search method working when not in tree
   */
  @Test
  public void search_isNotInTree() {
    AVL_Tree<SortierInt> wurzel = new AVL_Tree<>(new SortierInt(6));
    wurzel.insert(new SortierInt(2), wurzel);
    wurzel.insert(new SortierInt(12), wurzel);
    wurzel.insert(new SortierInt(8), wurzel);
    assertNull(wurzel.search(new SortierInt(9)));
  }

  /*
  remove working
   */
  @Test
  public void remove_nochild() {
    AVL_Tree<SortierInt> wurzel = new AVL_Tree<>(new SortierInt(6));
    wurzel.insert(new SortierInt(2), wurzel);
    wurzel.insert(new SortierInt(12), wurzel);
    wurzel.insert(new SortierInt(8), wurzel);
    assertEquals(wurzel.search(new SortierInt(8)).inhalt, 8);
    wurzel.remove(new SortierInt(8));
    assertFalse(wurzel.contains(new SortierInt(8)));
  }

  /*
  remove working
   */
  @Test
  public void remove_onechild() {
    AVL_Tree<SortierInt> wurzel = new AVL_Tree<>(new SortierInt(6));
    wurzel.insert(new SortierInt(2), wurzel);
    wurzel.insert(new SortierInt(12), wurzel);
    wurzel.insert(new SortierInt(8), wurzel);
    wurzel.remove(new SortierInt(12));
    assertFalse(wurzel.contains(new SortierInt(12)));
  }

  /*
  remove working
   */
  @Test
  public void remove_twochildren() {
    AVL_Tree<SortierInt> wurzel = new AVL_Tree<>(new SortierInt(6));
    wurzel.insert(new SortierInt(2), wurzel);
    wurzel.insert(new SortierInt(12), wurzel);
    wurzel.insert(new SortierInt(8), wurzel);
    wurzel.remove(new SortierInt(6));
    assertFalse(wurzel.contains(new SortierInt(6)));
  }
}