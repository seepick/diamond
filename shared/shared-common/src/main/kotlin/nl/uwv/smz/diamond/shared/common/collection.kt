package nl.uwv.smz.diamond.shared.common

// @OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.ifNotEmpty(code: (List<T>) -> Unit) {
//    contract {
//        callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
//    }
    if (isNotEmpty()) {
        code(this)
    }
}
