package example.com.utils

sealed class Either<out L, out R> {

    val isFailure get() = this is Failure<L>

    val isSuccess get() = this is Success<R>

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Failure -> fnL(error)
            is Success -> fnR(value)
        }

    fun errorValue(): L? = (this as? Failure)?.error

    fun successValue(): R? = (this as? Success)?.value

    data class Failure<out L>(val error: L) : Either<L, Nothing>() {
        val errorValue: L get() = error
    }

    data class Success<out R>(val value: R) : Either<Nothing, R>() {
        val successValue: R get() = value
    }
}
