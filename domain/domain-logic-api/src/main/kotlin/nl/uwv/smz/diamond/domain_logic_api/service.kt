package nl.uwv.smz.diamond.domain_logic_api

import arrow.core.Either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain_failure.Failure

interface GreetService {
    fun greet(): String
}

interface CrystalService {
    fun findAll(): List<Crystal>
    fun findSingle(id: CrystalId): Either<Failure, Crystal>
    fun create(create: CrystalCreate): Either<Failure, Crystal>
    fun delete(id: CrystalId): Either<Failure, Unit>
}
