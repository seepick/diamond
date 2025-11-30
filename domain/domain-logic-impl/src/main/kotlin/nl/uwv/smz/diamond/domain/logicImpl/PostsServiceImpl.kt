package nl.uwv.smz.diamond.domain.logicImpl

import nl.uwv.smz.diamond.domain.logicApi.PostsService
import nl.uwv.smz.diamond.domain.model.Post
import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern

class PostsServiceImpl(
    private val extern: PostsExtern,
) : PostsService {
    // could enrch crystal with posts
    override fun fetchPosts() = extern.fetchPosts().map { it.toPost() }
}

private fun PostRto.toPost() =
    Post(
        id = id,
        title = title,
        // drop the other properties
    )
