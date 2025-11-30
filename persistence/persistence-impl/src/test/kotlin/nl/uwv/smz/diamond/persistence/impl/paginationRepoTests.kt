package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.model.Page
import nl.uwv.smz.diamond.domain.model.PageMeta
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.domain.model.build
import nl.uwv.smz.diamond.domain.model.default
import org.jetbrains.exposed.sql.Database

fun sortPaginationTests() {
    // TODO check that skip is in order; first get all; store order; then request again
}

fun <REPO, ENTITY> paginationRepoTests(
    dbProvider: () -> Database,
    repoProvider: (Database) -> REPO,
    inserter: (Int) -> Unit,
    paginatedRepoCall: suspend REPO.(PageRequest) -> Either<Failure, Page<ENTITY>>,
    // paginatedRepoCall: suspend CrystalRepo.(PageRequest) -> Either<Failure, Page<Crystal>>,
) = describeSpec {
    suspend fun execute(request: PageRequest): Either<Failure, Page<ENTITY>> =
        repoProvider(dbProvider()).paginatedRepoCall(request)

    describe("Pagination regular content cases") {
        it("Given 2 When take 1 Then return 1") {
            inserter(2)
            val request = PageRequest.build(skip = 0, take = 1)
            execute(request).shouldBeRight().should {
                it.items shouldHaveSize 1
            }
        }
    }
    describe("Pagination edge cases") {
        it("When skip more than existing Then return empty") {
            val request = PageRequest.build(skip = 10, take = 1)
            execute(request).shouldBeRight() shouldBeEqual Page(
                meta = PageMeta(10, 1, false),
                items = emptyList(),
            )
        }
        it("When take more than existing Then return empty") {
            val request = PageRequest.build(skip = 0, take = 3)
            execute(request).shouldBeRight() shouldBeEqual Page(
                meta = PageMeta(0, 3, false),
                items = emptyList(),
            )
        }
    }
    describe("Pagination meta data") {
        it("When request some Then meta skip and take set") {
            val request = PageRequest.build(skip = 1, take = 2)
            execute(request).shouldBeRight().should {
                it.meta.skip shouldBeEqual 1
                it.meta.take shouldBeEqual 2
            }
        }
        it("When request some Then meta has more is false") {
            val request = PageRequest.default()
            execute(request).shouldBeRight().should {
                it.meta.hasMore.shouldBeFalse()
            }
        }
        it("When request some Then meta has more is false") {
            inserter(2)
            val request = PageRequest.build(skip = 0, take = 1)
            execute(request).shouldBeRight().should {
                it.meta.hasMore.shouldBeTrue()
            }
        }
    }
}
