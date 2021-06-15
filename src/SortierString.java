/**
 * Class used when storing String values in generic data structures.
 */
public class SortierString implements Vergleichbar<SortierString> {
  String inhalt;

  public SortierString(String pInhalt) {
    inhalt = pInhalt;
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this > parameter
   */
  @Override
  public boolean isGreater(SortierString pVergleich) {
    return inhalt.compareTo(pVergleich.getInhalt()) > 0;
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this == parameter
   */
  @Override
  public boolean isEqual(SortierString pVergleich) {
    return inhalt.compareTo(pVergleich.getInhalt()) == 0;
  }

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this < parameter
   */
  @Override
  public boolean isLess(SortierString pVergleich) {
    return inhalt.compareTo(pVergleich.getInhalt()) < 0;
  }

  public String getInhalt() {
    return inhalt;
  }
}
