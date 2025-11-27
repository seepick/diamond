package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.model.Post
import nl.uwv.smz.diamond.domain_logic_api.PostsService
import nl.uwv.smz.diamond.view.controller_api.PostsController
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
