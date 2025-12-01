package nl.uwv.smz.diamond.domain.logicImpl

import arrow.core.Either
import arrow.core.raise.either
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.logicApi.CrystalService
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalSortingsRequest
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.persistence.api.CrystalRepo

class CrystalServiceImpl(private val repo: CrystalRepo) : CrystalService {

    private val log = logger {}

    override suspend fun findAll(pageRequest: PageRequest, sorting: CrystalSortingsRequest) = either {
        log.debug { "findAll($pageRequest, $sorting)" }
        repo.selectAll(pageRequest, sorting).bind()
    }

    override suspend fun findSingle(id: CrystalId) = either {
        repo.selectById(id).bind()
    }

    override suspend fun create(create: CrystalCreate) = either {
        // here we could do actual business logic; right now it's just a "durchlauferhitzer" ;)
        repo.insert(create).bind()
    }

    override suspend fun update(update: CrystalUpdate) = either {
        repo.update(update).bind()
    }

    override suspend fun delete(id: CrystalId): Either<Failure, Unit> = either {
        repo.delete(id).bind()
    }
}
