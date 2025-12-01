package nl.uwv.smz.diamond.extern.api.posts

import nl.uwv.smz.diamond.shared.common.HealthableService

interface PostsExtern : HealthableService {
    // TODO throws exception if fail => translate to either
    fun fetchPosts(): List<PostRto>
}

data class PostRto(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean? = null,
)
