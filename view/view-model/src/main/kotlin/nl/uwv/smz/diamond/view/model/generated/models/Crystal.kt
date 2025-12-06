package nl.uwv.smz.diamond.view.model.generated.models

import kotlinx.serialization.Serializable

/**
 * A valuable item many desire.
 *
 * @param id
 * @param weightInGram how heavy the crystal is
 * @param created the moment the entity was stored in the database
 */
@Serializable
data class Crystal(
    val id: String,
    // how heavy the crystal is
    val weightInGram: Int,
    // the moment the entity was stored in the database
    val created: java.time.LocalDate
) {
    companion object // for extensions
}
