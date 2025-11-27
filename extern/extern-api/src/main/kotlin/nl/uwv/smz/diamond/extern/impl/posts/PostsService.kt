package nl.uwv.smz.diamond.extern.impl.posts

interface PostsService {
    // TODO throws exception if fail => translate to either
    fun fetchPosts(): List<PostDto>
}

data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean? = null,
)
