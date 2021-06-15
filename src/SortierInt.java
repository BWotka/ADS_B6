/**
 * Class used when storing Integer values in generic data structures.
 */
public class SortierInt implements Vergleichbar<SortierInt> {
  int inhalt;

  public SortierInt(int pInhalt) {
    inhalt = pInhalt;
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this > parameter
   */
  @Override
  public boolean isGreater(SortierInt pVergleich) {
    return inhalt > pVergleich.getInhalt();
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this == parameter
   */
  @Override
  public boolean isEqual(SortierInt pVergleich) {
    return inhalt == pVergleich.getInhalt();
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this < parameter
   */
  @Override
  public boolean isLess(SortierInt pVergleich) {
    return inhalt < pVergleich.getInhalt();
  }

  public int getInhalt() {
    return inhalt;
  }

  public void setInhalt(int pNeu) {
    inhalt = pNeu;
  }
}
