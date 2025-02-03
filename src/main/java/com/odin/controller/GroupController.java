package com.odin.controller;

             import com.odin.model.Group;
             import com.odin.service.GroupService;
             import org.springframework.beans.factory.annotation.Autowired;
             import org.springframework.web.bind.annotation.*;

             import java.util.List;
             import java.util.Optional;

             /**
              * REST controller for managing groups.
              */
             @RestController
             @RequestMapping("/groups")
             public class GroupController {

                 @Autowired
                 private GroupService groupService;

                 // Existing methods...

                 /**
                  * Updates the information of an existing group.
                  * @param groupId the ID of the group to update
                  * @param updatedGroup the updated group information
                  * @return the updated group
                  */
                 @PutMapping("/{groupId}")
                 public Group updateGroupInfo(@PathVariable Long groupId, @RequestBody Group updatedGroup) {
                     // Call the service to update the group information
                     return groupService.updateGroupInfo(groupId, updatedGroup);
                 }

                 /**
                  * Deletes a group by its ID.
                  * @param groupId the ID of the group to delete
                  */
                 @DeleteMapping("/{groupId}")
                 public void deleteGroup(@PathVariable Long groupId) {
                     // Call the service to delete the group
                     groupService.deleteGroup(groupId);
                 }
             }