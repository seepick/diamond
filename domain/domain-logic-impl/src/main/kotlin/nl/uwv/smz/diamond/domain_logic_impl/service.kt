package nl.uwv.smz.diamond.domain_logic_impl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.domain_logic_api.GreetService
import kotlin.uuid.Uuid

class GreetServiceImpl : GreetService {
    override fun greet(): String = "Hello Service!"
}

class CrystalServiceImpl : CrystalService {

    private val log = logger {}
    private val crystals = mutableListOf<Crystal>()

    init {
        crystals += Crystal(id = CrystalId(Uuid.random()), weight = Gram(1337))
    }

    override fun findAll(): List<Crystal> {
        log.debug { "findAll()" }
        return crystals
    }

    override fun findSingle(id: CrystalId): Either<Failure, Crystal> { //  = either ... only when bind()
        return crystals.firstOrNull { it.id == id }?.right() ?: Failure.NotFoundFailure("").left()
//        return userRepository.findBy(userId).flatMap { existingUser ->
//            existingUser?.right()
//        }
    }

    override fun create(create: CrystalCreate): Either<Failure, Crystal> = either {
        ensure(create.weight.value > 0) { // TODO should be actually implicit (invariant; precondition on creation)
            Failure.InvalidRequestFailure("Crystal weight must be > 0 but was: ${create.weight}")
        }
        // TODO ensure with same ID doesn't exist!
        val crystal = Crystal(
            id = CrystalId(Uuid.random()),
            weight = create.weight,
        )
        crystals += crystal
        crystal
    }

    override fun delete(id: CrystalId): Either<Failure, Unit> = either {
        if (crystals.removeIf { it.id == id }) {
            Unit.right()
        }
        Failure.NotFoundFailure("Crystal not found with ID: $id").left()
    }
}
