package com.pcmonitor.pcmonitorserver.services

import com.pcmonitor.pcmonitorserver.controllers.PCGroupController
import com.pcmonitor.pcmonitorserver.repositories.PCGroupRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class CreateGroupPCRequestValidation : Validator {

    companion object Test {
        fun create(): CreateGroupPCRequestValidation = CreateGroupPCRequestValidation()
    }

    @Autowired
    lateinit var pcGroupRepository: PCGroupRepository

    override fun validate(target: Any, errors: Errors) {
        //Проверяем на пустоту присланые поля
        ValidationUtils.rejectIfEmpty(errors, "groupName", "groupName.empty", "Введите звание группы!")

        val newPCGroup: PCGroupController.CreateGroupWithPC = target as PCGroupController.CreateGroupWithPC
//        TODO("Проверка на то что число пришло")
        //Если ошибок нет и id группы отсутствует то проверяем на существование группы
        if (!errors.hasErrors()) {
                if (pcGroupRepository.existsByGroupName(newPCGroup.groupName)) {
                        errors.rejectValue("groupName", "groupName.exists", "Введенное имя группы уже используется!")
                }
            }
        }


    override fun supports(clazz: Class<*>): Boolean {
        return PCGroupController.CreateGroupWithPC::class.java == clazz
    }
}


