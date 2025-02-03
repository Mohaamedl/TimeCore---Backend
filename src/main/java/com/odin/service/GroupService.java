package com.odin.service;

      import com.odin.model.Group;
      import com.odin.repository.GroupRepository;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Service;

      import java.util.List;
      import java.util.Optional;

      /**
       * Service class for managing groups.
       */
      @Service
      public class GroupService {

          @Autowired
          private GroupRepository groupRepository;

          // Existing methods...

          /**
           * Updates the information of an existing group.
           * @param groupId the ID of the group to update
           * @param updatedGroup the updated group information
           * @return the updated group
           */
          public Group updateGroupInfo(Long groupId, Group updatedGroup) {
              // Retrieve the group by ID, throw an exception if not found
              Group group = groupRepository.findById(groupId).orElseThrow();

              // Update the group's information with the new data
              group.setName(updatedGroup.getName());
              group.setObjectives(updatedGroup.getObjectives());
              group.setAdmin(updatedGroup.getAdmin());
              group.setMembers(updatedGroup.getMembers());
              group.setSchedules(updatedGroup.getSchedules());

              // Save and return the updated group
              return groupRepository.save(group);
          }

          /**
           * Deletes a group by its ID.
           * @param groupId the ID of the group to delete
           */
          public void deleteGroup(Long groupId) {
              // Delete the group by ID
              groupRepository.deleteById(groupId);
          }
      }