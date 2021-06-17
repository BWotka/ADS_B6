/**
 * Generic AVL Tree which is sorted.
 * Can be used to store and search information in log(N)
 *
 * @param <ContentType> Stored information of this datatype has to be comparable
 */
public class AVL_Tree<ContentType extends Vergleichbar<ContentType>> {

  AVL_Tree<ContentType> parent;
  ContentType basis;
  AVL_Tree<ContentType> left;
  AVL_Tree<ContentType> right;

  public AVL_Tree() {}
  public AVL_Tree(ContentType pBasis) {
    basis = pBasis;
  }
  public AVL_Tree(ContentType pBasis,   AVL_Tree<ContentType> pParent) {
    basis = pBasis;
    parent = pParent;
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
    makeAVL();

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
      return;
    }

    if (pInhalt.isLess(basis)) {
      if (left == null) {
        left = new AVL_Tree<ContentType>();
      }
      left.insert(pInhalt, this);
      return;
    }

  }

  private void makeAVL() {
    AVL_Tree<ContentType> parenthere = parent;
    if (getBalance() < -1 || getBalance() > 1) {
      /*
      right outside
      left outside
      left inside
      right inside
       */

      // right tree with too long right side
      if (getBalance() < 0 && right.getBalance() < 0) {
        rotate_left();
      }

      // left tree with too long left side
      else if (getBalance() > 0 && left.getBalance() > 0) {
        rotate_right();
      }

      //right tree with long left
      else if (getBalance() < 0 && right.getBalance() > 0) {
        right.rotate_right();
        rotate_left();
      }

      // left tree with long right
      else if (getBalance() > 0 && left.getBalance() < 0) {
        left.rotate_left();
        rotate_right();
      }

    }
    if (parenthere != null && !parenthere.isEmpty()) {
      parenthere.makeAVL();
    }
  }

  private void rotate_right() {
    // save all needed information
    AVL_Tree<ContentType> parenthere = parent;
    AVL_Tree<ContentType> lefthere = left;
    AVL_Tree<ContentType> righthere = right;
    boolean isRight = isRightTree();

    left = lefthere.right;
    parent = lefthere;
    lefthere.parent = parenthere;
    lefthere.right = this;
    if (isRight) {
      parenthere.right = lefthere;
    }
    else if (parenthere != null) {
      parenthere.left = lefthere;
    }
  }

  private void rotate_left(){
    // save all needed information
    AVL_Tree<ContentType> parenthere = parent;
    AVL_Tree<ContentType> lefthere = left;
    AVL_Tree<ContentType> righthere = right;
    boolean isRight = isRightTree();

    right = righthere.getLeft();
    parent = righthere;
    righthere.parent = parenthere;
    righthere.left = this;
    if (isRight) {
      parenthere.right = righthere;
    }
    else if (parenthere != null) {
      parenthere.left = righthere;
    }
  }


  public AVL_Tree<ContentType> getLeft() {
    return left;
  }

  public AVL_Tree<ContentType> getRight() {
    return right;
  }
  public   AVL_Tree<ContentType> getParent() {
    return parent;
  }

  /**Checks weither this is parent.right
   *
   * @return  true if this == parent.right
   */
  public boolean isRightTree(){
    if (getParent() == null) {
      return false;
    }
    if (getParent().getRight() == this) {
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
    if (left == null || left.isEmpty()) {
      ContentType rueckgabe = basis;
      //rechten teil hochziehen / loeschen
      if (right == null || right.isEmpty()) {
        basis = null;

      } else {
        basis = right.getContent();
        if (right.getLeft() != null && !right.getLeft().isEmpty()) {
          left = right.getLeft();
        }
        else {
          left = null;
        }
        if (right.getRight() != null && !right.getRight().isEmpty()) {
          right = right.getRight();
        }
        else {
          right = null;
        }
        
      }
      return rueckgabe;
    } else {
      return left.furthestLeft();
    }
  }

  /**
   * removes pRemove from tree or children trees.
   * nothing happens if pRemove not in tree
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
   * @return wether pInhalt is in the AVL Tree
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
   * Calculates the max depth/high of this tree.
   * uses recursion
   *
   * @return max depth of this tree
   */
  public int getHeight() {
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
  public int getBalance() {
    int hightl;
    if (getLeft() == null || getLeft().isEmpty()) {
      hightl = 0;
    } else {
      hightl = getLeft().getHeight();
    }
    int hightr;
    if (getRight() == null || getRight().isEmpty()) {
      hightr = 0;
    } else {
      hightr = getRight().getHeight();
    }
    return hightl-hightr;
  }

  public AVL_Tree<ContentType> highestParent() {
    if (parent != null && !parent.isEmpty()){
      return parent.highestParent();
    }
    else {
      return this;
    }
  }
}
