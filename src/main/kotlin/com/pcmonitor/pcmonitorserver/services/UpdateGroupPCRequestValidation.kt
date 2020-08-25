package com.pcmonitor.pcmonitorserver.services

import com.pcmonitor.pcmonitorserver.controllers.PCGroupController
import com.pcmonitor.pcmonitorserver.repositories.PCGroupRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UpdateGroupPCRequestValidation : Validator {

    @Autowired
    lateinit var pcGroupRepository: PCGroupRepository

    override fun validate(target: Any, errors: Errors) {

        val newPCGroup: PCGroupController.GroupWithPC = target as PCGroupController.GroupWithPC

        //Проверяем на пустоту присланые поля
        ValidationUtils.rejectIfEmpty(errors, "groupId", "groupId.empty", "Укажите id группы!")
        //Если ошибок нет и id группы отсутствует то проверяем на существование группы
        if (!errors.hasErrors()) {
            if (!pcGroupRepository.existsByGroupId(newPCGroup.groupId))
                errors.rejectValue("groupName", "groupName.notExists", "Указанной группы не существует!")
            else {
                if (newPCGroup.groupId == 1.toLong()) {
                    errors.rejectValue("groupId", "groupId.cannotUpdate", "Эту группу нельзя редактировать")
                } else{
                    if (newPCGroup.groupName.isEmpty()) {
                        newPCGroup.groupName = pcGroupRepository.findById(newPCGroup.groupId).get().groupName
                    } else {
                        if (pcGroupRepository.existsByGroupName(newPCGroup.groupName)) {
                            if (pcGroupRepository.findById(newPCGroup.groupId).get().groupName != newPCGroup.groupName) {
                                errors.rejectValue("groupName", "groupName.exists", "Введенное имя группы уже используется!")
                            }
                        }
                    }
                }
            }
        }
    }


    override fun supports(clazz: Class<*>): Boolean {
        return PCGroupController.GroupWithPC::class.java == clazz
    }
}


