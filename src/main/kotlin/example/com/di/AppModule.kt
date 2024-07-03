package example.com.di

import example.com.core.data.dao.user.UserDao
import example.com.core.domain.repositories.AuthRepository
import example.com.core.user.UserDaoImpl
import example.com.core.user.AuthRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}