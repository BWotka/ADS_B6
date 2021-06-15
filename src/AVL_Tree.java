/**
 * Generic BinaryTree which is sorted.
 * Can be used to store and search information in log(N)
 *
 * @param <ContentType> Stored information of this datatype has to be comparable
 */
public class AVL_Tree<ContentType extends Vergleichbar<ContentType>> {

  AVL_Tree<ContentType> parent;
  ContentType basis;
  AVL_Tree<ContentType> left;
  AVL_Tree<ContentType> right;

  public AVL_Tree(ContentType pBasis) {
    basis = pBasis;
  }
  public AVL_Tree(ContentType pBasis,   AVL_Tree<ContentType> pParent){
    basis = pBasis;
    parent = pParent;
  }

  public AVL_Tree() {
  }

  /**
   * removes this element and moves other elements up.
   * order stays intact
   */
  public void delete() {
    /*
     * switch
     * no child
     * one child
     * two children
     */
    int numberOfChildren = 0;
    if (! (left == null) && ! (left.isEmpty())) {
      numberOfChildren++;
    }
    if (! (right == null) && ! (right.isEmpty())) {
      numberOfChildren++;
    }
    switch (numberOfChildren) {
      case 0:
        basis = null;
        break;
      case 1:
        if (! (left == null) && ! (left.isEmpty())) {
          //rechts ist leer
          basis = left.getContent();
          right = left.getRight();
          left = left.getLeft();
        } else {
          //links ist leer
          basis = right.getContent();
          left = right.getLeft();
          right = right.getRight();
        }
        break;
      case 2:
        basis = right.furthestLeft();
        break;
    }


  }

  /**
   * inserts pInhalt at the correct spot.
   * works with recursion
   *
   * @param pInhalt which will be inserted
   */
  public void insert(ContentType pInhalt, AVL_Tree<ContentType> pParent) {
    if (this.isEmpty()) {
      basis = pInhalt;
      parent = pParent;
      makeAVL();
      return;
    }

    if (pInhalt.isGreater(basis)) {
      if (right == null) {
        right = new AVL_Tree<ContentType>();
      }
      right.insert(pInhalt, this);
      makeAVL();
      return;
    }

    if (pInhalt.isLess(basis)) {
      if (left == null) {
        left = new AVL_Tree<ContentType>();
      }
      left.insert(pInhalt, this);
      makeAVL();
      return;
    }

  }

  private void makeAVL() {
    if(getBalance() < -1 | getBalance() > 1){
      /*
      right outside
      left outside
      left inside
      right inside
       */

      // right tree with too long right side
      if(getLeft() != null && getBalance() < 0 && getRight().getBalance() < 0){
        AVL_Tree<ContentType> temp = getRight().getLeft();
        AVL_Tree<ContentType> tempparent = getParent();
        getRight().left = this;
        this.parent = getRight();
        getRight().parent = getParent();
        if(isRightTree()){
          tempparent.right = getRight();
        }
        else {
          tempparent.left = getRight();
        }
        right = temp;

      }
      // left tree with too long left side
        if (getLeft() != null && getBalance() > 0 && getLeft().getBalance() > 0) {
          AVL_Tree<ContentType> temp = getLeft().getRight();
          AVL_Tree<ContentType> tempparent = getParent();
          getLeft().right = this;
          this.parent = getLeft();
          getRight().parent = getParent();
          if (isRightTree()) {
            getParent().right = getRight();
          } else {
            getParent().left = getRight();
          }
          right = temp;

        }

    }
    if(getParent() != null){
      getParent().makeAVL();
    }
  }


  public AVL_Tree<ContentType> getLeft() {
    return left;
  }

  public AVL_Tree<ContentType> getRight() {
    return right;
  }
  public   AVL_Tree<ContentType> getParent(){
    return parent;
  }

  public boolean isRightTree(){
    if(getParent() == null){
      return false;
    }
    if(getParent().getRight() == this){
      return true;
    }
    return false;
  }


  public boolean isEmpty() {
    return basis == null;
  }

  public ContentType getContent() {
    return basis;
  }

  private ContentType furthestLeft() {
    if (left == null) {
      ContentType rueckgabe = basis;
      //rechten teil hochziehen / loeschen
      if (right == null) {
        basis = null;
      } else if (right.isEmpty()) {
        basis = null;
      } else {
        basis = right.getContent();
        right = right.getRight();
        left = right.getLeft();
      }
      return rueckgabe;
    } else if (left.isEmpty()) {
      ContentType rueckgabe = basis;
      //rechten teil hochziehen / loeschen
      if (right == null | right.isEmpty()) {
        basis = null;

      } else {
        basis = right.getContent();
        right = right.getRight();
        left = right.getLeft();
      }
      return rueckgabe;
    } else {
      return left.furthestLeft();
    }
  }

  /**
   * removes pRemove from tree or children trees.
   * nothing happens if premove not in tree
   * works with recursion
   *
   * @param pRemove content which will be removed
   */
  public void remove(ContentType pRemove) {
    if (this.isEmpty()) {
      return;
    }
    if (basis == null) {
      return;
    }
    if (pRemove.isEqual(basis)) {
      delete();
      return;
    }
    if (pRemove.isGreater(basis)) {
      if (right == null) {
        return;
      }
      right.remove(pRemove);
      return;
    }
    if (pRemove.isLess(basis)) {
      if (left == null) {
        return;
      }
      left.remove(pRemove);
      return;
    }
  }

  /**
   * Returns boolean wether pInhalt is in Tree.
   *
   * @param pInhalt which will be searched
   * @return wether pInhalt is in the BinarySearchTree
   */
  public boolean contains(ContentType pInhalt) {
    return ! (search(pInhalt) == null);
  }

  /**
   * Searches for ContentType which isEqual() with pInhalt.
   *
   * @param pInhalt test object which will be searched for
   * @return object in tree which is searched
   */
  public ContentType search(ContentType pInhalt) {
    if (basis == null) {
      return null;
    }
    if (this.isEmpty()) {
      return null;
    }
    if (basis.isEqual(pInhalt)) {
      return basis;
    }
    if (pInhalt.isGreater(basis)) {
      if (right == null) {
        return null;
      }
      return right.search(pInhalt);

    }
    if (pInhalt.isLess(basis)) {
      if (left == null) {
        return null;
      }
      return left.search(pInhalt);

    }

    return null;
  }

  /**
   * Calculates the max depth/high of this tree
   * uses recursion
   *
   * @return max depth of this tree
   */
  public int getHeight(){
    if (basis == null) {
      return 0;
    }
    if (this.isEmpty()) {
      return 0;
    }
    int hightl;
    if (getLeft() == null) {
      hightl = 0;
    } else {
      hightl = getLeft().getHeight();
    }
    int hightr;
    if (getRight() == null) {
      hightr = 0;
    } else {
      hightr = getRight().getHeight();
    }
    if (hightr > hightl) {
      return 1 + hightr;
    } else {
      return 1 + hightl;
    }
  }

  /**
   * Calculates balance of this tree.
   *
   * @return balanace of this tree
   */
  public int getBalance(){
    int hightl;
    if (getLeft() == null) {
      hightl = 0;
    } else {
      hightl = getLeft().getHeight()+1;
    }
    int hightr;
    if (getRight() == null) {
      hightr = 0;
    } else {
      hightr = getRight().getHeight()+1;
    }
    return hightl-hightr;
  }
}
