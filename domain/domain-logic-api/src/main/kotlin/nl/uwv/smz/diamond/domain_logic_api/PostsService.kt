package nl.uwv.smz.diamond.domain_logic_api

import nl.uwv.smz.diamond.domain.model.Post

interface PostsService {
    fun fetchPosts(): List<Post>
}
