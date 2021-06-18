/**
 * Interface to sort content in a generic data struture.
 *
 * @param <ContentType> Type of the data which should be sortable
 */
public interface Vergleichbar<ContentType> {

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this > parameter
   */
  boolean isGreater(ContentType pVergleich);

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this == parameter
   */
  boolean isEqual(ContentType pVergleich);

  /**
   * Returns boolean according to sortable value.
   *
   * @param pVergleich object to which this will be compared to
   * @return true if this < parameter
   */
  boolean isLess(ContentType pVergleich);

  Object getInhalt();
  void setInhalt(ContentType pInhalt);
}
