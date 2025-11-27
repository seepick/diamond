package nl.uwv.smz.diamond.extern.stub.posts

import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern

class PostsExternStub : PostsExtern {
    var posts =
        listOf(
            PostRto(id = 1, userId = 2, title = "test title", completed = true),
            PostRto(id = 2, userId = 3, title = "test title 2", completed = false),
        )

    override fun fetchPosts(): List<PostRto> = posts
}
