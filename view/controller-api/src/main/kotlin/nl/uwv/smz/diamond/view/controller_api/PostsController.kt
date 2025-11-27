package nl.uwv.smz.diamond.view.controller_api

import arrow.core.Either
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.view.model.PostDto

interface PostsController {
    fun findAll(): Either<Failure, List<PostDto>>
}
