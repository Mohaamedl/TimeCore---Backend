package com.example.timecore.service;

import com.example.timecore.model.Group;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private List<Group> groups = new ArrayList<>();

    public Group createGroup(Group group) {
        groups.add(group);
        return group;
    }

    public Optional<Group> getGroupById(String id) {
        return groups.stream().filter(group -> group.getId().equals(id)).findFirst();
    }

    public List<Group> getAllGroups() {
        return groups;
    }

    public Group updateGroup(String id, Group updatedGroup) {
        Optional<Group> existingGroup = getGroupById(id);
        if (existingGroup.isPresent()) {
            Group group = existingGroup.get();
            group.setName(updatedGroup.getName());
            group.setDescription(updatedGroup.getDescription());
            group.setMembers(updatedGroup.getMembers());
            return group;
        }
        return null;
    }

    public boolean deleteGroup(String id) {
        return groups.removeIf(group -> group.getId().equals(id));
    }
}