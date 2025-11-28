package nl.uwv.smz.diamond.extern.stub.posts

import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern

class PostsExternStub : PostsExtern {

    var posts = emptyList<PostRto>()

    override fun fetchPosts(): List<PostRto> = posts
}
