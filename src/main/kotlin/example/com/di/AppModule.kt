package example.com.di

import example.com.core.data.dao.user.UserDao
import example.com.core.domain.repositories.UserRepository
import example.com.core.user.UserDaoImpl
import example.com.core.user.UserRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}