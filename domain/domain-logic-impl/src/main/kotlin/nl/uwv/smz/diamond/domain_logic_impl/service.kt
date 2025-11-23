package nl.uwv.smz.diamond.domain_logic_impl

import arrow.core.Either
import arrow.core.raise.either
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.domain_logic_api.GreetService
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo

class GreetServiceImpl : GreetService {
    override fun greet(): String = "Hello Service!"
}

class CrystalServiceImpl(private val repo: CrystalRepo) : CrystalService {

    private val log = logger {}

    override suspend fun findAll() = either {
        log.debug { "findAll()" }
        repo.selectAll().map { it.toCrystal() }.bindAll()
    }

    override fun findSingle(id: CrystalId) = either {
        repo.selectById(id).bind().toCrystal().bind()
    }

    override fun create(create: CrystalCreate) = either {
        repo.create(create).bind().toCrystal().bind()
    }

    override fun update(update: CrystalUpdate) = either {
        repo.update(update).map { it.toCrystal().bind() }.bind()
    }

    override fun delete(id: CrystalId): Either<Failure, Unit> = either {
        repo.delete(id).bind()
    }
}

private fun CrystalDbo.toCrystal() = either {
    Crystal(
        id = CrystalId(id),
        weight = Gram(weightInGram).bind()
    )
}
