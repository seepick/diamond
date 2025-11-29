package nl.uwv.smz.diamond.domain_logic_impl

import arrow.core.Either
import arrow.core.raise.either
import io.github.oshai.kotlinlogging.KotlinLogging
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.persistence.api.CrystalRepo

class CrystalServiceImpl(private val repo: CrystalRepo) : CrystalService {

    private val log = KotlinLogging.logger {}

    override suspend fun findAll() = either {
        log.debug { "findAll()" }
        repo.selectAll().bind()
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
