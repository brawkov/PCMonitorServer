package com.pcmonitor.pcmonitorserver.controllers


import com.pcmonitor.pcmonitorserver.models.PCGroupModel
import com.pcmonitor.pcmonitorserver.models.PCModel
import com.pcmonitor.pcmonitorserver.repositories.PCGroupRepository
import com.pcmonitor.pcmonitorserver.repositories.PCRepository
import com.pcmonitor.pcmonitorserver.services.CreateGroupPCRequestValidation
import com.pcmonitor.pcmonitorserver.services.DeleteGroupPcRequestValidation
import com.pcmonitor.pcmonitorserver.services.UpdateGroupPCRequestValidation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.util.*


@Suppress("UNCHECKED_CAST")
@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
class PCGroupController {

    @Autowired
    lateinit var pcGroupRepository: PCGroupRepository

    @Autowired
    lateinit var pcController: PCController

    @Autowired
    private val createGroupPCRequestValidation: CreateGroupPCRequestValidation? = null

    @Autowired
    private val updateGroupPCRequestValidation: UpdateGroupPCRequestValidation? = null

    @Autowired
    private val deleteGroupPcRequestValidation: DeleteGroupPcRequestValidation? = null

    @Autowired
    lateinit var pcRepository: PCRepository

    class GroupWithPC(var groupId: Long, var groupName: String, var listPC: List<Any>?)
    class CreateGroupWithPC(var groupName: String, var listPC: List<Any>?)
    class DeleteGroupPc(var groupId: Long?)


    @InitBinder("createGroupWithPC")
    fun initBinderForCreateGroupWithPC(binder: WebDataBinder) {
        binder.addValidators(createGroupPCRequestValidation)
    }

    @InitBinder("groupWithPC")
    fun initBinderForGroupWithPC(binder: WebDataBinder) {
        binder.addValidators(updateGroupPCRequestValidation)
    }

    @InitBinder("deleteGroupPc")
    fun initBinderForDeleteGroupPc(binder: WebDataBinder) {
        binder.addValidators(deleteGroupPcRequestValidation)
    }

    @GetMapping("/group")
    fun getlistGroup(): ResponseEntity<*> {

        val listGroupWithPC: MutableList<GroupWithPC> = mutableListOf()
        val listGroup: List<PCGroupModel> = pcGroupRepository.findAll()

        for (name in listGroup) {
            listGroupWithPC.add(GroupWithPC(name.groupId, name.groupName, pcController.getListPcByPcGroupId(name.groupId)))
        }

        return ResponseEntity.ok(listGroupWithPC)
    }


    @PostMapping("/group")
    fun createGroup(@RequestBody @Validated newPCGroupRequest: CreateGroupWithPC, errors: Errors): ResponseEntity<*> {

        val message: MutableMap<String, String> = mutableMapOf()

        return if (errors.hasErrors()) {
            for (error in errors.fieldErrors) {
                message += error.code.toString() to error.defaultMessage.toString()
            }
            ResponseEntity(message, HttpStatus.BAD_REQUEST)
        } else {
            val pcGroup = PCGroupModel(
                    0,
                    newPCGroupRequest.groupName
            )

            val newPcGroup = pcGroupRepository.save(pcGroup)

            //добавить ПК в группу
            if (!newPCGroupRequest.listPC.isNullOrEmpty())
                changePcInGroup(newPCGroupRequest.listPC as List<Long>, newPcGroup.groupId)

            ResponseEntity.ok(mapOf("message" to "Группа создана"))
        }
    }
    @PutMapping("/group")
    fun updateGroup(@RequestBody @Validated newPCGroupRequest: GroupWithPC, errors: Errors): ResponseEntity<*> {

        val message: MutableMap<String, String> = mutableMapOf()

        return if (errors.hasErrors()) {
            for (error in errors.fieldErrors) {
                message += error.code.toString() to error.defaultMessage.toString()
            }
            ResponseEntity(message, HttpStatus.BAD_REQUEST)
        } else {
            val pcGroup = PCGroupModel(
                    newPCGroupRequest.groupId,
                    newPCGroupRequest.groupName
            )

            val newPcGroup = pcGroupRepository.save(pcGroup)

            //Изменить ПК входящие в группу
            if (!newPCGroupRequest.listPC.isNullOrEmpty())
                changePcInGroup(newPCGroupRequest.listPC as List<Long>, newPcGroup.groupId)

            ResponseEntity.ok(mapOf("message" to "Группа изменена"))
        }
    }

    @DeleteMapping("/group")
    fun deleteGroup(@RequestBody @Validated deletePCGropRequest: DeleteGroupPc, errors: Errors): ResponseEntity<*> {

        val message: MutableMap<String, String> = mutableMapOf()
        return if (errors.hasErrors()) {
            for (error in errors.fieldErrors)
                message += error.code.toString() to error.defaultMessage.toString()
            ResponseEntity(message, HttpStatus.BAD_REQUEST)
        } else {
            val pcGroup: Optional<PCGroupModel> = pcGroupRepository.findById(deletePCGropRequest.groupId!!)
            val listPc : MutableList<Long> = mutableListOf()
            pcController.getListPcByPcGroupId(deletePCGropRequest.groupId!!).forEach { listPc += it.getPcId() }
//            Перенос пк из уаленой в группу по умолчанию
            changePcInGroup(listPc, 1)
//            Удаление группы
            pcGroupRepository.delete(pcGroup.get())
            ResponseEntity.ok(mapOf("message" to "Группа удалена"))
        }
    }

    fun changePcInGroup(listPC: List<Long>, groupId: Long) {
        for (pcId in listPC) {
                try {
                    val pc: PCModel = pcRepository.findByPcId(pcId)
                    pc.pcGroupId = groupId
                    pcRepository.save(pc)
                } catch (error: Exception) {
                    continue
                }
        }
    }
}
