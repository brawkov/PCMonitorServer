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
class DeleteGroupPcRequestValidation : Validator {

    @Autowired
    lateinit var pcGroupRepository: PCGroupRepository

    override fun validate(target: Any, errors: Errors) {
        //Проверяем на пустоту присланые поля
        ValidationUtils.rejectIfEmpty(errors, "groupId", "groupId.empty", "Укажите Id группы")

        val deletePCGroup: PCGroupController.DeleteGroupPc = target as PCGroupController.DeleteGroupPc
        //Если ошибок нет то проверяем на существование группы и на то, что это не группа по умолчанию
        if (!errors.hasErrors()) {

            when (deletePCGroup.groupId ){
                    null -> errors.rejectValue("groupId", "groupId.cannotDeleted", "Укажите группу")
                    1.toLong() -> errors.rejectValue("groupId", "groupId.cannotDeleted", "Эту группу нельзя удалить")
                else->
                    if (!pcGroupRepository.existsByGroupId(deletePCGroup.groupId!!)) {
                        errors.rejectValue("groupId", "groupId.notExists", "Такой группы не существует")
                    }

            }
        }
    }


override fun supports(clazz: Class<*>): Boolean {
    return PCGroupController.DeleteGroupPc::class.java == clazz
}
}


