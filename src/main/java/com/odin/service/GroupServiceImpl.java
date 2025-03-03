package com.odin.service;

import com.odin.service.interfaces.GroupService;

/**
 * Implementation of GroupService interface.
 * Handles group management operations.
 */
public class GroupServiceImpl implements GroupService {

	/**
	 * Default constructor
	 */
	public GroupServiceImpl() {
		// Default constructor
	}

	@Override
	public <Response> Response getGroup(Long groupId) {

		return null;
	}

	@Override
	public void addMemberToGroup(Long groupId, Long userId) {

	}

	@Override
	public void deleteGroup(Long groupId) {

	}
}
