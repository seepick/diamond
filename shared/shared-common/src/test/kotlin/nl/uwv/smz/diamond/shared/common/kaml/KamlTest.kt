package nl.uwv.smz.diamond.shared.common.kaml

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class KamlTest : DescribeSpec({
    describe("Simple") {
        it("single leaf node") {
            YamlTree(listOf(Node("key", LeafNodeValue("value")))).toYamlString() shouldBeEqual "key: value"
        }
    }
})
