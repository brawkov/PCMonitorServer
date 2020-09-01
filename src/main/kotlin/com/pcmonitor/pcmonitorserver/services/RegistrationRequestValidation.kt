package com.pcmonitor.pcmonitorserver.services

import com.pcmonitor.pcmonitorserver.models.UserModel
import com.pcmonitor.pcmonitorserver.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Service
class RegistrationRequestValidation : Validator {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun validate(target: Any, errors: Errors) {
        //Проверяем на пустоту присланые поля
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty", "Введите email")
        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty", "Введите username")
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty", "Введите пароль")

        val emailRegex = Regex("^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
                "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
                "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
                "+(?:[a-zA-Z]){2,}\\.?)$")

        val passwordRegex = Regex("^(?=.*\\d)(?=.*[aA-zZ])(?!.*\\s).*\$")

        val newUser: UserModel = target as UserModel
        // Если поля не пустые то прверям коректность email
        if (!errors.hasFieldErrors("email") && emailRegex.matchEntire(newUser.email) == null) {
            errors.rejectValue("email", "email.invalid", "Некоректный email")
        }
        //Если пароль не пустой то проверяем его коректность
        if (!errors.hasFieldErrors("password")) {
            if (passwordRegex.matchEntire(newUser.password) == null)
                errors.rejectValue("password", "password.invalid", "Пароль должен меть минимум одну цифру и одну латинскую букву!")
            if (newUser.password.length < 8)
                errors.rejectValue("password", "password.mustHaveMore", "Пароль должен быть не менее 8 символов!")
            if (newUser.password.length > 30)
                errors.rejectValue("password", "password.mustHaveLess", "Пароль должен быть не более 30 символов!")
        }
        //Если ошибок нет то проверяем на существование пользователя
        if(!errors.hasErrors()){
            if (userRepository.existsByEmail(newUser.email)) {
                errors.rejectValue("email", "email.exists", "Данный Email уже зарегестрирован!")
            }
            if (userRepository.existsByUsername(newUser.username!!)) {
                errors.rejectValue("username", "username.exists", "Имя пользователя уже занято!")
            }
        }

    }

override fun supports(clazz: Class<*>): Boolean {
    return UserModel::class.java == clazz
}
}


