package nl.uwv.smz.diamond.domain.logicApi

import nl.uwv.smz.diamond.domain.model.Post

interface PostsService {
    fun fetchPosts(): List<Post>
}
