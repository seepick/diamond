package nl.uwv.smz.diamond.view.routing

import io.ktor.server.routing.RoutingRequest
import nl.uwv.smz.diamond.view.controllerApi.QueryParamNames
import nl.uwv.smz.diamond.view.model.PageRequestDto

fun RoutingRequest.readPageRequestDto() = PageRequestDto(
    skip = queryParameters[QueryParamNames.Pagination.SKIP],
    take = queryParameters[QueryParamNames.Pagination.TAKE],
)
