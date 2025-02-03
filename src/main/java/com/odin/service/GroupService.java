package com.odin.service;

public interface GroupService {

    public <Response> Response getGroup(Long groupId);
    public void addMemberToGroup(Long groupId, Long userId);
    public void deleteGroup(Long groupId);
}
