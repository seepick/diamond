package nl.uwv.smz.diamond.extern.api.posts

interface PostsExtern {
    // TODO throws exception if fail => translate to either
    fun fetchPosts(): List<PostRto>
}

data class PostRto(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean? = null,
)
