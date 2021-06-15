import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BinaryTreeTest {

  /*
  left(bin(x, b, y)) = x
   */
  @Test
  public void left() {
    BinaryTree<Integer> binTree = new BinaryTree<Integer>(3, new BinaryTree<Integer>(5), new BinaryTree<Integer>(7));
    assertEquals(5, binTree.getLeft().getContent());
  }

  /*
  right(bin(x, b, y)) = y
   */
  @Test
  public void right() {
    BinaryTree<Integer> binTree = new BinaryTree<Integer>(3, new BinaryTree<Integer>(5), new BinaryTree<Integer>(7));
    assertEquals(7, binTree.getRight().getContent());
  }

  /*
  value(bin(x, b, y)) = b
   */
  @Test
  public void value() {
    BinaryTree<Integer> binTree = new BinaryTree<Integer>(3, new BinaryTree<Integer>(5), new BinaryTree<Integer>(7));
    assertEquals(3, binTree.getContent());
  }

  /*
  is_empty(empty) = true
   */
  @Test
  public void leer_isEmpty() {
    assertTrue(new BinaryTree<Integer>().isEmpty());
  }

  /*
  is_empty(bin(x, b, y)) = false
   */
  @Test
  public void voll_isEmpty() {
    assertFalse(new BinaryTree<Integer>(3, new BinaryTree<Integer>(5), new BinaryTree<Integer>(7)).isEmpty());
  }

  /*
  search method working
   */
  @Test
  public void search_isInTree() {
    BinarySearchTree<SortierInt> wurzel = new BinarySearchTree<SortierInt>(new SortierInt(6));
    wurzel.insert(new SortierInt(2));
    wurzel.insert(new SortierInt(12));
    wurzel.insert(new SortierInt(8));
    assertEquals(wurzel.search(new SortierInt(8)).inhalt, 8);
  }

  /*
  search method working when not in tree
   */
  @Test
  public void search_isNotInTree() {
    BinarySearchTree<SortierInt> wurzel = new BinarySearchTree<SortierInt>(new SortierInt(6));
    wurzel.insert(new SortierInt(2));
    wurzel.insert(new SortierInt(12));
    wurzel.insert(new SortierInt(8));
    assertNull(wurzel.search(new SortierInt(9)));
  }

  /*
  remove working
   */
  @Test
  public void remove_nochild() {
    BinarySearchTree<SortierInt> wurzel = new BinarySearchTree<SortierInt>(new SortierInt(6));
    wurzel.insert(new SortierInt(2));
    wurzel.insert(new SortierInt(12));
    wurzel.insert(new SortierInt(8));
    wurzel.remove(new SortierInt(8));
    assertFalse(wurzel.contains(new SortierInt(8)));
  }

  /*
  remove working
   */
  @Test
  public void remove_onechild() {
    BinarySearchTree<SortierInt> wurzel = new BinarySearchTree<SortierInt>(new SortierInt(6));
    wurzel.insert(new SortierInt(2));
    wurzel.insert(new SortierInt(12));
    wurzel.insert(new SortierInt(8));
    wurzel.remove(new SortierInt(12));
    assertFalse(wurzel.contains(new SortierInt(12)));
  }

  /*
  remove working
   */
  @Test
  public void remove_twochildren() {
    BinarySearchTree<SortierInt> wurzel = new BinarySearchTree<SortierInt>(new SortierInt(6));
    wurzel.insert(new SortierInt(2));
    wurzel.insert(new SortierInt(12));
    wurzel.insert(new SortierInt(8));
    wurzel.remove(new SortierInt(6));
    assertFalse(wurzel.contains(new SortierInt(6)));
  }
}