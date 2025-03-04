package com.odin.service;

/**
 * Service interface for managing group operations.
 * Handles group-related business logic.
 */
public interface GroupService {

    /**
     * Retrieves a group by its ID
     * @param <Response> Type of response object to return
     * @param groupId ID of the group to retrieve
     * @return Response containing group information
     */
    public <Response> Response getGroup(Long groupId);

    /**
     * Adds a user to a group
     * @param groupId ID of the group
     * @param userId ID of the user to add
     */
    public void addMemberToGroup(Long groupId, Long userId);

    /**
     * Deletes a group
     * @param groupId ID of the group to delete
     */
    public void deleteGroup(Long groupId);
}
