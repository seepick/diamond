package nl.uwv.smz.diamond.extern.stub.posts

import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.shared.common.HealthState
import nl.uwv.smz.diamond.shared.common.ServiceHealthInfo

class PostsExternStub : PostsExtern {

    var posts = emptyList<PostRto>()

    override fun fetchPosts(): List<PostRto> = posts

    override fun healthInfo() = ServiceHealthInfo(
        serviceName = "Posts API Stub",
        pingTimeInMs = 0,
        state = HealthState.Healthy,
    )
}
