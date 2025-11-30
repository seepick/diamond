package nl.uwv.smz.diamond.view.controllerImpl

import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.logicApi.PostsService
import nl.uwv.smz.diamond.domain.model.Post
import nl.uwv.smz.diamond.view.controllerApi.PostsController
import nl.uwv.smz.diamond.view.model.PostDto

class PostsControllerImpl(
    private val service: PostsService,
) : PostsController {
    override fun findAll() =
        either {
            service
                .fetchPosts()
                .map { it.toDto() }
                .right()
                .bind()
        }
}

fun Post.toDto() =
    PostDto(
        id = id,
        title = title,
    )
