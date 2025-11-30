package nl.uwv.smz.diamond.view.controllerApi

import arrow.core.Either
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.view.model.PostDto

interface PostsController {
    fun findAll(): Either<Failure, List<PostDto>>
}
