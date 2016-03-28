package org.eventchain;

/**
 * Protocol is a convention interface for implementing so called
 * Domain Protocols.
 *
 * The idea behind protocols is to re-use "polymorphic" events to
 * perform common operations on various types of entities.
 *
 * Consider, for example, that we have models called Product, Widget
 * and Company. All of these models have a name.
 *
 * Instead of assigning name or renaming each of these models in individual
 * command/event pairs (RenameProduct/ProductRenamed, RenameWidget/WidgetRenamed,
 * RenameCompany/CompanyRemained) and having similar yet different name retrieval methods
 * in the respective model, the following approach is used:
 *
 * There is only one command/event pair (Rename/NameChanged) that takes and stores a "polymorphic"
 * reference to any model (its UUID) and a new name. Now, since renaming any supported model
 * is done the same way, we can implement a common protocol for name retrieval:
 *
 * <pre>
 * <code>
 *
 * public interface NameProtocol extends Protocol {
 *     public String name() {
 *         try (ResultSet&lt;EntityHandle&lt;NameChanged&gt;&gt; resultSet =
 *              repository.query(NameChanged.class, equal(NameChanged.REFERENCE_ID, id()),
 *                               queryOptions(orderBy(descending(attribute)),
 *                                            applyThresholds(threshold(EngineThresholds.INDEX_ORDERING_SELECTIVITY, 0.5))))) {
 *              if (resultSet.isEmpty()) {
 *                  return null;
 *              }
 *              return resultSet.iterator().next().get().get().name();
 *        }
 *     }
 * }
 *
 * </code>
 * </pre>
 *
 *
 * The above protocol implements a {@code name()} function that retrieves the last {@code NameChange} for the particular
 * model referenced by its UUID ({@code id()}).
 *
 * Now, all we have to do is to make every model implement this interface:
 *
 * <pre>
 * <code>
 *
 * public class Product implements Model, NameProtocol { ... }
 * public class Widget implements Model, NameProtocol { ... }
 * public class Company implements Model, NameProtocol { ... }
 *
 * </code>
 * </pre>
 */
public interface Protocol extends Model {
}